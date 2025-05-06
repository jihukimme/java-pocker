package CardGame.pocker.view;

import CardGame.pocker.model.dealer.Dealer;
import CardGame.pocker.model.player.Player;

import java.util.ArrayList;

public class OutputView {
    public void printGameResult(int round, Player winner, ArrayList<Player> players, Dealer dealer) {
        System.out.println("============================");
        System.out.println(round + "번째 게임");
        System.out.println("Winner: " + winner.getNickname());

        for (Player player : players) {
            System.out.print(player);
            System.out.println(" -> " + player.getNickname() + "의 족보 : " + dealer.getCardRankName(player));
        }
    }

    public void printFinalGameResult(ArrayList<Player> players) {
        players.sort((o1, o2) -> o2.getWinCount() - o1.getWinCount());

        System.out.println("===== 최종 게임 결과 =====");

        int rank = 1;
        for (Player player : players) {
            System.out.println(rank + "위 : " + "Player {" +
                    "nickname='" + player.getNickname() + '\'' +
                    ", gameMoney=" + player.getGameMoney() +
                    ", winCount=" + player.getWinCount() +
                    ", loseCount=" + player.getLoseCount() +
                    '}');

            rank++;
        }
    }
}
