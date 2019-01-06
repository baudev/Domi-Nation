package exceptions;

import models.enums.PlayerColor;

/**
 * A {@link models.classes.Player} is already using the {@link models.enums.PlayerColor} desired.
 * @see models.classes.Game#createPlayerWithColor(PlayerColor)
 */
public class PlayerColorAlreadyUsed extends Exception {
}
