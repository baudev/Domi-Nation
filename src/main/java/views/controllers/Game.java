package views.controllers;

import javafx.scene.Group;
import models.classes.Player;
import models.interfaces.OnGameModeClickListener;
import models.interfaces.OnPlayerNumberClickListener;
import views.templates.GameMode;
import views.templates.NumberPlayer;

public class Game {

    private Group root;
    private models.classes.Game game;

    /**
     * Start the Game view controller which start by asking the game mode
     * @param root
     */
    public Game(Group root) {
        this.setRoot(root); // set the root group as a class attribute
        GameMode gameModeView = new GameMode();
        gameModeView.setOnGameModeClickListener(new OnGameModeClickListener() {
            @Override
            public void onGameModeClickListener(models.enums.GameMode gameMode) {
                game = new models.classes.Game(); // we create an instance of the game
                controllers.Game.setGameMode(game, gameMode); // we set the gameMode of the game
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
        NumberPlayer numberPlayerView = new NumberPlayer();
        numberPlayerView.setOnPlayerNumberClickListener(new OnPlayerNumberClickListener() {
            @Override
            public void onPlayerNumberClickListener(models.enums.NumberPlayer playerNumber) {
                for(int i=0; i < playerNumber.getValue(); i++) {
                    // for each player we ask their color
                }
            }
        });
        this.getRoot().getChildren().add(numberPlayerView);
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

    public models.classes.Game getGame() {
        return game;
    }

    public void setGame(models.classes.Game game) {
        this.game = game;
    }
}
