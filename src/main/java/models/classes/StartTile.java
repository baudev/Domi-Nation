package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.LandPortionType;

/**
 * This is a {@link LandPortion} which is connectible to all types of {@link LandPortionType}.
 */
public class StartTile extends LandPortion {

    /**
     * Generates a {@link StartTile}.
     */
    public StartTile() throws MaxCrownsLandPortionExceeded {
        super(0, LandPortionType.TOUS);
    }

}
