package models.classes;



import exceptions.InvalidDominoesCSVFile;
import exceptions.MaxCrownsLandPortionExceeded;
import exceptions.PlayerColorAlreadyUsed;
import helpers.CSVReader;
import models.enums.GameMode;
import models.enums.PlayerColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    private List<Domino> dominoes;
    private List<Player> players;
    private GameMode gameMode;

    public Game() {
        this.setPlayers(new ArrayList<>());
    }


    /**
     * Return all unused player colors
     * @return
     */
    public List<PlayerColor> getFreePlayerColors() {
        // we add all possible PlayerColors
        List<PlayerColor> playerColorList = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        // we check if some players has not already taken some colors
        for (Player player : this.getPlayers()) {
            playerColorList.remove(player.getPlayerColor()); // we remove the used color
        }
        return playerColorList;
    }

    /**
     * Create a player in the game passed in parameter
     * @param playerColor
     */
    public void createPlayerWithColor(PlayerColor playerColor) throws PlayerColorAlreadyUsed {
        // we check if the color has not already been taken
        boolean alreadyTaken = false;
        for (Player player : this.getPlayers()) {
            if (player.getPlayerColor().equals(playerColor)) {
                alreadyTaken = true; // another player has the wanted color
            }
        }
        if(!alreadyTaken) { // the color is not already used, we create a new player with this color
            Player player = new Player(playerColor);
            this.addPlayer(player);
        } else {
            throw new PlayerColorAlreadyUsed(); // the color has already been taken, normally the button with this color should not appear on the user's screen
        }
    }

    /**
     * Generate the dominoes for the current game
     */
    public void generateDominoes() throws IOException, InvalidDominoesCSVFile {
        this.setDominoes(CSVReader.getDominoes()); // we get all the dominoes stored in the CSV file
        int numberDominoesToRemove = 0;
        switch (this.getPlayers().size()){
            case 2:
                numberDominoesToRemove = 24;
                break;
            case 3:
                numberDominoesToRemove = 12;
                break;
        }
        Collections.shuffle(this.dominoes); // we shuffle the list
        this.dominoes.subList(0, this.dominoes.size() - numberDominoesToRemove).clear(); // we just get a sub part of the list
    }

    public void initiatePlayers() throws MaxCrownsLandPortionExceeded {
        // for each player we generate his grid
        for(Player player : this.getPlayers()) {
            player.setBoard(new Board(this.getGameMode(), player.getPlayerColor())); // generate the board with castle and startTile
            // TODO generate kings of the player
        }
    }


    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public List<Domino> getDominoes() {
        return dominoes;
    }

    public void setDominoes(List<Domino> dominoes) {
        this.dominoes = dominoes;
    }

    public void addDomino(Domino domino) {
        this.dominoes.add(domino);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}


