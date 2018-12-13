package models.classes;

public class Position {

    private int x, y;

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
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
