package views.controllers;

import exceptions.*;
import helpers.Screen;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import models.classes.*;
import models.enums.GameMode;
import models.enums.PlayerColor;
import models.enums.PlayerNumber;
import models.enums.Rotation;
import views.interfaces.*;
import views.templates.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Handles the link between all JavaFX views and Core classes.
 */
public class GameViewController {

    private Group root;
    private Game game;

    private ButtonView buttonRotation;
    private ButtonView discardButton;

    /**
     * Starts the GameViewController view controller which start by asking the game mode.
     * @param root {@link Group} root of the JavaFX view.
     * @see #askGameMode()
     */
    public GameViewController(Group root) {
        this.setRoot(root); // set the root group as a class attribute
        askGameMode();
    }

    /**
     * Asks which {@link GameMode} should be played.
     * {@link GameMode#HARMOMY} is not playable.
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
     * Asks the number of players.
     * If the mode if {@link GameMode#THEGREATDUEL}, then the number of players of 2.
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
     * Asks to each player the {@link PlayerColor} he wants.
     * @param currentPlayerNumber Number of the current Player who choose his {@link PlayerColor}.
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
     * Initiates the {@link Game} and {@link Player}s.
     * Then start a new turn.
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
                        break;
                    case 1:
                        boardView.setTranslateX(Screen.percentageToXDimension(70));
                        break;
                    case 2:
                        boardView.setTranslateY(Screen.percentageToYDimension(45));
                        break;
                    case 3:
                        boardView.setTranslateY(Screen.percentageToYDimension(45));
                        boardView.setTranslateX(Screen.percentageToXDimension(70));
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
     * Pick {@link Domino}s and show them in the middle of the {@link Game} {@link Board}.
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
            this.getGame().getNewDominoesList().getDominoesListView().showNumberFaces();
            this.getGame().getNewDominoesList().getDominoesListView().showPortionsFaces();
        }
    }

    /**
     * Asks to the current {@link Player} which {@link Domino} he wants to choose.
     */
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
                });
            }
        }
    }

    /**
     * Asks to the current {@link Player} where he wants to place his last {@link Domino}(s).
     */
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


    /**
     * Calculates the final {@link Score} and shows the winner(s).
     * @see Score
     */
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
     * Shows a button to make rotate a domino. Shows possibilities of {@link Domino} places. And shows discard button also.
     * @param domino {@link Domino} that the current {@link Player} must place or discard.
     */
    public void showRotationButtonAndAssociatedPossibilities(Domino previousDomino, Domino domino, DominoesList newDominoesList) {
        //  we show all possible positions
        getGame().getCurrentPlayer().getBoard().getPossibilities(previousDomino);
        // show rotation button
        ButtonView buttonRotation = new ButtonView("Rotate",true);  //  add a buttonView with a small size
        setButtonRotation(buttonRotation);
        getButtonRotation().setLayoutX(Screen.percentageToXDimension(48));  //  positioning the button
        getButtonRotation().setLayoutY(Screen.percentageToYDimension(60));
        getRoot().getChildren().add(getButtonRotation());
        getButtonRotation().setTranslateX(-Screen.percentageToXDimension(20));
        getButtonRotation().setTranslateY(-Screen.percentageToYDimension(6));
        getButtonRotation().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getGame().makeRotateDomino(previousDomino);
            }
        });
        getButtonRotation().setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Bloom bloom = new Bloom();      //add a bloom effect when the mouse is on the button
                bloom.setThreshold(0.6);
                getButtonRotation().setEffect(bloom);
            }
        });
        getButtonRotation().setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                getButtonRotation().setEffect(null); //  remove the bloom effect when the mouse is no longer on the button

            }
        });
        ButtonView discardButton = new ButtonView("Discard",true);  //  add a ButtonView with a small size
        setDiscardButton(discardButton);
        getDiscardButton().setLayoutX(Screen.percentageToXDimension(55));   //  positioning the button
        getDiscardButton().setLayoutY(Screen.percentageToYDimension(60));
        getRoot().getChildren().add(getDiscardButton());
        getDiscardButton().setTranslateY(-Screen.percentageToYDimension(6));
        getDiscardButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideRotationButton(); // we remove the button rotation view
                getRoot().getChildren().remove(getDiscardButton());
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

        getDiscardButton().setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                Bloom bloom = new Bloom();      //add a bloom effect when the mouse is on the button
                bloom.setThreshold(0.6);
                getDiscardButton().setEffect(bloom);
            }
        });
        getDiscardButton().setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                getDiscardButton().setEffect(null); //  remove the bloom effect when the mouse is no longer on the button

            }
        });

        getGame().getCurrentPlayer().getBoard().getBoardView().setOnPossibilityClickListener(new OnPossibilityClickListener() {
            @Override
            public void onPossibilityClickListener(Position position1, Position position2) throws InvalidDominoPosition {
                hideRotationButton(); // we remove the button rotation view
                getRoot().getChildren().remove(getDiscardButton());
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
     * Hides the domino {@link Rotation} button.
     * @see Rotation
     */
    private void hideRotationButton() {
        getRoot().getChildren().remove(getButtonRotation());
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the root {@link Group} of JavaFX.
     * @return The root {@link Group} of JavaFX.
     */
    public Group getRoot() {
        return root;
    }

    /**
     * Sets the root {@link Group} of JavaFX.
     * @param root The root {@link Group} of JavaFX to be set.
     */
    public void setRoot(Group root) {
        this.root = root;
    }

    /**
     * Returns the {@link Game} associated to the {@link GameViewController}.
     * @return The {@link Game} associated to the {@link GameViewController}.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets the {@link Game} associated to the {@link GameViewController}.
     * @param game The {@link Game} associated to the {@link GameViewController}.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the {@link Rotation} {@link Button} instance.
     * @return The {@link Rotation} {@link Button} instance.
     */
    public ButtonView getButtonRotation() {
        return buttonRotation;
    }

    /**
     * Sets the {@link Rotation} {@link Button} instance.
     * @param buttonRotation The {@link Rotation} {@link Button} instance to be set.
     */
    public void setButtonRotation(ButtonView buttonRotation) {
        this.buttonRotation = buttonRotation;
    }

    /**
     * Returns the discard {@link Button} instance.
     * @return The discard {@link Button} instance.
     */
    public ButtonView getDiscardButton() {
        return discardButton;
    }

    /**
     * Sets the discard {@link Button} instance.
     * @param discardButton The discard {@link Button} instance to be set.
     */
    public void setDiscardButton(ButtonView discardButton) {
        this.discardButton = discardButton;
    }
}
