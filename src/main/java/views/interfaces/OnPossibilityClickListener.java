package views.interfaces;

import models.classes.Position;

/**
 * When a {@link models.classes.Player} click on a {@link Position}s possibility for his {@link models.classes.Domino} to be placed.
 */
public interface OnPossibilityClickListener {

    void onPossibilityClickListener(Position position1, Position position2);

}
