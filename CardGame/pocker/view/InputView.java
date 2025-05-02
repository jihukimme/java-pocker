package CardGame.pocker.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {
    private final BufferedReader br;

    public InputView(){
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public int readPlayersNumber() throws IOException {
        int playersNumber = 0;
        System.out.print("playerNumber를 입력해주세요 : ");

        try {
            playersNumber = Integer.parseInt(br.readLine());

            if(playersNumber >= 1 && playersNumber <= 4){
                return playersNumber;
            }
            else {
                System.out.println("playersNumber는 최소 1, 최대 4의 값을 갖을 수 있습니다. 다시 입력해주세요.");
                return readPlayersNumber();
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
        }

        return playersNumber;
    }


    public String readPlayerNickname() throws IOException {
        System.out.print("playerNickname : ");
        return br.readLine();
    }

}
