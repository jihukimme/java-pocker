package pocker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Game {
    public static void main(String[] args) throws IOException {
        int playerNumber;


        System.out.println("Card Game start");

        // 게임 종목 설정 (pocker로 고정)
        // System.out.print("gameName : ");
        // gameName = br.readLine();



        // playerNumber 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.print("playerNumber : ");
            try {
                playerNumber = Integer.parseInt(br.readLine());

                if(playerNumber >= 1 && playerNumber <= 4){
                    break;
                }
                else {
                    System.out.println("playerNumber는 최소 1, 최대 4의 값을 갖을 수 있습니다. 다시 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
            }
        }



        // playerManager 생성
        PlayerManager playerManager = new PlayerManager();

        // player 추가
        int added = 0;
        while (added < playerNumber) {
            System.out.print("playerNickname : ");
            String nickname = br.readLine();
            boolean success = playerManager.addPlayer(nickname);
            if (success) {
                added++;
            }
        }

        // gameManager 생성, 게임 시작
        ArrayList<Player> players = playerManager.getPlayers();
        GameManager gameManager = new GameManager(players);


        // 100번의 게임 반복
        for(int i=0; i<100; i++){
            gameManager.startGame();
        }


        // 게임결과 출력
        gameManager.printGameResult();


        // 게임 종료
        br.close();
    }
}
