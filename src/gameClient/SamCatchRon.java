package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
//import gui.Gui;
import dataStructure.edge;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import static javafx.application.Application.launch;


public class SamCatchRon implements Gamable {
    private game_service server;
    private Graph_Algo GameGraph;
    private fruits[] fruit;
    private robot[] robots;
    private int cunter = 0;
    private int scenario;
    private int seem = 0;
    private HashMap<Long, edge_data> edgeMap;

    @Override
    public void SamCatchRon(int index) {
        try {
            scenario = index;
            server = Game_Server.getServer(index);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void builderGame() {
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        GameGraph.initJson(server.getGraph().toString());
        HashMap<Long, edge_data> edgeMap = new HashMap<Long, edge_data>();
        for (int x : GameGraph.getAlgoGraph().getNeighbore().keySet()) {
            for (int y : GameGraph.getAlgoGraph().getNeighbore().get(x).keySet()) {
                edge_data temp = GameGraph.getAlgoGraph().getNeighbore().get(x).get(y);
                if (edgeMap.containsKey(temp.getDistance())) {
                    edgeMap.put(temp.getDistance(), temp);
                } else seem++;
            }
        }

        //  Gui gui = new Gui();
        //  gui.init(this);
    }

    @Override
    public void getFruits() {
        fruit = new fruit[server.getFruits().size()];
        int sum = 0;
        try {
            JSONArray Fruit2 = new JSONArray(server.getFruits());
            while (sum < Fruit2.length()) {
                JSONArray Fruit1 = Fruit2.getJSONArray(sum);
                JSONObject Fruit = Fruit1.getJSONObject(sum);
                double FruitValue = Fruit.getDouble("value");
                int type = Fruit.getInt("type");
                Point3D pos = new Point3D(Fruit.getString("pos"));
                fruit[sum] = new fruit(FruitValue, type, pos);
                sum++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sort(fruit);
    }

    @Override
    public robot[] getRobots() {
        if (this.robots != null) return robots;
        throw new RuntimeException("0-robot ");
    }

    @Override
    public void addRobot() {
        robots = new robot[server.getRobots().size()];
        int sum = 0;
        double value, speed;
        int id, type, src, dest;
        Point3D tempRobot;

        try {
            Iterator<String> f_iter = server.getRobots().iterator();
            while (f_iter.hasNext()) {
                JSONObject Robot = new JSONObject(f_iter.toString());
                id = Robot.getInt("id");
                value = Robot.getDouble("value");
                src = Robot.getInt("src");
                dest = Robot.getInt("dest");
                speed = Robot.getDouble("value");
                tempRobot = new Point3D(Robot.getString("pos"));
                robots[sum] = (robot) new gameClient.robot(id, src, dest, value, speed, tempRobot);
                sum++;
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
        boolean flag = true;
        Point3D x;
        robot ran = robots[0];
        while (flag) {
            /*
            ////// ran
            punktia
          klik on node
          klik next node
          semun index x.y.p3
            if (this.GameGraph.getAlgoGraph().getNodeMap().containsKey(x))

        }
        node_data str = GameGraph.getAlgoGraph().getNode(ran.getSrcNode());
        node_data nex = GameGraph.getAlgoGraph().getNode(ran.getNextNode());
        edge_data edgeRobot = new edge(str,nex, 0);
        for (int i=0; i<fruit.length;i++){

            if(fruit[i].getLocation().x()>
        }
        eater(edgeRobot, fruit);
//        if (this.edgeMap.containsKey(temp.getDistance())){
*/
        }
    }


    private boolean eater(edge_data edgeRobot, fruits[] fruit) {

        long caker=DistDest(edgeRobot,fruit)+DistSrc(edgeRobot,fruit);
        if (this.edgeMap.containsKey(caker))
            return true;
        return false;
    }

    public game_service getServer() {
        return server;
    }

    public Graph_Algo getGameGraph() {
        return GameGraph;
    }

    public fruits[] getFruit() {
        return fruit;
    }

    public int getCunter() {
        return cunter;
    }

    public int getScenario() {
        return scenario;
    }

    /**
     * A function that sorts the points that need to be captured
     * by an entire exporter from the
     * largest to the smaller
     * @param sort arr
     */
    private void sort(fruits [] arr) {
        if (fruit==null)return;
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            fruits key = arr[i];
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

    /**
     * A function that returns the length of the edge
     * for that dest-fruis
     * @param data
     * @param checker
     * @return length edge (dest-fruis)
     */

    public double DistDest(edge_data data,fruits checker) {
        long Dx, Dy, D;
        Point3D tempDest=this.GameGraph.getAlgoGraph().getNode(data.getDest()).getLocation();
        Dx = (long) (checker.getLocation().x() - tempDest.x());
        Dx = Dx * Dx;
        Dy = (long) (checker.getLocation().y() - tempDest.y());
        Dy = Dy * Dy;
        D = (long) Math.sqrt(Dy + Dx);
        return D;
    }

    /**
     * A function that returns the length of the edge
     * for that Src-fruis
     * @param data
     * @param checker
     * @return length edge (Src-fruis)
     */

    public double DistSrc(edge_data data,fruits checker) {
        long Dx, Dy, D;
        Point3D tempSrc=(this.GameGraph.getAlgoGraph().getNode(data.getSrc()).getLocation());
        Dx = (long) (checker.getLocation().x() - tempSrc.x());
        Dx = Dx * Dx;
        Dy = (long) (checker.getLocation().y() - tempSrc.y());
        Dy = Dy * Dy;
        D = (long) Math.sqrt(Dy + Dx);
        return D;
    }

    public static void main(String[] args) {
        SamCatchRon temp = new SamCatchRon();
        temp.SamCatchRon(1);
        temp.server.startGame();
        temp.builderGame();
        temp.addRobot();
        temp.getFruits();
        temp.server.move();
        while (Game_Server.getServer(1).isRunning()){
            temp.addRobot();
            temp.getFruits();
            temp.server.move();
    }
    }
}

