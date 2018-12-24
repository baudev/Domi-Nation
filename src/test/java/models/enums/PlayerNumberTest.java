package models.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerNumberTest {

    @Test
    void getValue() {
        PlayerNumber playerNumber = PlayerNumber.FOUR;
        assertEquals(4, playerNumber.getValue());
    }

}