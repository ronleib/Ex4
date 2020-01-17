package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

import java.io.Serializable;

public interface robots extends Serializable {


    int getID();

    void setNextNode(int var1);

    int getNextNode();

    Point3D getLocation();

    int getSrcNode();

    double getValue();

    double getSpeed();
}
