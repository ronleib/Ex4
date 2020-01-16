package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import gui.Gui;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Iterator;

import static javafx.application.Application.launch;


public class SamCatchRon implements Gamable {
    private game_service[] server = new game_service[24];
    private Graph_Algo GameGraph;
    private Fruit[] fruits;
    private robot[] robots;
    private int cunter = 0;
    private int scenario;


    @Override
    public void builderScenario(game_service[] Server) {
        try {
            for (int x = 0; x < server.length; x++) {
                server[cunter] = Server[x];
                cunter++;
            }
        } catch (Exception exception) {
            System.out.println("Only the first 23 serbs were received " +
                    "There is no more server");
        }
    }

    @Override
    public void builderGame() {
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        if (scenario < 0 || scenario > 24 || (server[scenario].equals(null)))
            throw new RuntimeException("naber pley is Does not exist  ");

        GameGraph.initJson(Game_Server.getServer(scenario).getGraph());
        Gui gui = new Gui();
        gui.init(this);

    }


    @Override
    public void getFruits(String fruitJson) {
        try {
            JSONObject Fruit = new JSONObject(fruitJson);
            JSONArray FruitValue = Fruit.getJSONArray("value");
            JSONArray type = Fruit.getJSONArray("type");
            JSONArray pos = Fruit.getJSONArray("pos");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public robot[] getRobots(String robotJson) {
        robots = new robot[this.server[scenario].getRobots().size()];
        int sum = 0;
        double value;
        int id;
        int type;

        try {
            Iterator<String> f_iter = this.server[scenario].getRobots().iterator();
            while (f_iter.hasNext()) {
                robots[sum] = (robot) JSONObject.stringToValue(f_iter.next());
                JSONObject Robot = new JSONObject(f_iter.toString());
                id =Robot.getInt("id");
                value =Robot.getDouble("value");
                type = Robot.getInt("type");
                sum++;
            }
        } catch (Exception e) {
            System.out.println("Exception robots");
        }
        return  null;
    }



    @Override
    public void addRobot() {
        robots = new robot[this.server[this.scenario].getRobots().size()];
        int index = 0;
        try {
            Iterator<String> f_iter = this.server[this.scenario].getRobots().iterator();
            while (f_iter.hasNext()) {
                robots[index] = (robot) JSONObject.stringToValue(f_iter.next());
                index++;
            }
        } catch (Exception e) {
            System.out.println("Exception robots");
        }
    }

    @Override
    public void Automaticplay() {

    }

    @Override
    public void Manualgame() {

    }

    private void sort(Fruit[] arr) {

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            Fruit key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].getValue() > key.getValue()) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }


    private double angle(Point3D p, Point3D p2) {
        double maxX, maxY, C;
        if (p.x() > p2.x()){
            maxX = p.x()-p2.x();}
        else {maxX = p2.x()- p.x();}
        if (p.y() > p2.y()){
            maxY = p.y()-p2.y();}
        else {maxY = p2.y()-p.y();}
        C =(maxY/maxX);
        C = (Math.atan(C));
        if (p.x()>p2.x())
            return C+180;
        else return C;
    }


    public game_service[] getServer() {
        return server;
    }

    public void setServer(game_service[] server) {
        this.server = server;
    }

    public Graph_Algo getGameGraph() {
        return GameGraph;
    }

    public void setGameGraph(Graph_Algo gameGraph) {
        GameGraph = gameGraph;
    }



    public void setFruits(Fruit[] fruits) {
        this.fruits = fruits;
    }



    public void setRobots(robot[] robots) {
        this.robots = robots;
    }

    public int getCunter() {
        return cunter;
    }

    public void setCunter(int cunter) {
        this.cunter = cunter;
    }

    public static void main(String[] args) {

        SamCatchRon test1 = new SamCatchRon();
        test1.scenario=0;

        game_service [] arr=new Game_Server[23];
        for (int x=0;x<5;x++){
            arr[x]=Game_Server.getServer(x);
            }

        arr[0].addRobot(0);
        arr[0].addRobot(2);
        test1.builderScenario(arr);

        test1.builderGame();
        Gui gui = new Gui();
        gui.init(test1);
        launch(Gui.class, args);  // correct
    }

}

