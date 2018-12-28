package models.enums;

public enum Rotation {

    NORMAL(0),
    LEFT(-90),
    RIGHT(+90),
    INVERSE(-180);

    private int degree;

    Rotation(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return this.degree;
    }

}
