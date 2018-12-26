package models.classes;



import exceptions.InvalidDominoesCSVFile;
import exceptions.MaxCrownsLandPortionExceeded;
import exceptions.PlayerColorAlreadyUsed;
import helpers.CSVReader;
import helpers.Function;
import models.enums.GameMode;
import models.enums.PlayerColor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game {

    private DominoesList dominoes;
    private List<Player> players;
    private List<PlayerColor> playerTurns;
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
        // if the gameMode is great duel, then all dominoes should be used
        if(this.getGameMode().equals(GameMode.THEGREATDUEL)) {
            numberDominoesToRemove = 0;
        }
        Collections.shuffle(this.dominoes); // we shuffle the list
        if(numberDominoesToRemove > 0) {
            this.dominoes.subList(this.dominoes.size() - numberDominoesToRemove, this.dominoes.size()).clear(); // we just get a sub part of the list
        }
    }

    /**
     * Initiate all player attributes such as their boards or kings
     * @throws MaxCrownsLandPortionExceeded
     */
    public void initiatePlayers() throws MaxCrownsLandPortionExceeded {
        // for each player we generate his grid and his kings
        for(Player player : this.getPlayers()) {
            // we generate the board
            player.setBoard(new Board(this.getGameMode(), player.getPlayerColor())); // generate the board with castle and startTile
            // we generate the good number of kings
            int numberOfKings = 0;
            switch (this.getPlayers().size()){
                case 2:
                    numberOfKings = 2;
                    break;
                case 3:
                case 4:
                    numberOfKings = 1;
                    break;
                default:
                    // TODO throw exception ?
                    break;
            }
            for(int i = 0; i < numberOfKings; i++){
                player.addKing(new King(player.getPlayerColor()));
            }
        }
    }

    /**
     * Pick a number of dominoes
     * @param number
     * @return
     */
    public DominoesList pickDominoes(int number) {
        // we select a part of the class dominoes array
        // TODO check if the number asked is not superior to the amount of dominoes
        DominoesList dominoesPicked = new DominoesList(this.getDominoes().subList(0, number));
        // we remove it from the class array
        this.getDominoes().subList(0, number).clear();
        // we sort and return them
        return dominoesPicked;
    }

    /**
     * Return the number of kings in the game
     * @return
     */
    public int numberKingsInGame() {
        int numberKings = 0;
        for(Player player : this.getPlayers()) {
            numberKings += player.getKings().size();
        }
        return numberKings;
    }

    /**
     * Return the next player
     * @return
     */
    public Player nextPlayer() {
        // if the nextPlayer order is not defined
        if(this.getPlayerTurns() == null) {
            this.setPlayerTurns(new ArrayList<>());
        }
        // if the nextPlayer should be randomly chose
        if(this.getPlayerTurns().size() < this.getPlayers().size()) {
            ArrayList<Player> possiblePlayers = new ArrayList<Player>(this.getPlayers());
            for(PlayerColor playerColor : this.getPlayerTurns()) {
                for(Player player : possiblePlayers) {
                    if(player.getPlayerColor().equals(playerColor)){
                        // we remove it
                        possiblePlayers.remove(player);
                    }
                }
            }
            Player player = possiblePlayers.get(Function.randInt(0, possiblePlayers.size() - 1));
            this.getPlayerTurns().add(player.getPlayerColor()); // we note that the player has been chosen
            return player;
        } else {
            // the order is already defined
            PlayerColor playerColor = this.getPlayerTurns().get(0); // we retrieve the next Player
            this.getPlayerTurns().remove(playerColor); // we remove it from the beginning
            this.getPlayerTurns().add(playerColor); // and add it at the end of the array
            for(Player player : this.getPlayers()) {
                if(player.getPlayerColor().equals(playerColor)){
                    return player; // it's the nextPlayer as he match with the next PlayerColor
                }
            }
        }
        return null; // TODO can't happen. Should throw an exception ?
    }

    
    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public DominoesList getDominoes() {
        return dominoes;
    }

    public void setDominoes(DominoesList dominoes) {
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

    public List<PlayerColor> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(List<PlayerColor> playerTurns) {
        this.playerTurns = playerTurns;
    }
}


