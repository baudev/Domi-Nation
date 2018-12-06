package models.classes;

import models.enums.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CastleTest {

    private static Castle castle;

    @BeforeAll
    private static void beforeAll() {
        castle = new Castle(PlayerColor.BLUE);
    }

    @Test
    void getColor() {
        assertEquals(PlayerColor.BLUE, castle.getColor());
    }

}