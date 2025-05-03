package CardGame.pocker.model.card;

public enum CardNumber {
    ONE(14, "Ace"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "Jack"),
    QUEEN(12, "Queen"),
    KING(13, "King");

    private final int score;
    private final String name;

    CardNumber(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public static int getScoreByName(String name) {
        for (CardNumber number : values()) {
            if (number.getName().equals(name)) {
                return number.getScore();
            }
        }
        throw new IllegalArgumentException("Unknown card number: " + name);
    }
}
