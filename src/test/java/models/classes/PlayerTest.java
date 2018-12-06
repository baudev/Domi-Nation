package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.LandPortionType;
import models.enums.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static Player player;

    @BeforeAll
    private static void beforeAll() {
        player = new Player(PlayerColor.BLUE);
    }

    @Test
    void getDominoes() {
        player.setDominoes(new ArrayList<>());
        assertEquals(0, player.getDominoes().size());
    }

    @Test
    void addDomino() throws MaxCrownsLandPortionExceeded {
        player.setDominoes(new ArrayList<>());
        LandPortion landPortionLeft = new LandPortion(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")), LandPortionType.CHAMPS);
        LandPortion landPortionRight = new LandPortion(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")) - 1, LandPortionType.MINE);
        player.addDomino(new Domino(landPortionLeft, landPortionRight, 50));
        assertEquals(1, player.getDominoes().size());
    }

    @Test
    void getKings() {
        player.setKings(new ArrayList<>());
        assertEquals(0, player.getKings().size());
    }

    @Test
    void addKing() {
        player.setKings(new ArrayList<>());
        player.addKing(new King(PlayerColor.BLUE));
        assertEquals(1, player.getKings().size());
    }

    @Test
    void getCastle() {
        Castle castle = new Castle(PlayerColor.BLUE);
        player.setCastle(castle);
        assertSame(castle, player.getCastle());
    }

    @Test
    void getPlayerColor() {
        PlayerColor playerColor = PlayerColor.BLUE;
        player.setPlayerColor(playerColor);
        assertSame(playerColor, player.getPlayerColor());
    }

    @Test
    void getStartTile() throws MaxCrownsLandPortionExceeded {
        StartTile startTile = new StartTile();
        player.setStartTile(startTile);
        assertSame(startTile, player.getStartTile());
    }

}