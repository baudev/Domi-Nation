package models.classes;

public class Position {

    private int x, y;

    public Position() {

    }

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        // TODO check value according to the applied rules
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        // TODO check value according to the applied rules
        this.y = y;
    }
}
