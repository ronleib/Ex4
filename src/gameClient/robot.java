package gameClient;

import utils.Point3D;

public class robot implements robots {

    int ID;
    Point3D location;
    int srcNode;
    int destNode;
    double value;
    double speed;

    public robot(int id, int src, int dest, double val, double spe, Point3D tempRobot){
        ID=id;
        location=tempRobot;
        srcNode=src;
        destNode=dest;
        value=val;
        speed=spe;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setNextNode(int nextID) {
        destNode=nextID;
    }

    @Override
    public int getNextNode() {
     return destNode;
    }

    @Override
    public Point3D getLocation() {
    return  this.location;
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
}
