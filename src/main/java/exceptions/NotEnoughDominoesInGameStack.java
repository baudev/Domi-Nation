package exceptions;

import models.classes.Game;

/**
 * There not enough {@link models.classes.Domino}s in the {@link models.classes.Game} stack.
 * @see Game#pickDominoes()
 */
public class NotEnoughDominoesInGameStack extends Exception {
}
