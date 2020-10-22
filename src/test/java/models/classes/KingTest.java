package models.classes;

import models.enums.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    private static King king;

    @BeforeAll
    private static void beforeAll() {
        king = new King(PlayerColor.BLUE);
    }

    @Test
    void getColor() {
        assertEquals(PlayerColor.BLUE, king.getColor());
    }

}