package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.LandPortionType;

public class StartTile extends LandPortion {

    /**
     * Constructor
     */
    public StartTile() throws MaxCrownsLandPortionExceeded {
        super(0, LandPortionType.TOUS);
    }

}
