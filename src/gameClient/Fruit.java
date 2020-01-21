package gameClient;

import utils.Point3D;

/**
 * This Class represent a Object Type fruit the implement's fruit's interface
 * That " neede to be Catched " by onother object in a
 * a Gamable Inetefece That can be geted from a server
 */
public class Fruit implements Fruits {

    Point3D location;
    double value;
    int type;
    int Catch;
    /**
     *
     * @param fruitValue  the Value  of the fruit
     * @param typ the Type   of the fruit
     * @param pos The Cordinate of the fruit
     */
    public Fruit(double fruitValue, int typ, Point3D pos) {
        value=fruitValue;
        type=typ;
        location=pos;
        Catch=-1;
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

    public int isCatch() {
        return Catch;
    }

    public void setCatch(int aCatch) {
        Catch = aCatch;
    }

}
