package models.classes;

import exceptions.MaxCrownsLandPortionExceeded;
import helpers.Config;
import models.enums.Rotation;
import views.templates.DominoView;

/**
 * It is composed of two sides. The first has a number and the other has two {@link LandPortion}.
 * A {@link Player} can appropriate a {@link Domino} by placing one of his {@link King}s on it.
 * The {@link Domino} can be turned in different directions ({@link Rotation}).
 */
public class Domino {

    private LandPortion leftPortion;
    private LandPortion rightPortion;
    private DominoView dominoView;
    private King king;
    private int number;
    private Rotation rotation;

    /**
     * Generates a {@link Domino} from two {@link LandPortion} (leftPortion and rightPortion) and a number.
     * @param leftPortion   Left {@link LandPortion} of the {@link Domino}.
     * @param rightPortion  Right {@link LandPortion} of the {@link Domino}.
     * @param number        Number of it.
     */
    public Domino(LandPortion leftPortion, LandPortion rightPortion, int number) {
        this.setLeftPortion(leftPortion);
        this.setRightPortion(rightPortion);
        this.setNumber(number);
        this.setRotation(Rotation.NORMAL);
    }

    /**
     * Return if the {@link Domino} is horizontal or not.
     * @return  true if the {@link Domino} is horizontal.
     *          false if the {@link Domino} is not horizontal (vertical then).
     * @see Rotation
     */
    public boolean isHorizontal() {
        switch (this.getRotation()) {
            case NORMAL:
            case INVERSE:
                return true;
        }
        return false;
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Get the left {@link LandPortion} of the {@link Domino}.
     * @return  Left {@link LandPortion}.
     */
    public LandPortion getLeftPortion() {
        return leftPortion;
    }

    /**
     * Set the left {@link LandPortion} of the {@link Domino}.
     * @param leftPortion   Left {@link LandPortion} to be set.
     */
    public void setLeftPortion(LandPortion leftPortion) {
        this.leftPortion = leftPortion;
    }

    /**
     * Get the right {@link LandPortion} of the {@link Domino}.
     * @return  Right {@link LandPortion}.
     */
    public LandPortion getRightPortion() {
        return rightPortion;
    }

    /**
     * Set the right {@link LandPortion} of the {@link Domino}.
     * @param rightPortion   Right {@link LandPortion} to be set.
     */
    public void setRightPortion(LandPortion rightPortion) {
        this.rightPortion = rightPortion;
    }

    /**
     * Get the number of the {@link Domino}.
     * @return The number of the {@link Domino}.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the number of the {@link Domino}.
     * @param number    Number to be set.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the {@link DominoView} of the {@link Domino}.
     * @return  if the view was not created, a new instance of {@link DominoView}.
     *          if the view was already created, the associated instance of {@link DominoView}.
     * @see DominoView
     */
    public DominoView getDominoView() {
        if(Boolean.valueOf(Config.getValue("CLImode"))){
            return null;
        }
        if(dominoView == null) {
            this.setDominoView(new DominoView(this));
        }
        return dominoView;
    }

    /**
     * Associates a {@link DominoView} to the {@link Domino}.
     * @param dominoView {@link DominoView} to be associated to the {@link Domino}.
     */
    public void setDominoView(DominoView dominoView) {
        this.dominoView = dominoView;
    }

    /**
     * Get the current {@link King} placed on the {@link Domino}.
     * @return  {@link King} placed on the {@link Domino}. Can be null.
     */
    public King getKing() {
        return king;
    }

    /**
     * Set the current {@link King} placed on the {@link Domino}.
     * If the king parameter is not null, then the view of the {@link Domino} is impacted.
     * @param king  King to be placed on the {@link Domino}.
     */
    public void setKing(King king) {
        this.king = king;
        if(king != null) {
            king.setPlaced(true); // the domino is now placed
            if(!Boolean.valueOf(Config.getValue("CLImode"))){
                this.getDominoView().addKing(); // add the king to the view
            }
        }
    }

    /**
     * Get the current {@link Rotation} of the {@link Domino}.
     * @return  Current {@link Rotation} of the {@link Domino}.
     */
    public Rotation getRotation() {
        return rotation;
    }

    /**
     * Set the current {@link Rotation} of the {@link Domino}.
     * If the {@link Rotation} is <code>INVERSE</code> or <code>NORMAL</code>, then the <code>leftLandPortion</code> and the <code>rightLandPortion</code> are inverted.
     * @param rotation  The new current {@link Rotation} of the {@link Domino}.
     * @see Rotation
     */
    public void setRotation(Rotation rotation) {
        switch (rotation) {
            case INVERSE:
            case NORMAL:
                LandPortion landPortionTemp = null;
                try {
                    landPortionTemp = new LandPortion(this.getLeftPortion().getNumberCrowns(), this.getLeftPortion().getLandPortionType());
                } catch (MaxCrownsLandPortionExceeded maxCrownsLandPortionExceeded) {
                    maxCrownsLandPortionExceeded.printStackTrace();
                }
                landPortionTemp.setPosition(this.getLeftPortion().getPosition());
                landPortionTemp.setLandPortionView(this.getLeftPortion().getLandPortionView());
                leftPortion = rightPortion;
                rightPortion = landPortionTemp;
                break;
        }
        this.rotation = rotation;
        // we make rotate the view
        if(!Boolean.valueOf(Config.getValue("CLImode"))){
            this.getDominoView().setRotate(rotation.getDegree());
        }
    }
}
