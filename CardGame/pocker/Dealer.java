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

    public Player getWinner(ArrayList<Player> players) {
        Player winner = null;
        int bestScore = -1;

        for (Player player : players) {
            ArrayList<Card> cards = player.getPlayerCards();
            CardRank cardRank = getCardRank(cards);
            int score = cardRank.getScore();

            if (winner == null) {
                winner = player;
                bestScore = score;
                continue;
            }

            if (score > bestScore) {
                winner = player;
                bestScore = score;
            } else if (score == bestScore) {
                // 점수가 같을 경우 하이카드 비교
//                ArrayList<Card> winnerCards = winner.getPlayerCards();
//                int compare = compareCard(cards, winnerCards);
//                if (compare > 0) {
//                    winner = player;
//                }

                if(cardRank.getScore() == getCardRank(winner.getPlayerCards()).getScore()) {}
            }
        }

        return winner;
    }

    private CardRank getCardRank(ArrayList<Card> cards) {
        // 1. 로얄 플러시(같은 모양, 10 J Q K A) -> 플러시 + 마운틴
        // 2. 백 스트레이트 플러쉬(같은 모양, A 2 3 4 5) -> 플러시 + 백스트레이트
        // 3. 스트레이트 플러쉬(같은 모양, 스트레이트) -> 플러시 + 스트레이트
        // 4. 포카드(같은 숫자 4장)
        // 5. 풀하우스(같은 숫자 3장, 또 다른 같은 숫자 2장)
        // 6. 플러쉬(같은 모양 5장)
        // 7. 마운틴(10 J Q K A)
        // 8. 백스트레이트(A 2 3 4 5)
        // 9. 스트레이트(연속되는 숫자 5장)
        // 10. 트리플(같은 숫자 3장)
        // 11. 투페어(같은 숫자 2장 두쌍)
        // 12. 원페어(같은 숫자 2장 한쌍)
        // 13. 탑(가지고 있는 카드 중 가장 큰 수, 가장 큰 모양)

        cards.sort((c1, c2) -> c2.getNumberScore() - c1.getNumberScore());

        boolean flush = isFlush(cards);
        boolean straight = isStraight(cards);
        boolean backStraight = isBackStraight(cards);
        boolean mountain = isMountain(cards);

        Map<String, Integer> numberMap = getNumberCountMap(cards);
        Collection<Integer> counts = numberMap.values();

        if (flush && mountain) return CardRank.ROYAL_FLUSH;
        if (flush && backStraight) return CardRank.BACK_STRAIGHT_FLUSH;
        if (flush && straight) return CardRank.STRAIGHT_FLUSH;
        if (counts.contains(4)) return CardRank.FOUR_OF_A_KIND;
        if (counts.contains(3) && counts.contains(2)) return CardRank.FULL_HOUSE;
        if (flush) return CardRank.FLUSH;
        if (mountain) return CardRank.MOUNTAIN;
        if (backStraight) return CardRank.BACK_STRAIGHT;
        if (straight) return CardRank.STRAIGHT;
        if (counts.contains(3)) return CardRank.TRIPLE;

        long pairCount = counts.stream().filter(v -> v == 2).count();
        if (pairCount == 2) return CardRank.TWO_PAIR;
        if (pairCount == 1) return CardRank.ONE_PAIR;

        return CardRank.HIGH_CARD;
    }

    public String getCardRankName(ArrayList<Card> cards) {
        String cardRankName = "";

        CardRank cardRank = getCardRank(cards);
        cardRankName = cardRank.getName();
//        if (cardRank == CardRank.HIGH_CARD) return "HIGH";
//        else{
//            cardRankName = cardRank.getName();
//        }

        return cardRankName;
    }



    private boolean isFlush(ArrayList<Card> cards) {
        int shapeScore = cards.get(0).getShapeScore();
        for (Card card : cards) {
            if (card.getShapeScore() != shapeScore) return false;
        }
        return true;
    }

    private boolean isStraight(ArrayList<Card> cards) {
        Card firstCard = cards.get(0);

        for(int i=1;i<cards.size();i++){
            if(firstCard.getNumberScore() + 1 != cards.get(i).getNumberScore()){
                return false;
            }
            else {
                firstCard = cards.get(i);
            }
        }

        return true;
    }

    private boolean isBackStraight(ArrayList<Card> cards) {
        // 백스트레이트(A(14) 2(2) 3(3) 4(4) 5(5))
        Card fisrstCard = cards.get(0);
        Card lastCard = cards.get(cards.size()-1);

        if(fisrstCard.getNumberScore() != 2 || lastCard.getNumberScore() != 14){
            return false;
        }
        else {
            for (int i=1; i<cards.size(); i++) {
                if(fisrstCard.getNumberScore() + 1 != cards.get(i).getNumberScore()){
                    return false;
                }
                else {
                    fisrstCard = cards.get(i);
                }
            }
        }

        return true;
    }

    private boolean isMountain(ArrayList<Card> cards) {
        // 마운틴(10(10) J(11) Q(12) K(13) A(14))
        Card firstCard = cards.get(0);

        if(firstCard.getNumberScore() != 10){
            return false;
        }
        else {
            return isStraight(cards);
        }
    }



    private Map<String, Integer> getNumberCountMap(ArrayList<Card> cards) {
        Map<String, Integer> countMap = new HashMap<>();
        for (Card card : cards) {
            String cardNumberName = card.getNumberName();
            countMap.put(cardNumberName, countMap.getOrDefault(cardNumberName, 0) + 1);
        }
        return countMap;
    }

    private int compareCard(Card card1, Card card2) {
        if(compareCardNumber(card1, card2)==0){
            return compareCardShape(card1, card2);
        }
        return compareCardNumber(card1, card2);
    }

    private int compareCardNumber(Card card1, Card card2) {
        if(card1.getNumberScore() == card2.getNumberScore()){
            return 0;
        }
        else {
            if(card1.getNumberScore() > card2.getNumberScore()){
                return 1;
            }
            else {
                return -1;
            }
        }
    }

    private int compareCardShape(Card card1, Card card2) {
        if(card1.getShapeScore() > card2.getShapeScore()){
            return 1;
        }
        else {
            return -1;
        }
    }

}
