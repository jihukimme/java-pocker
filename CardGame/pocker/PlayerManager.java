package CardGame.pocker;

import java.util.ArrayList;

public class PlayerManager {
    private final ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        return players;
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
