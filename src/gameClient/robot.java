package gameClient;

import Server.game_service;
import oop_dataStructure.oop_graph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.List;

public class robot implements robots {

    int ID;
    Point3D location;
    int srcNode;
    int destNode;
    double value;
    double speed;

    public robot(int id, int src, int dest, double val, double spe) {
        ID = id;
        srcNode = src;
        destNode = dest;
        value = val;
        speed = spe;
    }


    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setNextNode(int nextID) {
        destNode = nextID;
    }

    @Override
    public int getNextNode() {
        return destNode;
    }

    @Override
    public Point3D getLocation() {
        return this.location;
    }

    @Override
    public int getSrcNode() {
        return srcNode;
    }


    @Override
    public double getValue() {
        return value;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setLocation(Point3D location) {
        this.location = location;
    }


}
