package models.classes;

import java.util.Objects;

public class Position {

    private int x, y;

    public Position() {

    }

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(),this.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position connectionObj = (Position) obj;
            return (this.getX() == connectionObj.getX() && this.getY() == connectionObj.getY());
        }
        return false;
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
