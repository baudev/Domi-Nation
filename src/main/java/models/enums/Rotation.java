package models.enums;

/**
 * This handles the different possible rotation of a {@link models.classes.Domino} and their associated angles..
 */
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

    public Rotation getNextRotation() {
        switch (this) {
            case NORMAL:
                return RIGHT;
            case RIGHT:
                return INVERSE;
            case INVERSE:
                return LEFT;
            case LEFT:
                return NORMAL;
        }
        return null;
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
