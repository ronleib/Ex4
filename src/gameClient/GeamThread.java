package gameClient;

import java.util.List;

public class GeamThread implements Runnable {
    killTheTerrorists game;

    public GeamThread(killTheTerrorists game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.getServer().startGame();
        while (game.getServer().isRunning()) {
            game.moveRobots(game);
        }
    }
}

