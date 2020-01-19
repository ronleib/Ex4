package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

/**
 * This Class represent a Object Type fruit the implement's fruit's interface
 * That " neede to be Catched " by onother object in a
 * a Gamable Inetefece That can be geted from a server
 */
public class fruit implements fruits {

    Point3D location;
    double value;
    int type;

    /**
     *
     * @param fruitValue  the Value  of the fruit
     * @param typ the Type   of the fruit
     * @param pos The Cordinate of the fruit
     */
    public fruit(double fruitValue, int typ, Point3D pos) {
        value=fruitValue;
        type=typ;
        location=pos;
    }

    /**
     *
     * @return The location of the fruit
     */
    @Override
    public Point3D getLocation() {
       return location;
    }

    /**
     *
     * @return The Point of the fruit
     */
    @Override
    public double getValue() {
       return value;
    }

    /**
     *
     * @return The Type of the fruit
     */
    @Override
    public int getType() {
        return type;
    }
}
