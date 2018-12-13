package models.classes;

import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<King> kings;
    private PlayerColor playerColor;
    private Board board;

    public Player(PlayerColor playerColor) {
        this.setPlayerColor(playerColor);
        this.kings = new ArrayList<>();
        this.board = new Board();
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public List<King> getKings() {
        return kings;
    }

    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    public void addKing(King king) {
        this.kings.add(king);
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
