package models.enums;

import javafx.scene.paint.Color;

/**
 * Different possible colors for a {@link models.classes.Player}.
 */
public enum PlayerColor {

    PINK(Color.PINK),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE);

    private Color color;

    PlayerColor(Color color) {
        this.color = color;
    }

    public Color getValue() {
        return this.color;
    }

}
