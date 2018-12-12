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
                // TODO select game mode and pass to the next step
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
