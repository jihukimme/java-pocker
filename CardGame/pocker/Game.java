package CardGame.pocker;

import CardGame.pocker.view.InputView;
import CardGame.pocker.view.IntroView;
import CardGame.pocker.view.OutputView;

import java.io.IOException;

class Game {
    public static void main(String[] args) throws IOException {
        final IntroView introView = new IntroView();
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        final PlayerManager playerManager = new PlayerManager();
        final GameManager gameManager = new GameManager(playerManager.getPlayers());

        introView.printGameIntro();

        int playersNumber = inputView.readPlayersNumber();

        // playerNumbers 만큼 nickname 입력받아 player 추가
        for(int i = 1; i <= playersNumber; i++){
            String nickname = inputView.readPlayerNickname();
            boolean success = playerManager.addPlayer(nickname);
            if(!success){
                i--;
            }
        }

        // 100회의 게임 반복
        for(int round=1; round<=100; round++){
            gameManager.startGame();

            outputView.printGameResult(round, gameManager.getWinner(), gameManager.getPlayers(), gameManager.getDealer());

            gameManager.endGame();
        }

        // 총 게임결과 출력
         outputView.printFinalGameResult(gameManager.getPlayers());
    }
}
