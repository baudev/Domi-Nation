package models.classes;

import models.enums.PlayerColor;
import views.templates.KingView;

/**
 * This represents a {@link King} in the {@link Game}.
 */
public class King {

    private PlayerColor color;
    private boolean isPlaced;
    private KingView kingView;

    /**
     * Creates a {@link King} with the specified {@link PlayerColor}.
     * @param color {@link PlayerColor} of the {@link King}.
     */
    public King(PlayerColor color) {
        this.setColor(color);
        this.setPlaced(false);
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the {@link PlayerColor} of the {@link King}.
     * @return  {@link PlayerColor} of the {@link King}.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Sets the {@link PlayerColor} of the {@link King}.
     * @param color  {@link PlayerColor} of the {@link King} to be set.
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * Get the {@link KingView} of the {@link King}.
     * @return  if the view was not created, a new instance of {@link KingView}.
     *          if the view was already created, the associated instance of {@link KingView}.
     * @see KingView
     */
    public KingView getKingView() {
        if(kingView == null) {
            this.setKingView(new KingView(this));
        }
        return kingView;
    }

    /**
     * Associates a {@link KingView} to the {@link King}.
     * @param kingView {@link KingView} to be associated to the {@link King}.
     */
    public void setKingView(KingView kingView) {
        this.kingView = kingView;
    }

    /**
     * Returns if the {@link King} is placed on a {@link Domino}.
     * @return  true if the {@link King} is placed on a {@link Domino}.
     *          false otherwise.
     */
    public boolean isPlaced() {
        return isPlaced;
    }

    /**
     * Sets if the {@link King} is placed on a {@link Domino}.
     * @param placed Boolean defining if the {@link King} is placed on a {@link Domino}.
     */
    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }
}
