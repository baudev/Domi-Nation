package models.enums;

public enum Rotation {

    NORMAL(0),
    LEFT(270),
    RIGHT(90),
    INVERSE(180);

    private int degree;

    Rotation(int degree) {
        this.degree = degree;
    }

    public static Rotation getCorrespondingRotation(int degree) {
        if(degree % 360 == 0) {
            return Rotation.NORMAL;
        } else if(degree % 270 == 0) {
            return Rotation.LEFT;
        } else if(degree % 180 == 0) {
            return Rotation.INVERSE;
        } else if(degree % 90 == 0) {
            return Rotation.RIGHT;
        }
        return null; // TODO throw exception
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public int getDegree() {
        return this.degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

}
