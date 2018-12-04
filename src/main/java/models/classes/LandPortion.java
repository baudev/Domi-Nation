package models.classes;

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
    public LandPortion(int numberCrowns, LandPortionType landPortionType) {
        this.numberCrowns = numberCrowns;
        this.landPortionType = landPortionType;
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public int getNumberCrowns() {
        return numberCrowns;
    }

    public void setNumberCrowns(int numberCrowns) {
        this.numberCrowns = numberCrowns;
    }

    public LandPortionType getLandPortionType() {
        return landPortionType;
    }

    public void setLandPortionType(LandPortionType landPortionType) {
        this.landPortionType = landPortionType;
    }
}
