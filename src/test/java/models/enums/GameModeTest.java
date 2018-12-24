package models.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModeTest {

    @Test
    void toStringTest() {
        GameMode gameMode = GameMode.THEGREATDUEL;
        assertEquals("The Great Duel", gameMode.toString());
    }

}