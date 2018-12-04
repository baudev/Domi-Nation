package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.LandPortionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandPortionTest {

    private static LandPortion landPortion;

    @BeforeAll
    public static void setup() {
        try {
            landPortion = new LandPortion(0, LandPortionType.CHAMPS);
        } catch (MaxCrownsLandPortionExceeded maxCrownsLandPortionExceeded) {
            maxCrownsLandPortionExceeded.printStackTrace();
        }
    }

    @Test
    void setNumberCrownsExceededLimitShouldThrowException() {
        assertThrows(MaxCrownsLandPortionExceeded.class,
                ()->{
                    landPortion.setNumberCrowns(Integer.valueOf(Config.getValue("LandPortionMaxCrowns") + 1));
                }, "Should throw MaxCrownsLandPortionExceeded exception when the number of crowns exceed the limit");
    }

    @Test
    void setNumberCrownsEqualLimitShouldReturnLimit(){
        assertAll(
                ()->{
                    landPortion.setNumberCrowns(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")));
                }
        );
    }

    @Test
    void setNumberCrownsInferiorLimitShouldReturnNumber(){
        assertAll(
                ()->{
                    landPortion.setNumberCrowns(Integer.valueOf(Config.getValue("LandPortionMaxCrowns")) - 1);
                }
        );
    }

    @Test
    void getNumberCrowns() throws MaxCrownsLandPortionExceeded {
        int limit = Integer.valueOf(Config.getValue("LandPortionMaxCrowns"));
        landPortion.setNumberCrowns(limit - 1);
        assertEquals(limit - 1, landPortion.getNumberCrowns());
    }

    @Test
    void getLandPortionType() {
        landPortion.setLandPortionType(LandPortionType.MER);
        assertEquals(LandPortionType.MER, landPortion.getLandPortionType());
    }

}