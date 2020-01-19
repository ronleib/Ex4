package gameClient;

import Server.game_service;
import oop_dataStructure.oop_graph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.List;

/**
 * Thic class Implement Robot interface and it's an inctence of
 * an Object Type That "Catch" Anothe object in a
 *  * a Gamable Inetefece That can be geted from a server
 */
public class robot implements robots {

    int ID;
    Point3D location;
    int srcNode;
    int destNode;
    double value;
    double speed;

    /**
     * The Constractor of the robot
     * @param id The id of the robot
     * @param src The src node of the robot
     * @param dest The dest Node of the robot
     * @param val The Value of the robot
     * @param spe The speed of the robot
     */
    public robot(int id, int src, int dest, double val, double spe) {
        ID = id;
        srcNode = src;
        destNode = dest;
        value = val;
        speed = spe;
    }

    /**
     *
     * @return The id of the robot
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     *
     * @param nextID The next id to be seted
     */
    @Override
    public void setNextNode(int nextID) {
        destNode = nextID;
    }

    /**
     *
     * @return the next node of the robot
     */
    @Override
    public int getNextNode() {
        return destNode;
    }

    /**
     *
     * @return The location of th robot
     */
    @Override
    public Point3D getLocation() {
        return this.location;
    }

    /**
     *
     * @return The src node of the robot
     */
    @Override
    public int getSrcNode() {
        return srcNode;
    }

    /**
     *
     * @return The value of the robot
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     *
     * @return The speed of the robot
     */
    @Override
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @param location Set's The Location Of The Robot
     */
    @Override
    public void setLocation(Point3D location) {
        this.location = location;
    }


}
