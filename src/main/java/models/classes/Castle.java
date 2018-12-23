package models.classes;

import models.enums.PlayerColor;

public class Castle {

    private PlayerColor color;
    private Position position;

    public Castle(PlayerColor color, Position position) {
        this.setColor(color);
        this.setPosition(position);
    }

    /**
     *
     * GETTERS AND SETTERS
     *
     */
    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
