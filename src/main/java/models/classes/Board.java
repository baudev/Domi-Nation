package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.GameMode;
import models.enums.LandPortionType;
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

    /**
     * Check if it's possible to place the domino pass as parameter to the position1 and position2 according to its rotation
     * @param position1
     * @param position2
     * @param domino
     * @return
     */
    public boolean isPossibleToPlaceDomino(Position position1, Position position2, Domino domino) {
        if(this.getLandPortion(position1) != null || this.getLandPortion(position2) != null) {
            return false; // there is already a tile
        }
        List<Position> maxGridSize = this.calculateMaxGridSize();
        if(!maxGridSize.contains(position1) || !maxGridSize.contains(position2)) { // the position are not in the possibilities of the maxGrid
            return false;
        }
        if(position1.equals(position2)) {
            return false;
        }
        if(position1.getX() != position2.getX() && position1.getY() != position2.getY()) {
            return false;
        }
        if(position1.getY() != position2.getY() && domino.isHorizontal()) {
            return false;
        }
        if(position1.getX() != position2.getX() && !domino.isHorizontal()) {
            return false;
        }
        if((position1.getX() == position2.getX() && position1.getX() > position2.getX()) || (position1.getY() == position2.getY() && position1.getY() < position2.getY())) { // we swap the objects
            Position position_temp = new Position(position1.getX(), position1.getY());
            position1 = new Position(position2.getX(), position2.getY());
            position2 = new Position(position_temp.getX(), position_temp.getY());
        }
        if(domino.isHorizontal()) { // TODO useless condition
            LandPortion landPortion1ToCompare = null;
            LandPortion landPortion2ToCompare = null;
            switch (domino.getRotation()) {
                case NORMAL:
                    landPortion1ToCompare = domino.getLeftPortion();
                    landPortion2ToCompare = domino.getRightPortion();
                    break;
                case INVERSE:
                    landPortion1ToCompare = domino.getRightPortion();
                    landPortion2ToCompare = domino.getLeftPortion();
                    break;
            }
            LandPortion leftLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX() - 1, position1.getY()));
            LandPortion upLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX(), position1.getY() + 1));
            LandPortion lowLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX(), position1.getY() - 1));
            if(leftLandPortionToCheck1 != null) {
                if(leftLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && leftLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(upLandPortionToCheck1 != null) {
                if(upLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && upLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(lowLandPortionToCheck1 != null) {
                if(lowLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && lowLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            LandPortion rightLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX() + 1, position2.getY()));
            LandPortion upLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX(), position2.getY() + 1));
            LandPortion lowLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX(), position2.getY() - 1));
            if(rightLandPortionToCheck2 != null) {
                if(rightLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && rightLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(upLandPortionToCheck2 != null) {
                if(upLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && upLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(lowLandPortionToCheck2 != null) {
                if(lowLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && lowLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            return true;
        } else {
            LandPortion landPortion1ToCompare = null;
            LandPortion landPortion2ToCompare = null;
            switch (domino.getRotation()) {
                case RIGHT:
                    landPortion1ToCompare = domino.getLeftPortion();
                    landPortion2ToCompare = domino.getRightPortion();
                    break;
                case LEFT:
                    landPortion1ToCompare = domino.getRightPortion();
                    landPortion2ToCompare = domino.getLeftPortion();
                    break;
            }
            LandPortion leftLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX() - 1, position1.getY()));
            LandPortion rightLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX() + 1, position1.getY()));
            LandPortion upLandPortionToCheck1 = this.getLandPortion(new Position(position1.getX(), position1.getY() + 1));
            if(leftLandPortionToCheck1 != null) {
                if(leftLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && leftLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(rightLandPortionToCheck1 != null) {
                if(rightLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && rightLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(upLandPortionToCheck1 != null) {
                if(upLandPortionToCheck1.getLandPortionType() != landPortion1ToCompare.getLandPortionType() && upLandPortionToCheck1.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            LandPortion rightLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX() + 1, position2.getY()));
            LandPortion leftLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX() - 1, position2.getY()));
            LandPortion lowLandPortionToCheck2 = this.getLandPortion(new Position(position2.getX(), position2.getY() - 1));
            if(rightLandPortionToCheck2 != null) {
                if(rightLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && rightLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(leftLandPortionToCheck2 != null) {
                if(leftLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && leftLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            if(lowLandPortionToCheck2 != null) {
                if(lowLandPortionToCheck2.getLandPortionType() != landPortion2ToCompare.getLandPortionType() && lowLandPortionToCheck2.getLandPortionType() != LandPortionType.TOUS) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Return the LandPortion situated on the position given as parameter.
     * @param position
     * @return LandPortion|null
     */
    private LandPortion getLandPortion(Position position) {
        if(position.getX() == this.getMaxGridSize() && position.getY() == this.getMaxGridSize()) {
            return getStartTile();
        } else {
            for(Domino domino : this.getDominoes()) {
                if(position.getX() == domino.getLeftPortion().getPosition().getX() && position.getY() == domino.getLeftPortion().getPosition().getY()) {
                    return domino.getLeftPortion();
                }
                if(position.getX() == domino.getRightPortion().getPosition().getX() && position.getY() == domino.getRightPortion().getPosition().getY()) {
                    return domino.getRightPortion();
                }
            }
        }
        return null;
    }

    /**
     * Return all empty places according to the domino rotation and LandPortionType of the two LandPortions.
     * @param domino
     * @return
     */
    public List<List<Position>> getEmptyPlaces(Domino domino) {
        List<List<Position>> listEmptyPlaces = new ArrayList<>();
        List<Position> sameTypePortion1PositionList = new ArrayList<>();
        List<Position> sameTypePortion2PositionList = new ArrayList<>();
        switch (domino.getRotation()) {
            case NORMAL:
            case RIGHT:
                sameTypePortion1PositionList = this.getAllTilesOfType(domino.getLeftPortion().getLandPortionType());
                sameTypePortion2PositionList = this.getAllTilesOfType(domino.getRightPortion().getLandPortionType());
                break;
            case INVERSE:
            case LEFT:
                sameTypePortion1PositionList = this.getAllTilesOfType(domino.getRightPortion().getLandPortionType());
                sameTypePortion2PositionList = this.getAllTilesOfType(domino.getLeftPortion().getLandPortionType());
                break;
        }
        List<Position> maxGridSize = this.calculateMaxGridSize();
        switch (domino.getRotation()) {
            case NORMAL:
            case INVERSE:
                for (Position position : sameTypePortion1PositionList) {
                    Position right1Position = new Position(position.getX() + 1, position.getY());
                    Position right2Position = new Position(position.getX() + 2, position.getY());
                    if (maxGridSize.contains(right1Position) && maxGridSize.contains(right2Position)) { // TODO make redundent code in a function ? Make comprehension harder...
                        if (this.getLandPortion(right1Position) == null && this.getLandPortion(right2Position) == null) {
                            List<Position> tempList = new ArrayList<>();
                            tempList.add(right1Position);
                            tempList.add(right2Position);
                            listEmptyPlaces.add(tempList);
                        }
                    }
                }
                for (Position position : sameTypePortion2PositionList) {
                    Position left1Position = new Position(position.getX() - 2, position.getY());
                    Position left2Position = new Position(position.getX() - 1, position.getY());
                    if (maxGridSize.contains(left1Position) && maxGridSize.contains(left2Position)) {
                        if (this.getLandPortion(left1Position) == null && this.getLandPortion(left2Position) == null) {
                            List<Position> tempList = new ArrayList<>();
                            tempList.add(left1Position);
                            tempList.add(left2Position);
                            listEmptyPlaces.add(tempList);
                        }
                    }
                }
                break;
            case RIGHT:
            case LEFT:
                for (Position position : sameTypePortion1PositionList) {
                    Position right1Position = new Position(position.getX(), position.getY() - 1);
                    Position right2Position = new Position(position.getX(), position.getY() - 2);
                    if (maxGridSize.contains(right1Position) && maxGridSize.contains(right2Position)) {
                        if (this.getLandPortion(right1Position) == null && this.getLandPortion(right2Position) == null) {
                            List<Position> tempList = new ArrayList<>();
                            tempList.add(right1Position);
                            tempList.add(right2Position);
                            listEmptyPlaces.add(tempList);
                        }
                    }
                }
                for (Position position : sameTypePortion2PositionList) {
                    Position left1Position = new Position(position.getX(), position.getY() + 2);
                    Position left2Position = new Position(position.getX(), position.getY() + 1);
                    if (maxGridSize.contains(left1Position) && maxGridSize.contains(left2Position)) {
                        if (this.getLandPortion(left1Position) == null && this.getLandPortion(left2Position) == null) {
                            List<Position> tempList = new ArrayList<>();
                            tempList.add(left1Position);
                            tempList.add(left2Position);
                            listEmptyPlaces.add(tempList);
                        }
                    }
                }
        }
        return listEmptyPlaces;
    }

    /**
     * Return a list of all positions having LandPortion with the same type passed as parameter
     * @param landPortionType
     * @return
     */
    private List<Position> getAllTilesOfType(LandPortionType landPortionType) {
        List<Position> positionList = new ArrayList<>();
        positionList.add(this.getStartTile().getPosition());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getLandPortionType() == landPortionType) {
                positionList.add(domino.getLeftPortion().getPosition());
            }
            if(domino.getRightPortion().getLandPortionType() == landPortionType) {
                positionList.add(domino.getRightPortion().getPosition());
            }
        }
        return positionList;
    }

    /**
     * Calculate the maxGridSize possible while respecting all rules
     * @return
     */
    public List<Position> calculateMaxGridSize() {
        // TODO optimize with saving results
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
