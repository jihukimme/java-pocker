package CardGame.pocker.card;

import CardGame.pocker.card.cardEnum.CardNumber;
import CardGame.pocker.card.cardEnum.CardShape;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> cards;

    // 싱글톤 인스턴스
    private static final Deck instance = new Deck();

    // private 생성자로 외부에서 생성자 호출을 막음
    private Deck() {
        cards = new ArrayList<>();

        for(CardShape cardShape : CardShape.values()){
            for(CardNumber cardNumber : CardNumber.values()){
                cards.add(new Card(cardNumber, cardShape));
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
