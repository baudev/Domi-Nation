package views.controllers;

import javafx.scene.Group;
import models.interfaces.OnGameModeClickListener;
import views.templates.GameMode;

public class Game {

    private Group root;

    public Game(Group root) {
        this.setRoot(root); // set the root group as a class attribute
        root.getChildren().add(new GameMode(new OnGameModeClickListener() {
            @Override
            public void onGameModeClickListener(models.enums.GameMode gameMode) { // define the callback for each GameMode button
                models.classes.Game game = new models.classes.Game(); // we create an instance of the game
                controllers.Game.setGameMode(game, gameMode); // we set the gameMode of the game
            }
        }));
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
}
