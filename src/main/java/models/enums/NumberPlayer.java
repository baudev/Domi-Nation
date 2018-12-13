package models.enums;

public enum NumberPlayer {

    TWO(2),
    THREE(3),
    FOUR(4);

    private final int numberPlayer;

    NumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public int getValue() {
        return this.numberPlayer;
    }

}
