package pocker;

import java.util.ArrayList;

class Player {
    private static final int startGameMoney = 10000;

    private final String nickname;
    private int gameMoney;
    private int winCount;
    private int loseCount;
    private final ArrayList<Card> playerCards;

    Player(String nickname) {
        if (nickname == null || nickname.length() > 20) {
            throw new IllegalArgumentException("nickname은 null이거나, 20자를 넘길 수 없습니다.");
        }
        this.nickname = nickname;
        this.gameMoney = startGameMoney; // player는 처음에 10000원의 gameMoney를 가짐
        this.winCount = 0;
        this.loseCount = 0;
        this.playerCards = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public int getGameMoney() {
        return gameMoney;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public void addPrizeMoney(int prizeMoney){
        this.gameMoney += prizeMoney;
    }

    public void addWinCount(){
        this.winCount++;
    }

    public void addLoseCount(){
        this.loseCount++;
    }

    public void addCard(Card card){
        playerCards.add(card);
    }

    public void removeAllPlayerCards(){
        playerCards.clear();
    }


    @Override
    public String toString() {
        return "Player {" +
                "nickname='" + nickname + '\'' +
                ", gameMoney=" + gameMoney +
                ", winCount=" + winCount +
                ", loseCount=" + loseCount +
                ", playerCards=" + playerCards +
                '}';
    }
}
