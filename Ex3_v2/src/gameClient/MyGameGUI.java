package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.oop_edge_data;
import oop_utils.OOP_Point3D;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;
import utils.Point3D;
import utils.StdDraw;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;


public class MyGameGUI implements MyGameGUI2 {
   private game_service[] server = new game_service[24];
    Graph_Algo GameGraph;
    Fruit [] fruits;
    robot [] robots;
    int cunter = 0;
    StdDraw Game;


    private MyGameGUI() {

    }

    @Override
    public void builderScenario(game_service[] Server) {
        try {
            for (int x = 0; x < server.length; x++){
                server[cunter] = Server[x];
            cunter++;}
        } catch (Exception exception) {
            System.out.println("Only the first 23 serbs were received " +
                    "There is no more server");
        }
    }

    @Override
    public void builderGame(int ran) {
        this.GameGraph=new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        if (ran < 0 || ran > 24 || (server[ran].equals(null)))
            throw new RuntimeException("naber pley is Does not exist  ");

        GameGraph.initJson(Game_Server.getServer(ran).getGraph());
        double x = maxX(GameGraph.getAlgoGraph());
        double y = maxY(GameGraph.getAlgoGraph());
        Game.setCanvasSize((int) x, (int) y);
        Game.setXscale(-50, x + 50);
        Game.setYscale(-50, y + 50);
        DGraph g = GameGraph.getAlgoGraph();

        for (int i : g.getNodeMap().keySet()) {
            StdDraw.setPenColor(Color.BLUE);
            Point3D p = g.getNodeMap().get(i).getLocation();
            Game.filledCircle(p.x(), p.y(), 15);
            String key = Integer.toString(i);
            Game.setFont(new Font("font", Font.BOLD, 20));
            Game.setPenColor(Color.RED);
            Game.text(p.x(), p.y() - 2, key);
            if (!(g.getNeighbore().containsKey(i))) continue; // if this node is not connectd
            for (int j : g.getNeighbore().get(i).keySet()) {
                if (g.getNeighbore().get(i).equals(null) || g.getNeighbore().get(i).get(j).equals(null)) continue;
                node_data bro = g.getNodeMap().get((g.getNeighbore().get(i).get(j).getDest()));
                Point3D p2 = new Point3D(bro.getLocation());
                Game.setPenColor(Color.black);
                Game.line((int) p.x(), (int) p.y(), (int) p2.x(), (int) p2.y());
                Game.setPenColor(Color.red);
                Game.setFont(new Font("Courier", Font.BOLD, 15));
                String weight = Double.toString(g.getNeighbore().get(i).get(j).getWeight()); // the weight as String
                weight = weight.substring(0, weight.indexOf('.') + 2); //make it 0.44 insted of 0.444444444444
                Game.text((int) ((p.x() + p2.x()) / 2), (int) ((p.y() + p2.y()) / 2), ("*"));
                if ((p.x() == p2.x()) && (p.y() == p2.y())) continue;
                int WX = (int) ((1 * p.x() + 5 * p2.x()) / 6); //section formula ratio 1:6
                int WY = (int) ((1 * p.y() + 5 * p2.y()) / 6); //section formula ratio 1:6
                Game.text(WX, WY - 10, weight);
                Game.setPenColor(Color.BLACK);
                double inX = ((1 * p.x() + 5 * p2.x()) / 6); //section formula ratio 1:6
                double inY = ((1 * p.y() + 5 * p2.y()) / 6); //section formula ratio 1:6
                Game.setFont(new Font("Courier", Font.BOLD, 20));
                double inC = angle(p, p2);
                Game.text((int) inX, (int) inY, "->", inC);
            }
        }
    }

    @Override
    public void getFruits(int ran) {
       fruits=new Fruit[this.server[ran].getFruits().size()];
       int sum=0;
       try {
           Iterator<String> f_iter = this.server[ran].getFruits().iterator();
           while(f_iter.hasNext()) {
               fruits[sum]=(Fruit)JSONObject.stringToValue(f_iter.next());
               sum++;
           }
           sort(fruits);
        } catch (Exception e) {
           System.out.println("Exception fruits");
        }
    }

    @Override
    public void getPlaber(int ran) {
        robots = new robot[this.server[ran].getRobots().size()];
        int sum = 0;
        try {
            Iterator<String> f_iter = this.server[ran].getRobots().iterator();
            while (f_iter.hasNext()) {
                robots[sum] = (robot) JSONObject.stringToValue(f_iter.next());
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

    private double maxX(DGraph g) {
        double maxX = 0;
        for (int x : g.getNodeMap().keySet()) {
            if (Math.abs(g.getNodeMap().get(x).getLocation().x()) > maxX) {
                maxX = Math.abs(g.getNodeMap().get(x).getLocation().x());

            }
        }
        return maxX;

    }

    private double maxY(DGraph g) {
        double maxY = 0;
        for (int x : g.getNodeMap().keySet()) {
            if (Math.abs(g.getNodeMap().get(x).getLocation().y()) > maxY) {
                maxY = Math.abs(g.getNodeMap().get(x).getLocation().y());

            }
        }
        return maxY;
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


    public static void main(String[] args) {
        MyGameGUI test1 = new MyGameGUI();
        game_service [] arr=new Game_Server[5];
        for (int x=0;x<5;x++){
            arr[x]=Game_Server.getServer(x);}
        test1.builderScenario(arr);
        test1.builderGame(1);
    }
}

