package models.classes;

import java.util.Objects;

/**
 * This represents a 2D dimension (X, Y).
 */
public class Position {

    private int x, y;

    /**
     * Generates a {@link Position} without coordinates.
     */
    public Position() {

    }

    /**
     * Generates a {@link Position} with x and y coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getX(),this.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position connectionObj = (Position) obj;
            return (this.getX() == connectionObj.getX() && this.getY() == connectionObj.getY());
        }
        return false;
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Gets the X coordinate of the {@link Position}.
     * @return The X coordinate of the {@link Position}.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate of the {@link Position}.
     * @param x The X coordinate of the {@link Position} to be set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y coordinate of the {@link Position}.
     * @return The Y coordinate of the {@link Position}.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate of the {@link Position}.
     * @param y The Y coordinate of the {@link Position} to be set.
     */
    public void setY(int y) {
        this.y = y;
    }
}
