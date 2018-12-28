package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import mockit.Mock;
import mockit.MockUp;
import models.enums.GameMode;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import models.enums.Rotation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import views.templates.DominoView;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void calculateEmptyGridSizeInNormalGameMode() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.PINK);
        List<Position> positionList = board.calculateMaxGridSize();
        assertTrue(positionList.contains(new Position(3, 3)));
        assertTrue(positionList.contains(new Position(3, 7)));
        assertTrue(positionList.contains(new Position(7, 7)));
        assertTrue(positionList.contains(new Position(7, 3)));
    }

    @Test
    void isPossibleToPlaceHorizontalDominoNearStartTile() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.YELLOW);
        Domino domino = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 1);
        domino.setRotation(Rotation.INVERSE);
        assertTrue(board.isPossibleToPlaceDomino(new Position(5 - 2, 5), new Position(5 - 1, 5), domino));
    }

    @Test
    void isPossibleToPlaceVerticalDominoNearStartTile() throws MaxCrownsLandPortionExceeded {
        Board board = new Board(GameMode.CLASSIC, PlayerColor.YELLOW);
        Domino domino = new Domino(new LandPortion(1, LandPortionType.CHAMPS), new LandPortion(1, LandPortionType.MINE), 1);
        domino.setRotation(Rotation.LEFT);
        assertTrue(board.isPossibleToPlaceDomino(new Position(5, 5 + 2), new Position(5, 5 + 1), domino));
    }
}