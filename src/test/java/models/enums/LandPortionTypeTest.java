package models.enums;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandPortionTypeTest {

    @Test
    void getValue() {
        LandPortionType landPortionType = LandPortionType.CHAMPS;
        assertTrue(landPortionType.getValue() instanceof Color); // we do not check precisely the color which could often change
    }
}