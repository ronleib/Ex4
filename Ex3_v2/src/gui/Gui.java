package gui;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

import java.awt.*;

public class Gui {

    public static boolean drawGraph(graph g) {

        if (g.equals(null)) return false;

        else if ((g instanceof DGraph)) {
            ((DGraph) g).Gui();
            return true;
        } else if ((g instanceof Graph_Algo)) {
            ((Graph_Algo) g).getAlgoGraph().Gui();
            return true;
        } else {
            return false;
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


    public void paint(DGraph g) {

        double x = maxX(g);
        double y = maxY(g);

        //StdDraw.setScale(x,y);
//        StdDraw.setXscale(-x,x);
//        StdDraw.setYscale(-y,y);

    for (int i : g.getNodeMap().keySet()) {
        StdDraw.setPenColor(Color.BLUE);

        Point3D p = g.getNodeMap().get(i).getLocation();
        StdDraw.filledCircle(p.x(),p.y(),10);
        String key = Integer.toString(i);
        StdDraw.setFont(new Font("font", Font.BOLD, 20));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(p.x(),p.y(),key);
        if(!(g.getNeighbore().containsKey(i))) continue; // if this node is not connectd
        for (int j : g.getNeighbore().get(i).keySet()) {
            if (g.getNeighbore().get(i).equals(null) || g.getNeighbore().get(i).get(j).equals(null)) continue;
            node_data bro = new node();
            bro=g.getNodeMap().get((g.getNeighbore().get(i).get(j).getDest()));
            Point3D p2 = new Point3D(bro.getLocation());
            StdDraw.setPenColor(Color.black);
            StdDraw.line((int) p.x(), (int) p.y(), (int) p2.x(), (int) p2.y());
            StdDraw.setPenColor(Color.red);
            StdDraw.setFont(new Font("Courier", Font.BOLD, 12));
            String weight = new Double(g.getNeighbore().get(i).get(j).getWeight()).toString(); // the weight as String
            weight=weight.substring(0,weight.indexOf('.')+2); //make it 0.44 insted of 0.444444444444
            StdDraw.text((int)((p.x()+p2.x())/2),(int)((p.y()+p2.y())/2),("*"));
            if((p.x()==p2.x())&&(p.y()==p2.y())) continue;
            int WX = (int)((1*p.x()+8*p2.x())/9); //section formula ratio 1:9
            int WY = (int)((1*p.y()+8*p2.y())/9); //section formula ratio 1:9
            StdDraw.text(WX,WY,weight);
            StdDraw.setPenColor(Color.BLACK);
            int inX = (int)((1*p.x()+8*p2.x())/9); //section formula ratio 1:9
            int inY = (int)((1*p.y()+8*p2.y())/9); //section formula ratio 1:9
            StdDraw.filledCircle(inX,inY,10);



        }

    }


    }


}