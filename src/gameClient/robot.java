package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

public class robot implements robots {

    int ID;
    OOP_Point3D location;
    int currNode;
    int nextNode;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setNextNode(int nextID) {
        nextNode=nextID;
    }

    @Override
    public int getNextNode() {
     return this.nextNode;
    }

    @Override
    public OOP_Point3D getLocation() {
    return  this.location;
    }

    @Override
    public int getSrcNode() {
     return currNode;
    }



}
