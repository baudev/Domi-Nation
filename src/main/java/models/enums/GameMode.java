package models.enums;

public enum GameMode {

    CLASSIC("Classic"),
    DYNASTY("Dynasty"),
    MIDDLEKINGDOM("Middle Kingdom"),
    HARMOMY("Harmony"),
    THEGREATDUEL("The Great Duel");

    private final String title;

    GameMode(final String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
