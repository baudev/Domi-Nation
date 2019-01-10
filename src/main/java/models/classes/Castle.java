package models.classes;

import models.enums.PlayerColor;
import views.templates.CastleView;

/**
 * A player's {@link Castle}.
 * It is the central piece of the {@link Player}'s {@link Board}. Each {@link Player} has only one {@link Castle}.
 * Each one has an unique {@link Position} and {@link PlayerColor}.
 */
public class Castle {

    private PlayerColor color;
    private Position position;
    private CastleView castleView;

    /**
     * Creates a {@link Castle} with the specified color and position.
     * @param color {@link PlayerColor} of it.
     * @param position {@link Position} of it.
     */
    public Castle(PlayerColor color, Position position) {
        this.setColor(color);
        this.setPosition(position);
    }

    /*
     *
     * GETTERS AND SETTERS
     *
     */

    /**
     * Returns the {@link PlayerColor} of the {@link Castle}.
     * @return  {@link PlayerColor} of the {@link Castle}.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Sets a {@link PlayerColor} to the {@link Castle}.
     * @param color {@link PlayerColor} to be set.
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * Returns the {@link Position} of the {@link Castle}.
     * @return  {@link Position} of the {@link Castle}.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets a {@link Position} to the {@link Castle}.
     * @param position {@link Position} to be set.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the {@link CastleView} of the {@link Castle}.
     * @return  if the view was not created, a new instance of {@link CastleView}.
     *          if the view was already created, the associated instance of {@link CastleView}.
     * @see CastleView
     */
    public CastleView getCastleView() {
        if(castleView == null) {
            this.setCastleView(new CastleView(this));
        }
        return castleView;
    }

    /**
     * Associates a {@link CastleView} to the {@link Castle}.
     * @param castleView {@link CastleView} to be associated to the {@link Castle}.
     */
    public void setCastleView(CastleView castleView) {
        this.castleView = castleView;
    }
}
