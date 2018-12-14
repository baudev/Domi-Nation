package models.enums;

public enum PlayerNumber {

    TWO(2),
    THREE(3),
    FOUR(4);

    private final int numberPlayer;

    PlayerNumber(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public int getValue() {
        return this.numberPlayer;
    }

}
