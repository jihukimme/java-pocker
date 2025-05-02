package CardGame.pocker.card.cardEnum;

public enum CardRank {
    ROYAL_FLUSH(13, "로얄 플러시"),
    BACK_STRAIGHT_FLUSH(12, "백스트레이트 플러시"),
    STRAIGHT_FLUSH(11, "스트레이트 플러시"),
    FOUR_OF_A_KIND(10, "포카드"),
    FULL_HOUSE(9, "풀하우스"),
    FLUSH(8, "플러시"),
    MOUNTAIN(7, "마운틴"),
    BACK_STRAIGHT(6, "백스트레이트"),
    STRAIGHT(5, "스트레이트"),
    TRIPLE(4, "트리플"),
    TWO_PAIR(3, "투페어"),
    ONE_PAIR(2, "원페어"),
    HIGH_CARD(1, "탑");

    private final int score;
    private final String name;

    CardRank(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
