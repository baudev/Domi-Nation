package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.LandPortionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartTileTest {

    private static StartTile startTile;

    @BeforeAll
    private static void beforeAll() throws MaxCrownsLandPortionExceeded {
        startTile = new StartTile();
    }

    @Test
    void getNumberCrowns() throws MaxCrownsLandPortionExceeded {
        assertEquals(0, startTile.getNumberCrowns());
    }

    @Test
    void getLandPortionType() {
        assertEquals(LandPortionType.TOUS, startTile.getLandPortionType());
    }

}