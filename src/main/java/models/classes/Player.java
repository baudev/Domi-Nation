package models.classes;

import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Domino> dominoes;
    private List<King> kings;
    private Castle castle;
    private StartTile startTile;
    private PlayerColor playerColor;

    // TODO Add user board and other useful attributes

    public Player(PlayerColor playerColor) {
        this.setPlayerColor(playerColor);
        this.dominoes = new ArrayList<>();
        this.kings = new ArrayList<>();
    }

    /**
     * Select the player's color
     * @param playerColors
     * @param playerColor
     */
    public void chooseColor(ArrayList<PlayerColor> playerColors, PlayerColor playerColor){
        this.setPlayerColor(playerColor);   //  set the color that was chosen
        playerColors.remove(playerColor);   //  remove the color in the list
    }

    /**
     * Select the player's start tile
     * @param startTiles
     * @param startTile
     */
    public void chooseStartTile(ArrayList<StartTile> startTiles, StartTile startTile){
        this.setStartTile(startTile);   //  set the tile that was chosen
        startTiles.remove(startTile);   //  remove the tile in the liste
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

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public void addKing(King king) {
        this.kings.add(king);
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public StartTile getStartTile() {
        return startTile;
    }

    public void setStartTile(StartTile startTile) {
        this.startTile = startTile;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

}
