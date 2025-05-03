package CardGame.pocker.controller;

import CardGame.pocker.model.dealer.Dealer;
import CardGame.pocker.model.game.Game;
import CardGame.pocker.model.player.Player;
import CardGame.pocker.view.InputView;
import CardGame.pocker.view.IntroView;
import CardGame.pocker.view.OutputView;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    private final Game game;
    private final IntroView introView;
    private final InputView inputView;
    private final OutputView outputView;

    public GameController(Game game, IntroView introView, InputView inputView, OutputView outputView) {
        this.game = game;
        this.introView = introView;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        introView.printGameIntro();

        int playersNumber = inputView.readPlayersNumber();

        for (int i = 1; i <= playersNumber; i++) {
            String nickname = inputView.readPlayerNickname();
            boolean success = game.addPlayer(nickname);
            if (!success) i--;
        }

        for (int round = 1; round <= 100; round++) {
            game.startGame();
            outputView.printGameResult(round, game.getWinner(), game.getPlayers(), game.getDealer());
            game.endGame();
        }

        outputView.printFinalGameResult(game.getPlayers());
    }

}
