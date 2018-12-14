package controllers;

import exceptions.PlayerColorAlreadyUsed;
import models.classes.Player;
import models.enums.GameMode;
import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Game {

    /**
     * Set the mode of the game
     * @param game
     * @param gameMode
     */
    public static void setGameMode(models.classes.Game game, GameMode gameMode) {
        game.setGameMode(gameMode);
    }

    /**
     * Return all unused PlayerColors
     * @param game
     * @return
     */
    public static List<PlayerColor> getFreePlayerColors(models.classes.Game game) {
        List<PlayerColor> playerColorList = new ArrayList<>();
        // we add all possible PlayerColors
        for (PlayerColor playerColor : PlayerColor.values()) {
            playerColorList.add(playerColor);
        }
        // we check if some players has not already taken some colors
        for (Player player : game.getPlayers()) {
            playerColorList.remove(player.getPlayerColor()); // we remove the used color
        }
        return playerColorList;
    }

    /**
     * Create a player in the game passed in parameter
     * @param game
     * @param playerColor
     */
    public static void createPlayerWithColor(models.classes.Game game, PlayerColor playerColor) throws PlayerColorAlreadyUsed {
        // we check if the color has not already been taken
        boolean alreadyTaken = false;
        for (Player player : game.getPlayers()) {
            if (player.getPlayerColor().equals(playerColor)) {
                alreadyTaken = true; // another player has the wanted color
            }
        }
        if(!alreadyTaken) { // the color is not already used, we create a new player with this color
            Player player = new Player(playerColor);
            game.addPlayer(player);
        } else {
            throw new PlayerColorAlreadyUsed(); // the color has already been taken, normally the button with this color should not appear on the user's screen
        }
    }

}
