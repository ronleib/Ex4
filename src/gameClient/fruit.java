package gameClient;

import oop_utils.OOP_Point3D;

public class fruit implements fruits {

    OOP_Point3D location;
    double value;
    int type;

    @Override
    public OOP_Point3D getLocation() {
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
