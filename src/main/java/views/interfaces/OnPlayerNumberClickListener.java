package views.interfaces;

import models.enums.PlayerNumber;

/**
 * When a button with the number of {@link models.classes.Player}s for the {@link models.classes.Game} is clicked.
 */
public interface OnPlayerNumberClickListener {

    void onPlayerNumberClickListener(PlayerNumber playerNumber);

}
