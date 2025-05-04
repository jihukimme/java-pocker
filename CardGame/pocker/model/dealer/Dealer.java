package CardGame.pocker.model.dealer;

import CardGame.pocker.model.card.Card;
import CardGame.pocker.model.card.CardNumber;
import CardGame.pocker.model.card.CardRank;
import CardGame.pocker.model.card.Deck;
import CardGame.pocker.model.player.Player;

import java.util.*;

public class Dealer {
    private final ArrayList<Card> deckCards;
    private final ArrayList<Player> players;
    private final Map<Player, CardRank> playersCardRankMap;
    // private final Map<String, Integer> playerCardNumberCountMap;

    public Dealer(ArrayList<Player> players) {
        // deck은 dealer의 손에 있고, dealer는 deck을 섞고, player에게 카드를 나눠줌
        // dealer는 각 player의 카드를 평가하고 결과를 점수로 반환
        Deck deck = Deck.getInstance();
        this.deckCards = deck.getCards();
        this.players = players;
        this.playersCardRankMap = new HashMap<>();
        // this.playerCardNumberCountMap = new HashMap<>();
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

    public String getCardRankName(Player player) {
        // 로티플일 때(모양)
        // 백스플일 때(모양)
        // 스티플일 때(숫자, 모양)
        // 포카드일 때(숫자, 모양)
        // 풀하우스(숫자, 모양)
        // 플러쉬(모양)
        // 마운틴(모양)
        // 백스트레이트(모양)
        // 스트레이트(숫자, 모양)
        // 트리플(숫자, 모양)
        // 투페어(숫자, 모양)
        // 원페어일 때(숫자, 모양)
        // 하이카드(숫자, 모양)


        CardRank cardRank = playersCardRankMap.get(player);

        return cardRank.getName();
    }


    public Player getWinner(ArrayList<Player> players) {
        evaluatePlayersCard(players);
        List<Map.Entry<Player, CardRank>> playerCardRankList = getPlayerCardRankList();

        // 승자 결정
        Player winner = playerCardRankList.get(0).getKey();  // 가장 높은 족보 점수를 가진 플레이어
        CardRank bestCardRank = playerCardRankList.get(0).getValue();

        // 점수가 동일한 경우 하이카드로 승자 비교
        for (int i = 1; i < playerCardRankList.size(); i++) {
            Player player = playerCardRankList.get(i).getKey();
            CardRank cardRank = playerCardRankList.get(i).getValue();
            if (cardRank.equals(bestCardRank)) {
                // playerCard 간에 숫자와 모양 비교 필요

                winner = compareTwoPlayersHighCard(winner, player, bestCardRank);
            } else {
                break;
            }
        }

        return winner;
    }


    private CardRank getCardRank(ArrayList<Card> cards) {
        // card의 numberScore를 기준으로 오름차순 정렬(2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A)
        cards.sort((c1, c2) -> c2.getNumberScore() - c1.getNumberScore());

        boolean flush = isFlush(cards);
        boolean straight = isStraight(cards);
        boolean backStraight = isBackStraight(cards);
        boolean mountain = isMountain(cards);

        Map<String,Integer> playerCardNumberCountMap = getPlayerCardNumberCountMap(cards);
        Collection<Integer> counts = playerCardNumberCountMap.values();

        // 1. 로얄 플러시(같은 모양, 10 J Q K A) -> 플러시 + 마운틴
        if (flush && mountain) return CardRank.ROYAL_FLUSH;
        // 2. 백 스트레이트 플러쉬(같은 모양, A 2 3 4 5) -> 플러시 + 백스트레이트
        if (flush && backStraight) return CardRank.BACK_STRAIGHT_FLUSH;
        // 3. 스트레이트 플러쉬(같은 모양, 스트레이트) -> 플러시 + 스트레이트
        if (flush && straight) return CardRank.STRAIGHT_FLUSH;
        // 4. 포카드(같은 숫자 4장)
        if (counts.contains(4)) return CardRank.FOUR_OF_A_KIND;
        // 5. 풀하우스(같은 숫자 3장, 또 다른 같은 숫자 2장)
        if (counts.contains(3) && counts.contains(2)) return CardRank.FULL_HOUSE;
        // 6. 플러쉬(같은 모양 5장)
        if (flush) return CardRank.FLUSH;
        // 7. 마운틴(10 J Q K A)
        if (mountain) return CardRank.MOUNTAIN;
        // 8. 백스트레이트(A 2 3 4 5)
        if (backStraight) return CardRank.BACK_STRAIGHT;
        // 9. 스트레이트(연속되는 숫자 5장)
        if (straight) return CardRank.STRAIGHT;
        // 10. 트리플(같은 숫자 3장)
        if (counts.contains(3)) return CardRank.TRIPLE;

        long pairCount = counts.stream().filter(v -> v == 2).count();
        // 11. 투페어(같은 숫자 2장 두쌍)
        if (pairCount == 2) return CardRank.TWO_PAIR;
        // 12. 원페어(같은 숫자 2장 한쌍)
        if (pairCount == 1) return CardRank.ONE_PAIR;

        // 13. 탑(가지고 있는 카드 중 가장 큰 수, 가장 큰 모양)
        return CardRank.HIGH_CARD;
    }

    private void evaluatePlayersCard(ArrayList<Player> players) {
        playersCardRankMap.clear();

        for (Player player : players) {
            ArrayList<Card> playerCards = player.getPlayerCards();
            CardRank cardRank = getCardRank(playerCards);

            playersCardRankMap.put(player, cardRank);
        }

    }

    private Map<String,Integer> getPlayerCardNumberCountMap(ArrayList<Card> cards) {
        Map<String, Integer> playerCardNumberCountMap = new HashMap<>();

        for (Card card : cards) {
            String cardNumberName = card.getNumberName();
            playerCardNumberCountMap.put(cardNumberName, playerCardNumberCountMap.getOrDefault(cardNumberName, 0) + 1);
        }

        return playerCardNumberCountMap;
    }

    private List<Map.Entry<Player, CardRank>> getPlayerCardRankList() {
        List<Map.Entry<Player, CardRank>> playerCardRankList = new ArrayList<>(playersCardRankMap.entrySet());
        playerCardRankList.sort((entry1, entry2) -> entry2.getValue().getScore() - entry1.getValue().getScore());

        return playerCardRankList;
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

        for (int i = 1; i < cards.size(); i++) {
            if (firstCard.getNumberScore() + 1 != cards.get(i).getNumberScore()) {
                return false;
            } else {
                firstCard = cards.get(i);
            }
        }

        return true;
    }

    private boolean isBackStraight(ArrayList<Card> cards) {
        // 백스트레이트(A(14) 2(2) 3(3) 4(4) 5(5))
        Card firstCard = cards.get(0);
        Card lastCard = cards.get(cards.size() - 1);

        if (firstCard.getNumberScore() != 2 || lastCard.getNumberScore() != 14) {
            return false;
        } else {
            for (int i = 1; i < cards.size(); i++) {
                if (firstCard.getNumberScore() + 1 != cards.get(i).getNumberScore()) {
                    return false;
                } else {
                    firstCard = cards.get(i);
                }
            }
        }

        return true;
    }

    private boolean isMountain(ArrayList<Card> cards) {
        // 마운틴(10(10) J(11) Q(12) K(13) A(14))
        Card firstCard = cards.get(0);

        if (firstCard.getNumberScore() != 10) {
            return false;
        } else {
            return isStraight(cards);
        }
    }

    private Player compareTwoPlayersHighCard(Player player1, Player player2, CardRank bestCardRank) {
        ArrayList<Card> cards1 = player1.getPlayerCards();
        ArrayList<Card> cards2 = player2.getPlayerCards();

        // special case : 포카드일 때(포카드 중 높은 숫자), 풀하우스(같은 숫자인 것 중 높은 숫자), 트리플(트리플 중 숫자모양비교), 투페어(원페어 두쌍 중 높은 숫자, 숫자 같으면 모양비교), 원페어일 때(원페어 중 높은 숫자, 숫자 같으면 모양비교)
        // general case : highCard(높은 숫자, 숫자 같으면 모양 비교)
        switch (bestCardRank) {
            case FOUR_OF_A_KIND:
            case FULL_HOUSE:
            case TRIPLE:
            case TWO_PAIR:
            case ONE_PAIR:
                Card highCard1 = getHighCardFromPairCard(cards1);
                Card highCard2 = getHighCardFromPairCard(cards2);
                return getHighCard(highCard1, highCard2).equals(highCard1) ? player1 : player2;
            default:
                return getHighCard(cards1.get(0), cards2.get(0)).equals(cards1.get(0)) ? player1 : player2;
        }
    }

    private Card getHighCardFromPairCard(ArrayList<Card> cards) {
        Map<String, Integer> playerCardNumberCountMap = getPlayerCardNumberCountMap(cards);

        return cards.stream()
                .filter(card -> playerCardNumberCountMap.get(card.getNumberName()) >= 2)
                .max(Comparator
                        .comparingInt(Card::getNumberScore)
                        .thenComparingInt(Card::getShapeScore))
                .orElse(null);
    }

    private Card getHighCard(Card card1, Card card2) {
        // 숫자를 우선으로 비교, 숫자가 같은 경우 모양을 비교
        if (card1.getNumberScore() == card2.getNumberScore()) {
            return (card1.getShapeScore() > card2.getShapeScore()) ? card1 : card2;
        } else {
            if (card1.getNumberScore() > card2.getNumberScore()) {
                return card1;
            } else {
                return card2;
            }
        }
    }
}
