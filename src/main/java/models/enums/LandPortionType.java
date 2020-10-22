package models.enums;

import javafx.scene.paint.Color;

/**
 * This is the different {@link models.classes.LandPortion} types and their associated {@link Color}.
 */
public enum LandPortionType {

    CHAMPS(Color.YELLOW),
    FORET(Color.GREEN),
    MER(Color.BLUE),
    PRAIRIE(Color.BLACK),
    MINE(Color.GREY),
    MONTAGNE(Color.CYAN),
    TOUS(Color.PINK);

    private Color color;

    LandPortionType(Color color) {
        this.color = color;
    }

    public Color getValue() {
        return this.color;
    }


}
