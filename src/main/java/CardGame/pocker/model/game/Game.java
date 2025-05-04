package CardGame.pocker.model.game;

import CardGame.pocker.model.dealer.Dealer;
import CardGame.pocker.model.player.Player;

import java.util.ArrayList;

public class Game {
    private static final int pockerWinnerPrizeMoney = 100;
    private static final int pockerLoserPrizeMoney = 0;

    private final ArrayList<Player> players;
    private Player winner;
    private final Dealer dealer;

    public Game() {
        this.players = new ArrayList<>();
        this.dealer = new Dealer(players);
    }

    public void startGame(){
        dealer.shuffle();
        dealer.giveCardsToPlayers();

        winner = dealer.getWinner(players);
        winner.addPrizeMoney(pockerWinnerPrizeMoney);
        winner.addWinCount();

        for(Player player : players){
            if(player != winner){
                player.addLoseCount();
                player.addPrizeMoney(pockerLoserPrizeMoney);
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

    public boolean addPlayer(String nickname) {
        if (isNicknameDuplicated(nickname)) {
            System.out.println("nickname이 중복됩니다. 다시 시도해주세요.");
            return false;
        } else {
            players.add(new Player(nickname));
            return true;
        }
    }

    private boolean isNicknameDuplicated(String nickname) {
        for (Player player : players) {
            if (player.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
