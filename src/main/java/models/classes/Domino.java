package models.classes;

import views.templates.DominoView;

public class Domino {

    private LandPortion leftPortion;
    private LandPortion rightPortion;
    private DominoView dominoView;
    private int number;

    /**
     * Constructor
     * @param leftPortion
     * @param rightPortion
     * @param number
     */
    public Domino(LandPortion leftPortion, LandPortion rightPortion, int number) {
        this.setLeftPortion(leftPortion);
        this.setRightPortion(rightPortion);
        this.setNumber(number);
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */
    public LandPortion getLeftPortion() {
        return leftPortion;
    }

    public void setLeftPortion(LandPortion leftPortion) {
        this.leftPortion = leftPortion;
    }

    public LandPortion getRightPortion() {
        return rightPortion;
    }

    public void setRightPortion(LandPortion rightPortion) {
        this.rightPortion = rightPortion;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DominoView getDominoView() {
        if(dominoView == null) {
            this.setDominoView(new DominoView(this));
        }
        return dominoView;
    }

    public void setDominoView(DominoView dominoView) {
        this.dominoView = dominoView;
    }
}
