package CardGame.pocker;

import java.util.ArrayList;

class Deck {
    private static final char[] shapes = {'S', 'D', 'H', 'C'};
    private static final int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    private final ArrayList<Card> cards;

    // 싱글톤 인스턴스
    private static final Deck instance = new Deck();

    // private 생성자로 외부에서 생성자 호출을 막음
    private Deck() {
        cards = new ArrayList<>();

        for (char shape : shapes) {
            for (int number : numbers) {
                Card card = new Card(number, shape);
                cards.add(card);
            }
        }
    }

    // 인스턴스 반환 메소드
    public static Deck getInstance() {
        return instance;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
