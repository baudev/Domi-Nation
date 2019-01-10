package models.classes;

import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a {@link Player} in the {@link Game}.
 * Each player has a {@link Board}, {@link King}s and a {@link PlayerColor}.
 */
public class Player {

    private List<King> kings;
    private PlayerColor playerColor;
    private Board board;

    /**
     * Generates a {@link Player} with the playerColor passed as parameter.
     * @param playerColor   {@link PlayerColor} of the {@link Player} generated.
     */
    public Player(PlayerColor playerColor) {
        this.setPlayerColor(playerColor);
        this.setKings(new ArrayList<>());
    }

    /**
     * Returns all not placed {@link Player}'s {@link King}s.
     * @return {@link List} containing all not placed {@link Player}'s {@link King}s.
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
     * Returns the {@link Domino} of the player with the corresponding king passed as parameter.
     * @param king {@link King} for which we are searching on which {@link Domino} it is placed.
     * @return  {@link Domino} on which was place the king.
     *          null if the king was placed on any {@link Domino}.
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
     * Returns a random {@link Player}'s {@link Domino} without {@link Position}.
     * @return  {@link Domino} without position
     *          null if all {@link Player}'s {@link Domino} have a {@link Position}.
     */
    public Domino getDominoWithoutPosition() {
        for(Domino domino : this.getBoard().getDominoes()) {
            if(domino.getLeftPortion().getPosition() == null || domino.getRightPortion().getPosition() == null) {
                return domino;
            }
        }
        return null;
    }

    /**
     * Return the smallest number value of unplaced {@link Domino}s.
     * @return  The smallest number value of unplaced {@link Domino}s.
     * @see Domino#getNumber()
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

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns all {@link Player}'s {@link King}s.
     * @return All {@link Player}'s {@link King}s.
     */
    public List<King> getKings() {
        return kings;
    }

    /**
     * Sets all {@link Player}' {@link King}s.
     * Take care, ir replaces all previous {@link Player}'s {@link King}s.
     * @param kings {@link List} of {@link Player}'s {@link King}s to be set.
     */
    public void setKings(List<King> kings) {
        this.kings = kings;
    }

    /**
     * Add a {@link Player}'s {@link King}.
     * @param king {@link King} to be added to {@link Player}'s ones.
     */
    public void addKing(King king) {
        this.kings.add(king);
    }

    /**
     * Gets the {@link PlayerColor} of the {@link Player}.
     * @return  The {@link PlayerColor} of the {@link Player}.
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the {@link PlayerColor} of the {@link Player}.
     * @param playerColor The {@link PlayerColor} of the {@link Player} to be set.
     */
    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Returns the {@link Board} of the {@link Player}.
     * @return  The {@link Board} of the {@link Player}.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the {@link Board} of the {@link Player}.
     * @param board {@link Board} of the {@link Player} to be set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
