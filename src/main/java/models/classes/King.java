package models.classes;

import models.enums.PlayerColor;
import views.templates.KingView;

public class King {

    private PlayerColor color;
    private KingView kingView;

    public King(PlayerColor color) {
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

    public KingView getKingView() {
        if(kingView == null) {
            this.setKingView(new KingView(this));
        }
        return kingView;
    }

    public void setKingView(KingView kingView) {
        this.kingView = kingView;
    }
}
