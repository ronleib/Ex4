package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
//import gui.Gui;
import dataStructure.DGraph;
import dataStructure.edge;
import dataStructure.edge_data;
import dataStructure.node_data;
import gui.Gui;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import oop_utils.OOP_Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.*;

import static javafx.application.Application.launch;

//import static javafx.application.Application.launch;

//import static javafx.application.Application.launch;

/**
 * This class represent a Utils for the game  catch The Terrorist.
 * The  class communicate with the server to send and receive data from it ;
 * This game uses Datastucture method's such as Shrtest path and more for the game algorithm
 *
 *
 */
public class killTheTerrorists implements Gamable {
    private game_service server;
    private Graph_Algo GameGraph;
    private fruits[] fruits;
    private robots[] robots;
    private int cunter = 0;
    private int scenario;
    private int seem = 0;
    private HashMap<Integer, edge_data> edgeMap = new HashMap<Integer, edge_data>();
    ;

    /**
     * initiate a game instance with the specified scene
     * @param scene the game Scene
     */
    @Override
    public void SamCatchRon(int scene) {
        try {
            scenario = scene;
            server = Game_Server.getServer(scene);
            initRobot();
            initFruits();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * initiate the gameGraph from json with the current scene
     */
    @Override
    public void builderGame() {
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        GameGraph.initJson(server.getGraph().toString());

        //  Gui gui = new Gui();
        //  gui.init(this);
    }

    /**
     * initiate the fruits from json with the current scene
     */
    @Override
    public void initFruits() {
        fruits = new fruit[server.getFruits().size()];
        int sum = 0;
        try {
            String TEMP = server.getFruits().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject Fruit = new JSONObject(temp2.get(sum).toString());
                JSONObject f = new JSONObject(Fruit.getJSONObject("Fruit").toString());
                double FruitValue = f.getDouble("value");
                int type = f.getInt("type");
                Point3D pos = new Point3D(f.getString("pos").toString());
                fruits[sum] = new fruit(FruitValue, type, pos);
                sum++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sort(fruits);
        //initedgeFruit();
    }





    /**
     *
     * @return The fruits arr of the cur scene
     */
    @Override
    public fruits[] getFruits() {
        if (this.fruits != null) return fruits;
        throw new RuntimeException("0-fruit ");
    }

    /**
     *
     * @return The Robots arr of the cur scene
     */
    @Override
    public robots[] getRobots() {
        if (this.robots != null) return robots;
        throw new RuntimeException("0-robot ");
    }




    /**
     * Initiate The robot's arr of the curren scene
     */
    @Override
    public void initRobot() {
        robots = new robots[server.getRobots().size()];
        int sum = 0;
        double value, speed;
        int id, type, src, dest;
        Point3D LocationRobot;

        try {
            JSONObject line = new JSONObject(server.toString());
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            robots = new robots[rs];
            for (int rob = 0; rob < rs; rob++) {
                server.addRobot(rob + 1);
            }
            String TEMP = server.getRobots().toString();
            JSONArray temp2 = new JSONArray(TEMP);
            while (sum < temp2.length()) {
                JSONObject RobotT = new JSONObject(temp2.get(sum).toString());
                JSONObject Robot = new JSONObject(RobotT.getJSONObject("Robot").toString());
                id = Robot.getInt("id");
                value = Robot.getDouble("value");
                src = Robot.getInt("src");
                dest = Robot.getInt("dest");
                speed = Robot.getDouble("speed");
                robots[sum] = new gameClient.robot(id, src, dest, value, speed);
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
        List<String> log = server.move();
        if (log != null) {
            long t = server.timeToEnd();
            for (int i = 0; i < robots.length; i++) {

                if (robots[i].getNextNode() == -1) {
//                    robots[i].setNextNode(nextNode(GameGraph, robots[i].getSrcNode()));
                    server.chooseNextEdge(robots[i].getID(), robots[i].getNextNode());
                    System.out.println("Turn to node: " + robots[i].getNextNode() + "  time to end:" + (t / 1000)); }

            }
        }
    }
//    private static int nextNode(Graph_Algo g, int nextnode) {
//        g.
//
//
//        int ans = -1;
//        Collection<oop_edge_data> ee = g.getE(src);
//        Iterator<oop_edge_data> itr = ee.iterator();
//        int s = ee.size();
//        int r = (int)(Math.random()*s);
//        int i=0;
//        while(i<r) {itr.next();i++;}
//        ans = itr.next().getDest();
//        return ans;
//    }
//
//}


    /**
     * Funtion to move The Robot
     * @param sum
     * @return A point 3D
     */
    private Point3D startmoveRobot(int sum) {
        if (this.fruits.length <= 0) {
            throw new RuntimeException("Fruit is not Exists u not need robot ");
        } else {
            int index = (sum % fruits.length);
            if (edgeMap.containsKey(index)) {
                edge_data edgeFru = this.edgeMap.get(index);
                int stc = edgeFru.getSrc();
                int dec = edgeFru.getDest();
                edge_data Nodestc = GameGraph.getAlgoGraph().getEdge(stc, dec);
                if (GameGraph.getAlgoGraph().contains(dec, stc)) {
                    edge_data Nodedec = GameGraph.getAlgoGraph().getEdge(dec, stc);
                    if (Nodestc.getWeight() > Nodedec.getWeight()) {
                        return GameGraph.getAlgoGraph().getNode(dec).getLocation();
                    } else return GameGraph.getAlgoGraph().getNode(stc).getLocation();
                } else {
                    return GameGraph.getAlgoGraph().getNode(stc).getLocation();
                }
            } else throw new RuntimeException("Fruit is not Exists on smting graf tehy edge ");
        }
    }

    public edge_data getEdgeFruit(int x){
        if (this.edgeMap.containsKey(x)) throw new RuntimeException("Fruit is not Exists ");
        return this.edgeMap.get(x);
    }
    public game_service getServer() {
        return server;
    }

    public Graph_Algo getGameGraph() {
        return GameGraph;
    }

    public int getCunter() {
        return cunter;
    }

    public int getScenario() {
        return scenario;
    }



//    /**
//     * Finds the rescue that fruit
//     * You sat Alb and put it in O(v*n)
//     * we could eventually pull it O(1)
//     */
//    private void initedgeFruit(){
//
//
//
//        for (int i=0; i<fruits.length;i++)
//            if(!this.edgeMap.containsKey(i)){
//
//                for (int x : GameGraph.getAlgoGraph().getNeighbore().keySet()) {
//                    for (int y : GameGraph.getAlgoGraph().getNeighbore().get(x).keySet()) {
//                        edge_data temp = GameGraph.getAlgoGraph().getNeighbore().get(x).get(y);
//                        if (checker(temp,fruits[i])) {
//                            edgeMap.put(i,temp);
//                        }
//                    }
//                }
//            }
//    }



    /**
     * Internal function that checks if the point of fruits
     * between the two points
     * @param temp
     * @param fruitsChecker
     * @return t,f
     */

    private boolean checker(edge_data temp, fruits fruitsChecker) {
        double DxE = this.GameGraph.getAlgoGraph().getNode(temp.getDest()).getLocation().x();
        double DyE = this.GameGraph.getAlgoGraph().getNode(temp.getDest()).getLocation().y();
        double SxE = this.GameGraph.getAlgoGraph().getNode(temp.getSrc()).getLocation().y();
        double SyE = this.GameGraph.getAlgoGraph().getNode(temp.getSrc()).getLocation().y();
        double Fx  = fruitsChecker.getLocation().x();
        double Fy  = fruitsChecker.getLocation().y();
        boolean X  = false, Y = false;

        if (((DxE < Fx) && (Fx < SxE)) || ((DxE > Fx) && (Fx > SxE))) {
            X = true;
        } else return false;
        if (((DyE < Fx) && (Fy < SyE)) || ((DyE > Fy) && (Fy > SyE))) {
            Y = true;
        } else return false;

        return (X && Y);
    }

    /**
     * A function that sorts the points that need to be captured
     * by an entire exporter from the
     * largest to the smaller
     * @param  arr
     */
    private void sort(fruits [] arr) {
        if (fruits==null)return;
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
        double Dx, Dy, D;
        Point3D tempDest=this.GameGraph.getAlgoGraph().getNode(data.getDest()).getLocation();
        Dx = (checker.getLocation().x() - tempDest.x());
        Dx = Dx * Dx;
        Dy = (checker.getLocation().y() - tempDest.y());
        Dy = Dy * Dy;
        D = Math.sqrt(Dy + Dx);
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
        double Dx, Dy, D;
        Point3D tempSrc=(this.GameGraph.getAlgoGraph().getNode(data.getSrc()).getLocation());
        Dx = (checker.getLocation().x() - tempSrc.x());
        Dx = Dx * Dx;
        Dy = (checker.getLocation().y() - tempSrc.y());
        Dy = Dy * Dy;
        D =  Math.sqrt(Dy + Dx);
        return D;
    }




    @Override
    public String toString() {
        return "SamCatchRon{" +
                "server=" + server +
                ", GameGraph=" + GameGraph +
                ", fruit=" + Arrays.toString(fruits) +
                ", robots=" + Arrays.toString(robots) +
                ", cunter=" + cunter +
                ", scenario=" + scenario +
                ", seem=" + seem +
                ", edgeMap=" + edgeMap +
                '}';
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
        launch(Gui.class, args);  // correct	        launch(Gui.class, args);
    }
}

