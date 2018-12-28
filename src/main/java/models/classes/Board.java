package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.GameMode;
import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private List<Domino> dominoes;
    private Castle castle;
    private StartTile startTile;
    private List<Position> grid;
    private int maxGridSize;

    public Board(GameMode gameMode, PlayerColor playerColor) throws MaxCrownsLandPortionExceeded {
        this.setDominoes(new ArrayList<>()); // we set en empty ArrayList for the dominoes
        switch (gameMode) {
            case THEGREATDUEL:
                maxGridSize = 7;
                break;
            default:
                maxGridSize = 5;
                break;
        }
        this.setStartTile(new StartTile());
        // we put the startTile to the center of the grid
        this.getStartTile().setPosition(new Position(maxGridSize, maxGridSize));
        // we generate the castle
        this.setCastle(new Castle(playerColor, new Position(maxGridSize, maxGridSize)));
    }

    /**
     * Return the mostLeft position
     * @return
     */
    private Position mostLeftPosition() {
        Position mostLeft = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition().getX() < mostLeft.getX()) {
                mostLeft = domino.getLeftPortion().getPosition();
            }
            if(domino.getRightPortion().getPosition().getX() < mostLeft.getX()) { // as the domino can rotate
                mostLeft = domino.getRightPortion().getPosition();
            }
        }
        return mostLeft;
    }

    /**
     * Return the most right position
     * @return
     */
    private Position mostRightPosition() {
        Position mostRight = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition().getX() > mostRight.getX()) {
                mostRight = domino.getLeftPortion().getPosition();
            }
            if(domino.getRightPortion().getPosition().getX() > mostRight.getX()) { // as the domino can rotate
                mostRight = domino.getRightPortion().getPosition();
            }
        }
        return mostRight;
    }

    /**
     * Return the upper position
     * @return
     */
    private Position UpperPosition() {
        Position upperPosition = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition().getY() > upperPosition.getY()) {
                upperPosition = domino.getLeftPortion().getPosition();
            }
            if(domino.getRightPortion().getPosition().getY() > upperPosition.getY()) { // as the domino can rotate
                upperPosition = domino.getRightPortion().getPosition();
            }
        }
        return upperPosition;
    }

    /**
     * Return the lower position
     * @return
     */
    private Position LowerPosition() {
        Position lowerPosition = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition().getY() < lowerPosition.getY()) {
                lowerPosition = domino.getLeftPortion().getPosition();
            }
            if(domino.getRightPortion().getPosition().getY() < lowerPosition.getY()) { // as the domino can rotate
                lowerPosition = domino.getRightPortion().getPosition();
            }
        }
        return lowerPosition;
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

    public List<Position> getGrid() {
        return grid;
    }

    public void setGrid(List<Position> grid) {
        this.grid = grid;
    }

    public int getMaxGridSize() {
        return maxGridSize;
    }

    public void setMaxGridSize(int maxGridSize) {
        this.maxGridSize = maxGridSize;
    }
}
