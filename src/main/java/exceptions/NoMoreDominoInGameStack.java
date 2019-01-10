package exceptions;

import models.classes.Game;

/**
 * There is no more {@link models.classes.Domino} in the {@link models.classes.Game} stack.
 * @see Game#pickDominoes()
 */
public class NoMoreDominoInGameStack extends Exception {
}
