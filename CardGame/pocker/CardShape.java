package CardGame.pocker;

public enum CardShape {
    SPADE("♠", 4),
    DIAMOND("♦", 3),
    HEART("♥", 2),
    CLUB("♣", 1);

    private final String name;
    private final int score;

    CardShape(String name,  int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScroe() {
        return score;
    }
}