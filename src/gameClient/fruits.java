package gameClient;

import oop_utils.OOP_Point3D;

import java.io.Serializable;

public interface fruits extends Serializable {

        OOP_Point3D getLocation();

        double getValue();

        int getType();
    }

