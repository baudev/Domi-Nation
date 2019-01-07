package models.classes;

import exceptions.InvalidDominoPosition;
import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.GameMode;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import views.templates.BoardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the grid on which the {@link Player} places his {@link Domino}s.
 * It contains all the methods related to the management of all {@link Domino}s.
 * It also monitors, depending on the {@link GameMode}, that the size of the game remains in accordance with the rules.
 */
public class Board {

    private List<Domino> dominoes;
    private Castle castle;
    private StartTile startTile;
    private List<Position> grid;
    private int maxGridSize;
    private BoardView boardView;

    /**
     * Creates a {@link Board} from the {@link GameMode} and {@link PlayerColor} parameters.
     * @param gameMode      {@link GameMode} of the current {@link Game}.
     * @param playerColor   {@link PlayerColor} of the {@link Board}'s {@link Player} owner.
     * @throws MaxCrownsLandPortionExceeded If the number of crowns is not correct.
     */
    public Board(GameMode gameMode, PlayerColor playerColor) throws MaxCrownsLandPortionExceeded {
        this.setDominoes(new ArrayList<>()); // we set en empty ArrayList for the dominoes
        this.setGrid(new ArrayList<>());
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
     * Returns all possibles {@link Position} for a {@link Domino}.
     * This method first recovers all the {@link Position} available for the {@link Domino} passed as parameter, depending on its orientation.
     * Then, it checks that these {@link Position} will not make the {@link Board} exceed its regulatory size.
     * @param domino    {@link Domino} for which we want to have the possible positions.
     * @return  {@link List] of {@link Position} {@link List} containing all possible {@link Position}s for the {@link Domino} passed as parameter.
     *          All {@link List] of {@link Position}s contains only two of the latter. Indeed, as each {@link Domino} is composed of two {@link LandPortion}, a {@link Position} must be provided for each of them.
     */
    public List<List<Position>> getPossibilities(Domino domino) {
        List<List<Position>> listEmptyPlaces = this.getEmptyPlaces(domino);
        List<List<Position>> listToDelete = new ArrayList<>();
        for(List<Position> listPosition : listEmptyPlaces) {
            if(!this.isPossibleToPlaceDomino(listPosition.get(0), listPosition.get(1), domino)) { // we remove the position as a possibility
                listToDelete.add(listPosition);
            }
        }
        listEmptyPlaces.removeAll(listToDelete);

        // we impact the view
        for(List<Position> list : listEmptyPlaces) {
            Position position1 = list.get(0);
            Position position2 = list.get(1);
            boolean hasToBeInverted = false;
            // we check if the position must be inverted
            if(position1.getX() == position2.getX()) { // horizontal
                if(position1.getY() <= position2.getY()) {
                    // invert
                    hasToBeInverted = true;
                }
            }
            if(position1.getY() == position2.getY()) { // vertical
                if(position1.getX() >= position2.getX()) {
                    // invert
                    hasToBeInverted = true;
                }
            }
            if(hasToBeInverted) {
                Position tempPosition = new Position(position1.getX(), position1.getY()); // TODO copy object method
                position1 = position2;
                position2 = tempPosition;
            }
            this.getBoardView().addPossibility(position1, position2);
        }
        return listEmptyPlaces;
    }

    /**
     * Check if it's possible to place the {@link Domino} passed as parameter to the position1 and position2 according to its {@link models.enums.Rotation}.
     * @param position1 {@link Position} of one of the two {@link LandPortion} of the {@link Domino}.
     * @param position2 {@link Position} of one of the two {@link LandPortion} of the {@link Domino}.
     * @param domino    {@link Domino} whose possibility of being place at position1 and position2 is checked.
     * @return  true if the {@link Domino} can be placed to the position1 and position2.
     *          false otherwise.
     */
    // TODO take care if it's the domino positions to not pass them as references. It could be great surcharge this method
    public boolean isPossibleToPlaceDomino(Position position1, Position position2, Domino domino) {
        if(this.getLandPortion(position1) != null || this.getLandPortion(position2) != null) {
            return false; // there is already a tile
        }
        this.calculateGridMaxSize(); // TODO check if call useful there
        List<Position> maxGridSize = this.getGrid();
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
        return true;
    }

    /**
     * Return all empty {@link Position} for the {@link Domino} passed as parameter.
     * @param domino {@link Domino} for which we are looking for free locations.
     * @return  {@link List] of {@link Position} {@link List} containing all empty {@link Position}s for the {@link Domino} passed as parameter.
     *          All {@link List] of {@link Position}s contains only two of the latter. Indeed, as each {@link Domino} is composed of two {@link LandPortion}, a {@link Position} must be provided for each of them.
     */
    private List<List<Position>> getEmptyPlaces(Domino domino) {
        List<List<Position>> listEmptyPlaces = new ArrayList<>();
        this.calculateGridMaxSize(); // TODO not every time
        List<Position> list1SamePortionType = this.getAllTilesOfType(domino.getLeftPortion().getLandPortionType());
        List<Position> list2SamePortionType = this.getAllTilesOfType(domino.getRightPortion().getLandPortionType());
        if(domino.isHorizontal()) {
            for(Position position : list1SamePortionType) {
                Position rightSameY = new Position(position.getX() + 1, position.getY());
                Position rightSameY2 = new Position(position.getX() + 2, position.getY());
                isTherePlace(listEmptyPlaces, rightSameY, rightSameY2);

                Position rightUpY = new Position(position.getX(), position.getY() + 1);
                Position rightUpY2 = new Position(position.getX() + 1, position.getY() + 1);
                isTherePlace(listEmptyPlaces, rightUpY, rightUpY2);

                Position rightLowY = new Position(position.getX(), position.getY() - 1);
                Position rightLowY2 = new Position(position.getX() + 1, position.getY() - 1);
                isTherePlace(listEmptyPlaces, rightLowY, rightLowY2);
            }
            for(Position position : list2SamePortionType) {
                Position leftSameY = new Position(position.getX() - 2, position.getY());
                Position leftSameY2 = new Position(position.getX() - 1, position.getY());
                isTherePlace(listEmptyPlaces, leftSameY, leftSameY2);

                Position leftUpY = new Position(position.getX() - 1, position.getY() + 1);
                Position leftUpY2 = new Position(position.getX(), position.getY() + 1);
                isTherePlace(listEmptyPlaces, leftUpY, leftUpY2);

                Position leftLowY = new Position(position.getX() - 1, position.getY() - 1);
                Position leftLowY2 = new Position(position.getX(), position.getY() - 1);
                isTherePlace(listEmptyPlaces, leftLowY, leftLowY2);
            }
        } else { // vertical
            for(Position position : list1SamePortionType) {
                Position upSameX = new Position(position.getX(), position.getY() + 2);
                Position upSameX2 = new Position(position.getX(), position.getY() + 1);
                isTherePlace(listEmptyPlaces, upSameX, upSameX2);

                Position leftUpX = new Position(position.getX() - 1, position.getY() + 1);
                Position leftUpX2 = new Position(position.getX() - 1, position.getY());
                isTherePlace(listEmptyPlaces, leftUpX, leftUpX2);

                Position rightUpX = new Position(position.getX() + 1, position.getY() + 1);
                Position rightUpX2 = new Position(position.getX() + 1, position.getY());
                isTherePlace(listEmptyPlaces, rightUpX, rightUpX2);
            }
            for(Position position : list2SamePortionType) {
                Position lowSameX = new Position(position.getX(), position.getY() - 1);
                Position lowSameX2 = new Position(position.getX(), position.getY() - 2);
                isTherePlace(listEmptyPlaces, lowSameX, lowSameX2);

                Position leftLowX = new Position(position.getX() - 1, position.getY());
                Position leftLowX2 = new Position(position.getX() - 1, position.getY() - 1);
                isTherePlace(listEmptyPlaces, leftLowX, leftLowX2);

                Position rightLowX = new Position(position.getX() + 1, position.getY());
                Position rightLowX2 = new Position(position.getX() + 1, position.getY() - 1);
                isTherePlace(listEmptyPlaces, rightLowX, rightLowX2);
            }
        }
        return listEmptyPlaces;
    }

    /**
     * Useful method for {@link Board#getEmptyPlaces(Domino)}. It checks if the position1 and position2 is free, and add them to listEmptyPlaces if it's the case
     * @param listEmptyPlaces {@link List} where the position1 and position2 must be added if they are free.
     * @param position1 {@link Position} whose occupation is being checked.
     * @param position2 {@link Position} whose occupation is being checked.
     */
    private void isTherePlace(List<List<Position>> listEmptyPlaces, Position position1, Position position2) {
        if(this.getLandPortion(position1) == null && this.getLandPortion(position2) == null) {
            List<Position> tempList = new ArrayList<>();
            tempList.add(position1);
            tempList.add(position2);
            listEmptyPlaces.add(tempList);
        }
    }

    /**
     * Returns the {@link LandPortion} situated on the {@link Position} given as parameter.
     * @param position {@link Position} in which we want to know what's there.
     * @return {@link LandPortion} if there is one located at the position specified as parameter.
     *          Null if nothing was found to the position specified.
     */
    public LandPortion getLandPortion(Position position) {
        if(position.getX() == this.getMaxGridSize() && position.getY() == this.getMaxGridSize()) {
            return getStartTile();
        } else {
            for(Domino domino : this.getDominoes()) {
                if(domino.getLeftPortion().getPosition() != null) {
                    if (position.getX() == domino.getLeftPortion().getPosition().getX() && position.getY() == domino.getLeftPortion().getPosition().getY()) {
                        return domino.getLeftPortion();
                    }
                }
                if(domino.getRightPortion().getPosition() != null) {
                    if (position.getX() == domino.getRightPortion().getPosition().getX() && position.getY() == domino.getRightPortion().getPosition().getY()) {
                        return domino.getRightPortion();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a {@link List} of all {@link Position}s having {@link LandPortion} with the same {@link LandPortionType} passed as parameter.
     * @param landPortionType {@link LandPortionType} we are looking for in the {@link Board}.
     * @return  {@link List} of {@link Position} having the same {@link LandPortionType}.
     */
    private List<Position> getAllTilesOfType(LandPortionType landPortionType) {
        List<Position> positionList = new ArrayList<>();
        positionList.add(this.getStartTile().getPosition()); // startTile is connectible to all landPortionType
        for(Domino domino : this.getDominoes()) {
            if(domino.getRightPortion().getPosition() != null && domino.getLeftPortion().getPosition() != null) {
                if (domino.getLeftPortion().getLandPortionType() == landPortionType) {
                    positionList.add(domino.getLeftPortion().getPosition());
                }
                if (domino.getRightPortion().getLandPortionType() == landPortionType) {
                    positionList.add(domino.getRightPortion().getPosition());
                }
            }
        }
        return positionList;
    }

    /**
     * Calculates the maxGridSize possible while respecting all rules.
     * This updates the grid attribute. It calculates the {@link Position}s of the extremes of the {@link Board}.
     * This function allows you to know if you can place a {@link Domino} for example in a {@link Position} without exceeding the size of the regulatory {@link Board}.
     */
    public void calculateGridMaxSize() {
        // we calculate the xMin Position
        Position xMin;
        Position xExtremeLeft = new Position(this.mostLeftPosition().getX(), this.mostLeftPosition().getY());
        if(xExtremeLeft.getX() - 2 >= 1 && this.mostRightPosition().getX() - (xExtremeLeft.getX() - 2) < this.getMaxGridSize()) {
            xExtremeLeft.setX(xExtremeLeft.getX() - 2);
            xMin = xExtremeLeft;
        } else if(xExtremeLeft.getX() - 1 >= 1 && this.mostRightPosition().getX() - (xExtremeLeft.getX() - 1) < this.getMaxGridSize()) {
            xExtremeLeft.setX(xExtremeLeft.getX() - 1);
            xMin = xExtremeLeft;
        } else {
            xMin = xExtremeLeft;
        }

        // we calculate the xMax Position
        Position xMax;
        Position xExtremeRight = new Position(this.mostRightPosition().getX(), this.mostRightPosition().getY());
        if(xExtremeRight.getX() + 2 <= 2 * getMaxGridSize() - 1 && xExtremeRight.getX() + 2 - this.mostLeftPosition().getX() < this.getMaxGridSize()) {
            xExtremeRight.setX(xExtremeRight.getX() + 2);
            xMax = xExtremeRight;
        } else if(xExtremeRight.getX() + 1 <= 2 * getMaxGridSize() - 1 && xExtremeRight.getX() + 2 - this.mostLeftPosition().getX() < this.getMaxGridSize()) {
            xExtremeRight.setX(xExtremeRight.getX() + 1);
            xMax = xExtremeRight;
        } else {
            xMax = xExtremeRight;
        }

        // we calculate the yMax Position
        Position yMax;
        Position yExtremeUp = new Position(this.highestPosition().getX(), this.highestPosition().getY());
        if(yExtremeUp.getY() + 2 <= 2 * getMaxGridSize() - 1 && yExtremeUp.getY() + 2 - this.lowerPosition().getY() < this.getMaxGridSize()) {
            yExtremeUp.setY(yExtremeUp.getY() + 2);
            yMax = yExtremeUp;
        } else if(yExtremeUp.getY() + 1 <= 2 * getMaxGridSize() - 1 && yExtremeUp.getY() + 1 - this.lowerPosition().getY() < this.getMaxGridSize()) {
            yExtremeUp.setY(yExtremeUp.getY() + 1);
            yMax = yExtremeUp;
        } else {
            yMax = yExtremeUp;
        }
        // we calculate the yMax Position
        Position yMin;
        Position yExtremeLow = new Position(this.lowerPosition().getX(), this.lowerPosition().getY());
        if(yExtremeLow.getY() - 2 >= 1 && this.highestPosition().getY() - (yExtremeLow.getY() - 2) < this.getMaxGridSize()) {
            yExtremeLow.setY(yExtremeLow.getY() - 2);
            yMin = yExtremeLow;
        } else if(yExtremeLow.getY() - 1 >= 1 && this.highestPosition().getY() - (yExtremeLow.getY() - 1) < this.getMaxGridSize()) {
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
        this.setGrid(positionList);
    }

    /**
     * Return the mostLeft {@link Position}.
     * @return {@link Position} of the most left element on the {@link Board}.
     */
    public Position mostLeftPosition() {
        Position mostLeft = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition() != null) {
                if (domino.getLeftPortion().getPosition().getX() < mostLeft.getX()) {
                    mostLeft = domino.getLeftPortion().getPosition();
                }
            }
            if(domino.getRightPortion().getPosition() != null) {
                if (domino.getRightPortion().getPosition().getX() < mostLeft.getX()) { // as the domino can rotate
                    mostLeft = domino.getRightPortion().getPosition();
                }
            }
        }
        return mostLeft;
    }

    /**
     * Return the mostRight {@link Position}.
     * @return {@link Position} of the most right element on the {@link Board}.
     */
    public Position mostRightPosition() {
        Position mostRight = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition() != null) {
                if (domino.getLeftPortion().getPosition().getX() > mostRight.getX()) {
                    mostRight = domino.getLeftPortion().getPosition();
                }
            }
            if(domino.getRightPortion().getPosition() != null) {
                if (domino.getRightPortion().getPosition().getX() > mostRight.getX()) { // as the domino can rotate
                    mostRight = domino.getRightPortion().getPosition();
                }
            }
        }
        return mostRight;
    }

    /**
     * Return the highest {@link Position}.
     * @return {@link Position} of the highest element on the {@link Board}.
     */
    public Position highestPosition() {
        Position upperPosition = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition() != null) {
                if (domino.getLeftPortion().getPosition().getY() > upperPosition.getY()) {
                    upperPosition = domino.getLeftPortion().getPosition();
                }
            }
            if(domino.getRightPortion().getPosition() != null) {
                if (domino.getRightPortion().getPosition().getY() > upperPosition.getY()) { // as the domino can rotate
                    upperPosition = domino.getRightPortion().getPosition();
                }
            }
        }
        return upperPosition;
    }

    /**
     * Return the lowest {@link Position}.
     * @return {@link Position} of the lowest element on the {@link Board}.
     */
    public Position lowerPosition() {
        // TODO rename in lowest
        Position lowerPosition = new Position(this.getMaxGridSize(), this.getMaxGridSize());
        for(Domino domino : this.getDominoes()) {
            if(domino.getLeftPortion().getPosition() != null) {
                if (domino.getLeftPortion().getPosition().getY() < lowerPosition.getY()) {
                    lowerPosition = domino.getLeftPortion().getPosition();
                }
            }
            if(domino.getRightPortion().getPosition() != null) {
                if (domino.getRightPortion().getPosition().getY() < lowerPosition.getY()) { // as the domino can rotate
                    lowerPosition = domino.getRightPortion().getPosition();
                }
            }
        }
        return lowerPosition;
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns all {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     * {@link Domino} can have a {@link Position} or not.
     * @return  {@link List} of {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     */
    public List<Domino> getDominoes() {
        return dominoes;
    }

    /**
     * Sets a list of {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     * Take care, it erases all previous selected {@link Domino}s.
     * @param dominoes  New list of {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     */
    public void setDominoes(List<Domino> dominoes) {
        this.dominoes = dominoes;
    }

    /**
     * Adds a {@link Domino} to the list of {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     * If the {@link Domino} has a {@link Position}, this checks if it can be added on the {@link Board} by respecting the connection rules.
     * This impacts the view of the {@link Board} too.
     * @param domino {@link Domino} which the {@link Player} wants to add.
     * @throws InvalidDominoPosition    If the {@link Domino} cant be added to the {@link Board}'s {@link Player} according to it's {@link Position}.
     */
    public void addDomino(Domino domino) throws InvalidDominoPosition {
        if(domino.getLeftPortion().getPosition() != null && domino.getRightPortion().getPosition() != null) {
            if(!isPossibleToPlaceDomino(new Position(domino.getLeftPortion().getPosition().getX(), domino.getLeftPortion().getPosition().getY()), new Position(domino.getRightPortion().getPosition().getX(), domino.getRightPortion().getPosition().getY()), domino)) {
                throw new InvalidDominoPosition();
            }
            this.getBoardView().addDomino(domino);
        }
        this.dominoes.add(domino);
    }

    /**
     * Removes a {@link Domino} to the list of {@link Domino}s selected by the {@link Board}'s {@link Player} owner.
     * Take care, this doesn't impact the view of the {@link Board}. If you want to, see {@link views.templates.DominoesListView#removeDominoView(Domino)}.
     * @param domino {@link Domino} which the {@link Player} wants to remove.
     */
    public void removeDomino(Domino domino) {
        this.dominoes.remove(domino);
    }

    /**
     * Gets the {@link Castle} of the {@link Player}.
     * @return {@link Castle} of the {@link Player}.
     */
    public Castle getCastle() {
        return castle;
    }

    /**
     * Sets the {@link Castle} of the {@link Player}.
     * @param castle {@link Castle} of the {@link Player} to be set.
     */
    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    /**
     * Gets the {@link StartTile} of the {@link Player}.
     * @return  {@link StartTile} of the {@link Player}.
     */
    public StartTile getStartTile() {
        return startTile;
    }

    /**
     * Sets the {@link StartTile} of the {@link Player}.
     * @param startTile {@link StartTile} of the {@link Player} to be set.
     */
    public void setStartTile(StartTile startTile) {
        this.startTile = startTile;
    }

    /**
     * Gets the grid of the {@link Board}.
     * This is a list of {@link Position}s on which {@link Domino}s are allowed to be placed on it. Outside this list, the {@link Domino} cannot be placed without violating the regulatory size.
     * @return {@link List} of {@link Position}s on which {@link Domino}s are allowed to be placed on it.
     * @see #calculateGridMaxSize()
     */
    public List<Position> getGrid() {
        return grid;
    }

    /**
     * Sets the grid of the {@link Board}.
     * This is a list of {@link Position}s on which {@link Domino}s are allowed to be placed on it. Outside this list, the {@link Domino} cannot be placed without violating the regulatory size.
     * @param grid {@link List} of {@link Position}s on which {@link Domino}s are allowed to be placed on it.
     * @see #calculateGridMaxSize()
     */
    public void setGrid(List<Position> grid) {
        this.grid = grid;
    }

    /**
     * Returns the maximum {@link Board} size allowed according to the {@link GameMode}.
     * @return integer corresponding to the maximum {@link Board} size allowed.
     */
    public int getMaxGridSize() {
        return maxGridSize;
    }

    /**
     * Sets the maximum {@link Board} size allowed according to the {@link GameMode}.
     * @param maxGridSize integer corresponding to the maximum {@link Board} size allowed.
     */
    public void setMaxGridSize(int maxGridSize) {
        this.maxGridSize = maxGridSize;
    }

    /**
     * Gets the JavaFX {@link BoardView} linked to the {@link Board}.
     * @return  if the view was not created, a new instance of {@link BoardView}.
     *          if the view was already created, the associated instance of {@link BoardView}.
     */
    public BoardView getBoardView() {
        if(boardView == null) {
            this.setBoardView(new BoardView(this));
        }
        return boardView;
    }

    /**
     * Sets the JavaFX {@link BoardView} linked to the {@link Board}.
     * @param  boardView JavaFX {@link BoardView} that has to be linked to the {@link Board}.
     */
    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }
}
