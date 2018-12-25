package models.enums;

import javafx.scene.paint.Color;

public enum LandPortionType {

    CHAMPS(Color.YELLOW),
    FORET(Color.GREEN),
    MER(Color.BLUE),
    PRAIRIE(Color.RED),
    MINE(Color.GREY),
    MONTAGNE(Color.WHITESMOKE),
    TOUS(Color.PINK);

    private Color color;

    LandPortionType(Color color) {
        this.color = color;
    }

    public Color getValue() {
        return this.color;
    }


}
