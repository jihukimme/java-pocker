package CardGame.pocker;

import java.util.ArrayList;

public class GameManager {
    private static final int pockerPrizeMoney = 100;

    private final ArrayList<Player> players;
    private Player winner;
    private final Dealer dealer;


    public GameManager(ArrayList<Player> players) {
        this.players = players;
        this.dealer = new Dealer(players);
    }


    public void startGame(){
        dealer.shuffle();
        dealer.giveCardsToPlayers();

        winner = dealer.getWinner(players);
        winner.addPrizeMoney(pockerPrizeMoney);
        winner.addWinCount();

        for(Player player : players){
            if(player != winner){
                player.addLoseCount();
                player.addPrizeMoney(0);
            }
        }
    }

    public void endGame(){
        // 모든 player의 카드 회수
        for(Player player : players){
            player.removeAllPlayerCards();
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getWinner() {
        return winner;
    }

    public Dealer getDealer() {
        return dealer;
    }

}
