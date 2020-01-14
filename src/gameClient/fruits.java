package gameClient;

import Server.robot;
import utils.Point3D;

import java.io.Serializable;

public interface fruits extends Serializable {

        Point3D getLocation();

        double getValue();

    //    double grap(robot var1, double var2);

        int getType();
    }

