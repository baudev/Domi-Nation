package models.classes;

import models.enums.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void shouldReturnXandYPosition() {
        Position position = new Position();
        position.setX(1);
        position.setY(2);
        assertEquals(1, position.getX());
        assertEquals(2, position.getY());
    }

    @Test
    void shouldBeEquals() {
        Position position = new Position(1, 1);
        Position position2 = new Position(1, 1);
        assertEquals(position, position2);
    }

    @Test
    void shouldNotBeEquals() {
        Position position = new Position(1, 2);
        assertNotEquals(position, new King(PlayerColor.BLUE));
    }

}