package CardGame.pocker;

import CardGame.pocker.controller.GameController;
import CardGame.pocker.model.game.Game;
import CardGame.pocker.view.InputView;
import CardGame.pocker.view.IntroView;
import CardGame.pocker.view.OutputView;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        IntroView introView = new IntroView();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        GameController gameController = new GameController(game, introView, inputView, outputView);
        gameController.run();
    }
}
