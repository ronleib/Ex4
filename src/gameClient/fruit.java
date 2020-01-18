package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

public class fruit implements fruits {

    Point3D location;
    double value;
    int type;

    public fruit(double fruitValue, int typ, Point3D pos) {
        value=fruitValue;
        type=typ;
        location=pos;
    }

    @Override
    public Point3D getLocation() {
       return location;
    }

    @Override
    public double getValue() {
       return value;
    }

    @Override
    public int getType() {
        return type;
    }
}
