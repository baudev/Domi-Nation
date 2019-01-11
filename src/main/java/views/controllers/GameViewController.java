package views.controllers;

import exceptions.*;
import helpers.Screen;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import models.classes.*;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.PlayerNumber;
import models.enums.Rotation;
import views.interfaces.*;
import views.templates.BoardView;
import views.templates.ColorPlayerView;
import views.templates.GameModeView;
import views.templates.NumberPlayerView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javafx.application.Platform.exit;

public class GameViewController {

    private Group root;
    private Game game;

    private Button buttonRotation;

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
            int count = 0;
            for(Player player : this.getGame().getPlayers()) {
                BoardView boardView = player.getBoard().getBoardView();
                this.getRoot().getChildren().add(boardView);
                switch (count) {
                    case 0:
                        boardView.setTranslateY(Screen.percentageToYDimension(10));
                        break;
                    case 1:
                        boardView.setTranslateY(Screen.percentageToYDimension(10));
                        boardView.setTranslateX(Screen.percentageToXDimension(60));
                        break;
                    case 2:
                        boardView.setTranslateX(Screen.percentageToXDimension(40));
                        break;
                    case 3:
                        boardView.setTranslateY(Screen.percentageToYDimension(60));
                        boardView.setTranslateX(Screen.percentageToXDimension(40));
                        break;
                }
                count++;
            }
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
            this.getGame().pickDominoes(); // we pick as many dominoes as kings in game
        } catch (NoMoreDominoInGameStack noMoreDominoInGameStack) {
            lastPlayerTurn();
        } catch (NotEnoughDominoesInGameStack notEnoughDominoesInGameStack) {
            notEnoughDominoesInGameStack.printStackTrace(); // TODO
        }
        if(!this.getGame().isLastTurn()) {
            playTurnPlayer(); // !! BEFORE DOMINO VIEW GENERATION !!
            this.getRoot().getChildren().add(this.getGame().getNewDominoesList().getDominoesListView());
            this.getGame().getNewDominoesList().getDominoesListView().setLowerPosition();
            // we translate by x the precedent row of dominoes
            if (this.getGame().getTurnNumber() > 1) {
                this.getGame().getPreviousDominoesList().getDominoesListView().setUpperPosition();
            }
            this.getGame().getNewDominoesList().getDominoesListView().showPortionsFaces();
        }
    }

    private void playTurnPlayer() {
        if(this.getGame().isLastTurn()) {
            lastPlayerTurn();
        } else {
            for (Domino domino : this.getGame().getNewDominoesList()) { // we define a clickListener for each domino
                domino.getDominoView().setOnDominoClickListener(new OnDominoClickListener() {
                    @Override
                    public void onDominoClickListener(Domino domino) {
                        for(Domino domino1 : getGame().getNewDominoesList()) { // disable all clickListeners for all Dominoes
                            domino1.getDominoView().setOnDominoClickListener(new OnDominoClickListener() {
                                @Override
                                public void onDominoClickListener(Domino domino) {

                                }
                            });
                        }
                        switch (getGame().playerChoosesDomino(domino)) {
                            case PICKDOMINOES:
                                pickDominoes();
                                break;
                            case NEXTTURNPLAYER:
                                playTurnPlayer();
                                break;
                            case SHOWPLACEPOSSIBILITIES:
                                Domino previousDomino = getGame().getPreviousDomino();
                                showRotationButtonAndAssociatedPossibilities(previousDomino, domino, getGame().getNewDominoesList());
                                break;
                            case NULL:
                                // nothing
                                break;
                        }
                    }
                });
            }
        }
    }

    private void lastPlayerTurn() {
        switch (getGame().playerChoosesDomino(null)){
            case GAMEOVER:
                this.calculateScore();
                break;
            default:
                Domino previousDomino = getGame().getPreviousDomino();
                showRotationButtonAndAssociatedPossibilities(previousDomino, null, getGame().getNewDominoesList());
                break;
        }
    }

    private void calculateScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game results");
        alert.setHeaderText("The game is over, the results are as follows:");
        StringBuilder resultsString = new StringBuilder();

        Map<Player, Integer> results = this.getGame().calculateScore();
        for (Map.Entry<Player, Integer> entry : results.entrySet()) {
            resultsString.append("Player ").append(entry.getKey().getPlayerColor().toString()).append(" : ").append(entry.getValue()).append("\n");
        }
        alert.setContentText(resultsString.toString());
        alert.showAndWait();

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Game results");
        alert2.setHeaderText("The winner(s) is/are then:");
        StringBuilder resultsString2 = new StringBuilder();

        List<Player> winners = this.getGame().getWinners();
        for (Player player : winners) {
            resultsString2.append("Player ").append(player.getPlayerColor().toString()).append("\n");
        }
        alert2.setContentText(resultsString2.toString());
        alert2.showAndWait();
    }

    /**
     * Show a button to make rotate a domino
     * @param domino
     */
    public void showRotationButtonAndAssociatedPossibilities(Domino previousDomino, Domino domino, DominoesList newDominoesList) {
        //  we show all possible positions
        getGame().getCurrentPlayer().getBoard().getPossibilities(previousDomino);
        // show rotation button
        setButtonRotation(new Button());
        getButtonRotation().setLayoutX(Screen.percentageToXDimension(48));
        getButtonRotation().setLayoutY(Screen.percentageToYDimension(60));
        getButtonRotation().setText("Rotate");
        getRoot().getChildren().add(getButtonRotation());
        getButtonRotation().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getGame().makeRotateDomino(previousDomino);
            }
        });

        Button discardButton = new Button();
        discardButton.setLayoutX(Screen.percentageToXDimension(55));
        discardButton.setLayoutY(Screen.percentageToYDimension(60));
        discardButton.setText("Discard");
        getRoot().getChildren().add(discardButton);
        discardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideRotationButton(); // we remove the button rotation view
                getRoot().getChildren().remove(discardButton);
                switch (getGame().discardDomino()) {
                    case PICKDOMINOES:
                        pickDominoes();
                        break;
                    case NEXTTURNPLAYER:
                        playTurnPlayer();
                        break;
                    case NULL:
                        break;
                }
            }
        });

        getGame().getCurrentPlayer().getBoard().getBoardView().setOnPossibilityClickListener(new OnPossibilityClickListener() {
            @Override
            public void onPossibilityClickListener(Position position1, Position position2) {
                hideRotationButton(); // we remove the button rotation view
                getRoot().getChildren().remove(discardButton);
                switch (getGame().playerChoosesPositionForDomino(position1, position2)) {
                    case NEXTTURNPLAYER:
                        playTurnPlayer();
                        break;
                    case PICKDOMINOES:
                        pickDominoes();
                        break;
                    case NULL:
                        break;
                }
            }
        });
    }

    /**
     * Hide the domino rotation button
     */
    public void hideRotationButton() {
        getRoot().getChildren().remove(getButtonRotation());
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

    public Button getButtonRotation() {
        return buttonRotation;
    }

    public void setButtonRotation(Button buttonRotation) {
        this.buttonRotation = buttonRotation;
    }
}
