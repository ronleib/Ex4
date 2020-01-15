package gameClient;

import Server.game_service;
import Server.robot;

/**
 * This interface represents a directional weighted MyGameGUI.
 * The interface has a road-system or communication network in mind - and should support a large number of nodes (over 100,000).
 * The implementation should be based on an efficient compact representation (should NOT be based on a n*n matrix).
 *
 */
public interface Gamable {

    /**
     * Builds a scenario get Server
     * @param Server
     */
    public void builderScenario(game_service [] Server);

    /**
     * get is number Server
     * Game Builder: (Map, Points, Ribs)
     * @param g
     */
    public void builderGame(int ran);

    /**
     *  get oll tey Figures end in Game
     * @param key - Fruits
     */
    public void getFruits(int ran);



    public robot[] getRobots(int ran);




    /**
     *  Accepts amount of users
     * @param key - Robots
     */
    public void getPlaber(int ran);
    /**
     *  Automatic play: Move the players to win best
     * @param key - Robots
     */
    public void Automaticplay();
    /**
     *Manualgame
     */
    public void Manualgame();


}

