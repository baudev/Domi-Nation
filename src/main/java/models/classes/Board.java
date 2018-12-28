package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.GameMode;
import models.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

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

    public List<Position> calculateMaxGridSize() {
        // we calculate the xMin Position
        Position xMin;
        Position xExtremeLeft = this.mostLeftPosition();
        if(xExtremeLeft.getX() - 2 >= 1 && this.mostRightPosition().getX() - xExtremeLeft.getX() - 2 <= this.getMaxGridSize()) {
            xExtremeLeft.setX(xExtremeLeft.getX() - 2);
            xMin = xExtremeLeft;
        } else if(xExtremeLeft.getX() - 1 >= 1 && this.mostRightPosition().getX() - xExtremeLeft.getX() - 1 <= this.getMaxGridSize()) {
            xExtremeLeft.setX(xExtremeLeft.getX() - 1);
            xMin = xExtremeLeft;
        } else {
            xMin = xExtremeLeft;
        }

        // we calculate the xMax Position
        Position xMax;
        Position xExtremeRight = this.mostRightPosition();
        if(xExtremeRight.getX() + 2 <= 2 * getMaxGridSize() - 1 && xExtremeRight.getX() + 2 - this.mostLeftPosition().getX() <= this.getMaxGridSize()) {
            xExtremeRight.setX(xExtremeRight.getX() + 2);
            xMax = xExtremeRight;
        } else if(xExtremeRight.getX() + 1 <= 2 * getMaxGridSize() - 1 && xExtremeRight.getX() + 2 - this.mostLeftPosition().getX() <= this.getMaxGridSize()) {
            xExtremeRight.setX(xExtremeRight.getX() + 1);
            xMax = xExtremeRight;
        } else {
            xMax = xExtremeRight;
        }

        // we calculate the yMax Position
        Position yMax;
        Position yExtremeUp = this.upperPosition();
        if(yExtremeUp.getY() + 2 <= 2 * getMaxGridSize() - 1 && yExtremeUp.getY() + 2 - this.lowerPosition().getY() <= this.getMaxGridSize()) {
            yExtremeUp.setY(yExtremeUp.getY() + 2);
            yMax = yExtremeUp;
        } else if(yExtremeUp.getY() + 1 <= 2 * getMaxGridSize() - 1 && yExtremeUp.getY() + 1 - this.lowerPosition().getY() <= this.getMaxGridSize()) {
            yExtremeUp.setY(yExtremeUp.getY() + 1);
            yMax = yExtremeUp;
        } else {
            yMax = yExtremeUp;
        }

        // we calculate the yMax Position
        Position yMin;
        Position yExtremeLow = this.lowerPosition();
        if(yExtremeLow.getY() - 2 >= 1 && this.upperPosition().getY() - yExtremeLow.getY() - 2 <= this.getMaxGridSize()) {
            yExtremeLow.setY(yExtremeLow.getY() - 2);
            yMin = yExtremeLow;
        } else if(yExtremeLow.getY() - 1 >= 1 && this.upperPosition().getY() - yExtremeLow.getY() - 1 <= this.getMaxGridSize()) {
            yExtremeLow.setY(yExtremeLow.getY() - 1);
            yMin = yExtremeLow;
        } else {
            yMin = yExtremeLow;
        }
        List<Position> positionList = new ArrayList<>();
        for(int i = xMin.getX(); i <= xMax.getX(); i++) {
            for(int j = yMin.getY(); j <= yMax.getY(); j++) {
                positionList.add(new Position(i, j));
            }
        }
        return positionList;
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
    private Position upperPosition() {
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
    private Position lowerPosition() {
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
