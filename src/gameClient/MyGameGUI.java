package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Gui;
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

import static javafx.application.Application.launch;


public class MyGameGUI implements MyGameGUI2 {
   private game_service[] server = new game_service[24];
    Graph_Algo GameGraph;
    Fruit [] fruits;
    robot [] robots;
    int cunter = 0;
    StdDraw Game;


    private MyGameGUI() {

    }
//    private game_service[] Game ;
//
//
//    public void MyGameGUI(){
//    for(int x=0;x<24;x++)
//        Game[x]= Game_Server.getServer(x);
//}
//    public  void start(int num){
//        int sumfruits=0;
//        String temp=this.Game[num].getGraph();
//        Graph_Algo gameDGrap=new Graph_Algo();
//        ArrayList temFruits=new ArrayList();
//        gameDGrap.initJson(temp);
//        try {
//            JSONObject line = new JSONObject(Game[num].toString());
//            JSONObject Server = line.getJSONObject("GameServer");
//            int robot = Server.getInt("robots");
//            System.out.println(Game[num].toString());
//            System.out.println(gameDGrap.toString());
//            // the list of fruits should be considered in your solution
//            Iterator<String> f_iter = Game[num].getFruits().iterator();
//            while(f_iter.hasNext()) {
//                temFruits.add(f_iter.next());}
//            Collections.sort(temFruits);
//            /// sort fruits
//            /// TSP MOD ROBOT
//            //
//            int src_node = 0;  // arbitrary node, you should start at one of the fruits
//            for(int a = 0;a<robot;a++) {
//        //        game.addRobot(src_node+a);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

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
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        if (ran < 0 || ran > 24 || (server[ran].equals(null)))
            throw new RuntimeException("naber pley is Does not exist  ");

        GameGraph.initJson(Game_Server.getServer(ran).getGraph());
        double x = maxX(GameGraph.getAlgoGraph());
        double y = maxY(GameGraph.getAlgoGraph());


        Gui gui = new Gui();
        gui.init(GameGraph.getAlgoGraph());

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
        game_service [] arr=new Game_Server[23];
        for (int x=0;x<5;x++){
            arr[x]=Game_Server.getServer(x);}
        test1.builderScenario(arr);
        test1.builderGame(4);
        launch(Gui.class, args);  // correct
    }

}

