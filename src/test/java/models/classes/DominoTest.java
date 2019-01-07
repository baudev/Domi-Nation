package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import mockit.Mock;
import mockit.MockUp;
import models.enums.LandPortionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import views.templates.DominoView;
import views.templates.LandPortionView;

import static org.junit.jupiter.api.Assertions.*;

class DominoTest {

    private static Domino domino;
    private static LandPortion landPortionLeft, landPortionRight;

    @BeforeAll
    private static void beforeAll() throws MaxCrownsLandPortionExceeded {

        // Mock the DominoView class
        new MockUp<DominoView>() {
            @Mock
            public void $init(Domino domino) {
            }
        };
        new MockUp<LandPortionView>() {
            @Mock
            public void $init(LandPortion landPortion) {
            }
        };

        landPortionLeft = new LandPortion(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")), LandPortionType.CHAMPS);
        landPortionRight = new LandPortion(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")) - 1, LandPortionType.MINE);
        domino = new Domino(landPortionLeft, landPortionRight, 50);
    }

    /*
    @Test
    void getLeftPortion() {
        assertSame(landPortionLeft, domino.getLeftPortion());
    }

    @Test
    void getRightPortion() {
        assertSame(landPortionRight, domino.getRightPortion());
    }*/

    @Test
    void getNumber() {
        assertEquals(50, domino.getNumber());
    }

    @Test
    void setDominoViewShouldReturnSame() {
        DominoView dominoView = new DominoView(domino);
        domino.setDominoView(dominoView);
        assertSame(dominoView, domino.getDominoView());
    }

    @Test
    void setNullDominoViewShouldReturnDominoView() {
        domino.setDominoView(null);
        assertTrue(domino.getDominoView() instanceof DominoView);
    }

}