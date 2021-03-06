package models.classes;

import models.enums.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CastleTest {

    private static Castle castle;

    @BeforeAll
    private static void beforeAll() {
        castle = new Castle(PlayerColor.BLUE, new Position(0, 0));
    }

    @Test
    void getColorShouldReturnBlue() {
        assertEquals(PlayerColor.BLUE, castle.getColor());
    }

    @Test
    void getPositionShouldReturnPosition00() {
        assertEquals(new Position(0, 0), castle.getPosition());
    }

}