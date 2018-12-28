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
        this.setKings(new ArrayList<>());
    }

    /**
     * Return all not placed kings
     * @return
     */
    public List<King> getNotPlacedKings() {
        List<King> kingList = new ArrayList<>();
        for(King king : this.getKings()) {
            if(!king.isPlaced()) {
                kingList.add(king);
            }
        }
        return kingList;
    }

    /**
     * Return the domino of the player with the corresponding king passed as parameter
     * @param king
     * @return
     */
    public Domino getDominoWithKing(King king) {
        for(Domino domino : this.getBoard().getDominoes()) {
            if(domino.getKing() != null) {
                if(domino.getKing() == king) {
                    return domino;
                }
            }
        }
        return null;
    }

    /**
     * Return the smallest domino number value of unplaced ones
     * @return
     */
    public int getSmallestNumberOfUnPlacedDominoes() {
        int smallValue = 48;
        for(Domino domino : getBoard().getDominoes()) {
            if(domino.getRightPortion().getPosition() == null && domino.getLeftPortion().getPosition() == null) {
                if (domino.getNumber() <= smallValue) {
                    smallValue = domino.getNumber();
                }
            }
        }
        return smallValue;
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
