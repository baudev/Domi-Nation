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
    void getPlayerColor() {
        PlayerColor playerColor = PlayerColor.BLUE;
        player.setPlayerColor(playerColor);
        assertSame(playerColor, player.getPlayerColor());
    }

}