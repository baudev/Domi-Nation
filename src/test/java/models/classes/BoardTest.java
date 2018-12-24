package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.GameMode;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void setGreatDuelShouldReturnBoardOfSize7AndCastleAndStartTileMustBeCentered() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.THEGREATDUEL, PlayerColor.BLUE);
        assertTrue(board.getGrid().containsKey(new Position(13, 13))); // the size of the board should be 13
        assertEquals(new Position(7, 7), board.getStartTile().getPosition()); // the startTile must be centered
        assertEquals(new Position(7, 7), board.getCastle().getPosition()); // the castle must be centered
        assertEquals(PlayerColor.BLUE, board.getCastle().getColor()); // the color of the castle should be blue
    }

    @Test
    void setClassicShouldReturnBoardOfSize5AndCastleAndStartTileMustBeCentered() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.BLUE);
        assertTrue(board.getGrid().containsKey(new Position(9, 9))); // the size of the board should be 9
        assertEquals(new Position(5, 5), board.getStartTile().getPosition()); // the startTile must be centered
        assertEquals(new Position(5, 5), board.getCastle().getPosition()); // the castle must be centered
        assertEquals(PlayerColor.BLUE, board.getCastle().getColor()); // the color of the castle should be blue
    }

    @Test
    void setaddDominoShouldReturnDomino() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.BLUE);
        board.setDominoes(new ArrayList<>());
        Domino domino = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 1);
        board.addDomino(domino);
        assertSame(domino, board.getDominoes().get(0));
    }
}