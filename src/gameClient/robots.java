package gameClient;

import oop_utils.OOP_Point3D;
import utils.Point3D;

import java.io.Serializable;


/**
 * This Interface represent a Object Type That "Catch" Anothe object in a
 * a Gamable Inetefece That can be geted from a server
 */
public interface robots extends Serializable {

    /**
     *
     * @return The ID Of the Robot
     */
    int getID();

    void setNextNode(int var1);

    /**
     *
     * @return The Next Node of The Robot
     */
    int getNextNode();


    /**
     *
     * @return The Location Of The Robot In GPS Cordinate
     */
    Point3D getLocation();


    /**
     *
     * @return Return The SrcNode Of The Robot
     */
    int getSrcNode();

    /**
     *
     * @return The Value of The Robot
     */
    double getValue();


    /**
     *
     * @return The Speed of The Robot
     */
    double getSpeed();

    /**
     *
     * @param location Set's The Location Of The Robot
     */
    void setLocation(Point3D location);
}
