package CardGame.pocker;

import CardGame.pocker.Player;

import java.util.ArrayList;

public class GameManager {
    private static final int pockerPrizeMoney = 100;

    private final ArrayList<Player> players;

    public GameManager(ArrayList<Player> players) {
        this.players = players;
    }


    public void startGame(){
        // dealer 생성
        Dealer dealer = new Dealer(players);

        // dealer가 deck을 갖고 있음 -> dealer가 deck을 suffle
        dealer.shuffle();

        // dealer가 각 player에게 카드를 5장씩 나눠줌
        dealer.giveCardsToPlayers();

        // dealer가 각 player의 족보 순위를 매김


        // winner에게 winCount 1증가, gameMoney prizeMoney만큼 증가


        // loser에게 loseCount 1증가, gameMoney 0증가



        // 모든 player의 카드 회수
        for(Player player : players){
            player.removeAllPlayerCards();
        }
    }


    public void printGameResult() {
        // 승리 수가 많은 순으로 정렬해 출력
        players.sort((o1, o2) -> o2.getWinCount() - o1.getWinCount());

        int rank = 1;
        for(Player player : players){
            System.out.print(rank++);
            player.toString();
        }
    }

}
