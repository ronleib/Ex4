package gameClient;

import Server.Fruit;
import Server.robot;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import gui.Gui;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Iterator;

import static javafx.application.Application.launch;


public class SamCatchRon implements Gamable {
   private game_service[] server = new game_service[24];
    private Graph_Algo GameGraph;
    private Fruit [] fruits;
                private robot [] robots;
    private int cunter = 0;



    private SamCatchRon() {

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
        this.GameGraph = new Graph_Algo();
        if (server.equals(null)) throw new RuntimeException("server is empty  ");
        if (ran < 0 || ran > 24 || (server[ran].equals(null)))
            throw new RuntimeException("naber pley is Does not exist  ");

        GameGraph.initJson(Game_Server.getServer(ran).getGraph());
        Gui gui = new Gui();
        gui.init(this);

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
    public robot[] getRobots(int ran) {
        return robots;
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

    public Fruit[] getFruits() {
        return fruits;
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
        game_service [] arr=new Game_Server[23];
        for (int x=0;x<5;x++){
            arr[x]=Game_Server.getServer(x);}
        test1.builderScenario(arr);
        test1.builderGame(0);
        Gui gui = new Gui();
        gui.init(test1);
        launch(Gui.class, args);  // correct
    }

}

