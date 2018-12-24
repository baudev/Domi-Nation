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
    private Map<Position, LandPortion> grid;

    public Board(GameMode gameMode, PlayerColor playerColor) throws MaxCrownsLandPortionExceeded {
        this.setDominoes(new ArrayList<>()); // we set en empty ArrayList for the dominoes
        this.setGrid(new HashMap<Position, LandPortion>());
        this.generateGrid(gameMode, playerColor); // generate the grid with the different elements such as castle of startTile
    }

    private void generateGrid(GameMode gameMode, PlayerColor playerColor) throws MaxCrownsLandPortionExceeded {
        int sizeGrid;
        switch (gameMode) {
            case THEGREATDUEL:
                sizeGrid = 7;
                break;
            default:
                sizeGrid = 5;
                break;
        }
        int gridMaximumAxis = 2 * sizeGrid;
        // TODO is it useful to generate all position and to set them to null ?
        for (int i = 1; i < gridMaximumAxis; i++) {
            for (int j = 1; j < gridMaximumAxis; j++) {
                this.getGrid().put(new Position(i, j), null);
            }
        }
        this.setStartTile(new StartTile());
        // we put the startTile to the center of the grid
        this.getStartTile().setPosition(new Position(sizeGrid, sizeGrid));
        this.getGrid().put(new Position(sizeGrid, sizeGrid), this.getStartTile());
        // we generate the castle
        this.setCastle(new Castle(playerColor, new Position(sizeGrid, sizeGrid)));
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

    public Map<Position, LandPortion> getGrid() {
        return grid;
    }

    public void setGrid(Map<Position, LandPortion> grid) {
        this.grid = grid;
    }
}
