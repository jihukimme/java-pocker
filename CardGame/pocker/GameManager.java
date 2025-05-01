package CardGame.pocker;

import CardGame.pocker.Player;

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

        // dealer가 deck을 갖고 있음 -> dealer가 deck을 suffle
        dealer.shuffle();

        // dealer가 각 player에게 카드를 5장씩 나눠줌
        dealer.giveCardsToPlayers();

        // winner에게 winCount 1증가, gameMoney prizeMoney만큼 증가
        winner = dealer.getWinner(players);
        winner.addPrizeMoney(pockerPrizeMoney);
        winner.addWinCount();

        // loser에게 loseCount 1증가, gameMoney 0증가
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

    public void printGameResult() {
        System.out.println("Winner: " + winner);
        for(Player player : players){
            System.out.print(player + " ");
            System.out.println("족보 : " + dealer.getCardRankName(player.getPlayerCards()));
        }
    }


    public void printTotalGameResult() {
        // 승리 수가 많은 순으로 정렬해 출력
        players.sort((o1, o2) -> o2.getWinCount() - o1.getWinCount());

        int rank = 1;
        for(Player player : players){
            System.out.println(rank + "위 : " + player);
            rank++;
        }
    }

}
