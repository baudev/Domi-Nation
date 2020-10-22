package views.interfaces;

import models.enums.PlayerColor;

/**
 * When a {@link models.classes.Player} chose his {@link PlayerColor}.
 */
public interface OnPlayerColorClickListener {

    void onPlayerColorClickListener(PlayerColor playerColor);

}
