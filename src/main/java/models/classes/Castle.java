package models.classes;

import models.enums.PlayerColor;

public class Castle {

    private PlayerColor color;

    public Castle(PlayerColor color) {
        this.setColor(color);
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

}
