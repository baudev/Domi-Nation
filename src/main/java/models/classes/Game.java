package models.classes;



import models.enums.GameMode;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Domino> dominoes;
    private List<Player> players;
    private GameMode gameMode;

    public Game() {
        this.setDominoes(new ArrayList<>());
        this.setPlayers(new ArrayList<>());
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


