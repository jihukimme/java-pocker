package CardGame.pocker;

import CardGame.pocker.Player;

import java.util.*;

class Dealer {
    private final ArrayList<Card> deckCards;
    private final ArrayList<Player> players;

    Dealer(ArrayList<Player> players) {
        // deck은 dealer의 손에 있고, dealer는 deck을 섞고, player에게 카드를 나눠줌
        // dealer는 각 player의 카드를 평가하고 결과를 점수로 반환
        Deck deck = Deck.getInstance();
        deckCards = deck.getCards();
        this.players = players;
    }

    public void shuffle() {
        Collections.shuffle(deckCards);
    }

    public void giveCardsToPlayers() {
        int cardIndex = 0;
        for (int round = 0; round < 5; round++) { // 카드 5장씩
            for (Player player : players) {
                Card card = deckCards.get(cardIndex++);
                player.addCard(card);
            }
        }
    }


    public boolean isStraight(ArrayList<Card> cards) {
        List<Integer> numbers = new ArrayList<>();
        for (Card card : cards) {
            numbers.add(card.getNumber());
        }
        Collections.sort(numbers);

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) != numbers.get(i - 1) + 1) return false;
        }
        return true;
    }

    public boolean isFlush(ArrayList<Card> cards) {
        char shape = cards.get(0).getShape();
        for (Card card : cards) {
            if (card.getShape() != shape) return false;
        }
        return true;
    }

    public Map<Integer, Integer> getNumberCountMap(ArrayList<Card> cards) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Card card : cards) {
            int number = card.getNumber();
            countMap.put(number, countMap.getOrDefault(number, 0) + 1);
        }
        return countMap;
    }


}
