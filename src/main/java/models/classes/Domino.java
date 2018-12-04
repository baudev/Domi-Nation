package models.classes;

public class Domino {

    private LandPortion leftPortion;
    private LandPortion rightPortion;

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
}
