package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.LandPortionType;

public class LandPortion {

    /**
     * Number of crowns on the portion
     */
    private int numberCrowns;

    /**
     * Land type of the portion
     */
    private LandPortionType landPortionType;

    /**
     * Constructor
     * @param numberCrowns
     * @param landPortionType
     */
    public LandPortion(int numberCrowns, LandPortionType landPortionType) throws MaxCrownsLandPortionExceeded {
        this.setNumberCrowns(numberCrowns);
        this.setLandPortionType(landPortionType);
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public int getNumberCrowns() {
        return numberCrowns;
    }

    public void setNumberCrowns(int numberCrowns) throws MaxCrownsLandPortionExceeded {
        // we check if the number of crowns is correct
        if(numberCrowns > Integer.valueOf(Config.getValue("LandPortionMaxCrowns"))){
            throw new MaxCrownsLandPortionExceeded();
        }
        this.numberCrowns = numberCrowns;
    }

    public LandPortionType getLandPortionType() {
        return landPortionType;
    }

    public void setLandPortionType(LandPortionType landPortionType) {
        this.landPortionType = landPortionType;
    }
}
