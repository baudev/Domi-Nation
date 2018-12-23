package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import models.enums.LandPortionType;

public class StartTile extends LandPortion {

    /**
     * Constructor
     */
    public StartTile(Position position) throws MaxCrownsLandPortionExceeded {
        super(0, LandPortionType.TOUS);
        this.setPosition(position);
    }

}
