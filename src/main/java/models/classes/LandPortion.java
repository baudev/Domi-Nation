package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.LandPortionType;
import views.templates.LandPortionView;

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
     * The view corresponding to the entitty
     */
    private LandPortionView landPortionView;

    /**
     * Position of the land portion
     */
    private Position position;

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LandPortionView getLandPortionView() {
        if(landPortionView == null) {
            // we generate a new view
            this.setLandPortionView(new LandPortionView(this));
        }
        return landPortionView;
    }

    public void setLandPortionView(LandPortionView landPortionView) {
        this.landPortionView = landPortionView;
    }
}
