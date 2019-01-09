package models.enums;

/**
 * Different possibles response from the {@link models.classes.Game} class.
 */
public enum Response {

    /**
     * It's a new turn, new {@link models.classes.Domino}s must be picked.
     */
    PICKDOMINOES,
    /**
     * It's the turn of the next {@link models.classes.Player}.
     */
    NEXTTURNPLAYER,
    /**
     * The {@link models.classes.Board} of the {@link models.classes.Player} need to show all possible possibilities of {@link models.classes.Position} for the {@link models.classes.Domino} to be placed.
     */
    SHOWPLACEPOSSIBILITIES,
    /**
     * The {@link models.classes.Game} is over.
     */
    GAMEOVER,
    /**
     * Weird case.
     */
    NULL;

}
