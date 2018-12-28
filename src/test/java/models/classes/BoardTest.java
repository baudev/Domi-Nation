package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import mockit.Mock;
import mockit.MockUp;
import models.enums.GameMode;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import views.templates.DominoView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @BeforeAll
    private static void beforeAll() {
        new MockUp<DominoView>() {
            @Mock
            public void $init(Domino domino) {
            }
        };
    }

    @Test
    void setGreatDuelShouldReturnBoardOfSize7AndCastleAndStartTileMustBeCentered() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.THEGREATDUEL, PlayerColor.BLUE);
        assertEquals(new Position(7, 7), board.getStartTile().getPosition()); // the startTile must be centered
        assertEquals(new Position(7, 7), board.getCastle().getPosition()); // the castle must be centered
        assertEquals(PlayerColor.BLUE, board.getCastle().getColor()); // the color of the castle should be blue
    }

    @Test
    void setClassicShouldReturnBoardOfSize5AndCastleAndStartTileMustBeCentered() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.BLUE);
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