package gameClient;

import Server.game_service;
import Server.robot;
import utils.Point3D;

import java.io.Serializable;

public interface fruits extends Serializable {

        void updateLocation(game_service server);

        Point3D getLocation();

        double getValue();



        int getType();
    }

