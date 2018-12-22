package views.controllers;

import exceptions.PlayerColorAlreadyUsed;
import javafx.scene.Group;
import models.enums.PlayerColor;
import models.enums.PlayerNumber;
import views.interfaces.OnGameModeClickListener;
import views.interfaces.OnPlayerColorClickListener;
import views.interfaces.OnPlayerNumberClickListener;
import views.templates.ColorPlayerView;
import views.templates.GameModeView;
import views.templates.NumberPlayerView;

import java.util.List;

public class GameViewController {

    private Group root;
    private models.classes.Game game;

    /**
     * Start the GameViewController view controller which start by asking the game mode
     * @param root
     */
    public GameViewController(Group root) {
        this.setRoot(root); // set the root group as a class attribute
        GameModeView gameModeView = new GameModeView();
        gameModeView.setOnGameModeClickListener(new OnGameModeClickListener() {
            @Override
            public void onGameModeClickListener(models.enums.GameMode gameMode) {
                game = new models.classes.Game(); // we create an instance of the game
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


    private void askPlayerColor(int currentPlayerNumber) {
        // we get all unused playerColors
        List<PlayerColor> freePlayerColorList = game.getFreePlayerColors();
        // we ask the color to the current player
        ColorPlayerView colorPlayerView = new ColorPlayerView(freePlayerColorList, currentPlayerNumber);
        colorPlayerView.setOnPlayerColorClickListener(new OnPlayerColorClickListener() {
            @Override
            public void onPlayerColorClickListener(PlayerColor playerColor) {
                System.out.println(playerColor);
                // we create a player with the selected color
                try {
                    game.createPlayerWithColor(playerColor);
                    // we reset the view for next step
                    root.getChildren().remove(colorPlayerView);
                    if(currentPlayerNumber <= 1) {
                        // we ask their wanted color to each player. We can start the game
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
