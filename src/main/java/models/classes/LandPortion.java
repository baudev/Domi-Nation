package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.LandPortionType;
import views.templates.LandPortionView;

/**
 * This represents a {@link LandPortion} in the {@link Game}.
 * It's the half part of a {@link Domino}.
 */
public class LandPortion {

    /*
     * Number of crowns on the portion
     */
    private int numberCrowns;

    /*
     * Land type of the portion
     */
    private LandPortionType landPortionType;

    /*
     * The view corresponding to the entitty
     */
    private LandPortionView landPortionView;

    /*
     * Position of the land portion
     */
    private Position position;

    /**
     * Generates a {@link LandPortion} with the numberCrowns and landPortionType passed as parameter.
     * @param numberCrowns  Number of crowns.
     * @param landPortionType   {@link LandPortionType} of the {@link LandPortion} generated.
     */
    public LandPortion(int numberCrowns, LandPortionType landPortionType) throws MaxCrownsLandPortionExceeded {
        this.setNumberCrowns(numberCrowns);
        this.setLandPortionType(landPortionType);
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the number of crowns of the {@link LandPortion}.
     * @return The number of crowns of the {@link LandPortion}.
     */
    public int getNumberCrowns() {
        return numberCrowns;
    }

    /**
     * Sets the number of crowns of the {@link LandPortion}.
     * @param numberCrowns Number of crowns of the {@link LandPortion} to be set.
     * @throws MaxCrownsLandPortionExceeded If the number of crowns exceed the maximum authorized (value in the configuration file).
     */
    public void setNumberCrowns(int numberCrowns) throws MaxCrownsLandPortionExceeded {
        // we check if the number of crowns is correct
        if(numberCrowns > Integer.valueOf(Config.getValue("LandPortionMaxCrowns"))){
            throw new MaxCrownsLandPortionExceeded();
        }
        this.numberCrowns = numberCrowns;
    }

    /**
     * Returns the {@link LandPortionType} of the {@link LandPortion}.
     * @return The {@link LandPortionType} of the {@link LandPortion}.
     */
    public LandPortionType getLandPortionType() {
        return landPortionType;
    }

    /**
     * Sets the {@link LandPortionType} of the {@link LandPortion}.
     * @param landPortionType   {@link LandPortionType} of the {@link LandPortion} to be set.
     */
    public void setLandPortionType(LandPortionType landPortionType) {
        this.landPortionType = landPortionType;
    }

    /**
     * Returns the {@link Position} of the {@link LandPortion}.
     * @return The {@link Position} of the {@link LandPortion}.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the {@link Position} of the {@link LandPortion}.
     * @param position {@link Position} of the {@link LandPortion} to be set.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the {@link LandPortionView} of the {@link LandPortion}.
     * @return  if the view was not created, a new instance of {@link LandPortionView}.
     *          if the view was already created, the associated instance of {@link LandPortionView}.
     * @see LandPortionView
     */
    public LandPortionView getLandPortionView() {
        if(landPortionView == null) {
            // we generate a new view
            this.setLandPortionView(new LandPortionView(this));
        }
        return landPortionView;
    }

    /**
     * Associates a {@link LandPortionView} to the {@link LandPortion}.
     * @param landPortionView {@link LandPortionView} to be associated to the {@link LandPortionView}.
     */
    public void setLandPortionView(LandPortionView landPortionView) {
        this.landPortionView = landPortionView;
    }
}
