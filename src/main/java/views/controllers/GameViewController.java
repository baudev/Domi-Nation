package views.controllers;

import exceptions.*;
import helpers.Screen;
import javafx.scene.Group;
import models.classes.*;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.PlayerNumber;
import views.interfaces.OnDominoClickListener;
import views.interfaces.OnGameModeClickListener;
import views.interfaces.OnPlayerColorClickListener;
import views.interfaces.OnPlayerNumberClickListener;
import views.templates.ColorPlayerView;
import views.templates.GameModeView;
import views.templates.NumberPlayerView;

import java.io.IOException;
import java.util.List;

import static javafx.application.Platform.exit;

public class GameViewController {

    private Group root;
    private Game game;

    /**
     * Start the GameViewController view controller which start by asking the game mode
     * @param root
     */
    public GameViewController(Group root) {
        this.setRoot(root); // set the root group as a class attribute
        askGameMode();
    }

    /**
     * Ask which game mode should be played
     */
    private void askGameMode() {
        game = new Game(); // we create an instance of the game
        GameModeView gameModeView = new GameModeView();
        gameModeView.setOnGameModeClickListener(new OnGameModeClickListener() {
            @Override
            public void onGameModeClickListener(GameMode gameMode) {
                // TODO if DYNASTY, count the number of rounds
                game.setGameMode(gameMode);  // we set the gameMode of the game
                // we remove the current view
                root.getChildren().remove(gameModeView);
                // ask the number of players
                askNumberPlayer();
            }
        });
        root.getChildren().add(gameModeView);
    }

    /**
     * Ask the number of players
     */
    private void askNumberPlayer() {
        // if the gameMode is great duel, then the number of players is 2
        if(game.getGameMode().equals(GameMode.THEGREATDUEL)){
            askPlayerColor(PlayerNumber.TWO.getValue());
        } else { // if the gameMode is another one, we ask the number of players
            NumberPlayerView numberPlayerView = new NumberPlayerView();
            numberPlayerView.setOnPlayerNumberClickListener(new OnPlayerNumberClickListener() {
                @Override
                public void onPlayerNumberClickListener(PlayerNumber playerNumber) {
                    // we reset the view
                    root.getChildren().remove(numberPlayerView);
                    askPlayerColor(playerNumber.getValue());
                }
            });
            this.getRoot().getChildren().add(numberPlayerView);
        }
    }

    /**
     * Ask to each player the color he wants
     * @param currentPlayerNumber
     */
    private void askPlayerColor(int currentPlayerNumber) {
        // we get all unused playerColors
        List<PlayerColor> freePlayerColorList = game.getFreePlayerColors();
        // we ask the color to the current player
        ColorPlayerView colorPlayerView = new ColorPlayerView(freePlayerColorList, currentPlayerNumber);
        colorPlayerView.setOnPlayerColorClickListener(new OnPlayerColorClickListener() {
            @Override
            public void onPlayerColorClickListener(PlayerColor playerColor) {
                // we create a player with the selected color
                try {
                    game.createPlayerWithColor(playerColor);
                    // we reset the view for next step
                    root.getChildren().remove(colorPlayerView);
                    if(currentPlayerNumber <= 1) {
                        // we ask their wanted color to each player. We can start the game
                        initiateGame();
                    } else {
                        // we have not asked to each player their wanted color
                        askPlayerColor(currentPlayerNumber - 1);
                    }
                } catch (PlayerColorAlreadyUsed playerColorAlreadyUsed) {
                    // the color is already taken, weird case
                    // we ask again
                    System.out.println("Error, color already taken");
                }
            }
        });
        root.getChildren().add(colorPlayerView);
    }

    /**
     * Initiate the game and players
     */
    private void initiateGame() {
        try {
            this.getGame().initiatePlayers(); // we initiate all player attributes
            this.getGame().generateDominoes(); // we generate the dominoes for the game
            this.getGame().setTurnNumber(1); // first turn start
        } catch (IOException | MaxCrownsLandPortionExceeded | InvalidDominoesCSVFile e) {
            e.printStackTrace(); // TODO handle errors
        }
        pickDominoes();
    }

    /**
     * Pick dominoes and show them in the middle of the game board
     */
    private void pickDominoes() {
        try {
            this.getGame().pickDominoes(this.getGame().numberKingsInGame()); // we pick as many dominoes as kings in game
        } catch (NoMoreDominoInGameStack noMoreDominoInGameStack) {
            System.out.println("Game ended");
            exit();
        } catch (NotEnoughDominoesInGameStack notEnoughDominoesInGameStack) {
            notEnoughDominoesInGameStack.printStackTrace();
        }
        DominoesList newDominoesList = this.getGame().getPickedDominoes().get(this.getGame().getPickedDominoes().size() - 1);
        newDominoesList.sortByNumber(); // we order them by the number
        playTurnPlayer();
        this.getRoot().getChildren().add(newDominoesList.getDominoesListView());
        // TODO
        // TODO
        // we translate by x the precedent row of dominoes
        if(this.getGame().getTurnNumber() > 1) {
            DominoesList previousDominoesList = this.getGame().getPickedDominoes().get(this.getGame().getPickedDominoes().size() - 2);
            previousDominoesList.getDominoesListView().setTranslateY(Screen.percentageToYDimension(40));
        }
        newDominoesList.getDominoesListView().showPortionsFaces();
    }

    private void playTurnPlayer() {
        DominoesList newDominoesList = this.getGame().getPickedDominoes().get(this.getGame().getPickedDominoes().size() - 1);
        // we ask for the next King to be placed
        King king = this.getGame().nextKing();
        for(Domino domino : newDominoesList) { // we define a clickListener for each domino
            domino.getDominoView().setOnDominoClickListener(new OnDominoClickListener() {
                @Override
                public void onDominoClickListener(Domino domino) {
                    if(domino.getKing() != null || king.isPlaced()) {
                        // we do nothing
                    } else {
                        System.out.println("Domino number : " + domino.getNumber());
                        domino.setKing(king);
                        // TODO simplify next operations
                        if(getGame().getTurnNumber() == 1) {
                            getGame().getCurrentPlayer().getBoard().addDomino(domino);
                            newDominoesList.remove(domino);
                            getGame().playerHasSelectedDomino();
                        } else {
                            // we get the domino on which the king was
                            Domino previousDomino = getGame().getCurrentPlayer().getDominoWithKing(king);
                            previousDomino.setKing(null); // !! BEFORE SETTING POSITION OF THE PREVIOUS DOMINO !!
                            // set the position
                            previousDomino.getLeftPortion().setPosition(new Position(1, 1));
                            previousDomino.getRightPortion().setPosition(new Position(1, 2));

                            getGame().getCurrentPlayer().getBoard().addDomino(domino);
                            newDominoesList.remove(domino);

                            // TODO add the Player to the turns list
                            getGame().playerHasSelectedDomino();
                        }

                        // we check if it's not a new turn
                        if(getGame().isAllPickedDominoesListHaveKings(getGame().getPickedDominoes().size() - 1)) {
                            getGame().setTurnNumber(getGame().getTurnNumber() + 1); // increment the turn number
                            pickDominoes();
                        } else {
                            playTurnPlayer();
                        }
                    }
                }
            });
        }
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */
    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
