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
        if (g.equals(null)) throw new RuntimeException("Graph is not Exists ");

        double x = maxX(g);
        double y = maxY(g);
        StdDraw.setCanvasSize((int) x, (int) y);
        StdDraw.setXscale(-50, x + 50);
        StdDraw.setYscale(-50, y + 50);

        for (int i : g.getNodeMap().keySet()) {
            StdDraw.setPenColor(Color.BLUE);
            Point3D p = g.getNodeMap().get(i).getLocation();
            StdDraw.filledCircle(p.x(), p.y(), 15);
            String key = Integer.toString(i);
            StdDraw.setFont(new Font("font", Font.BOLD, 20));
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(p.x(), p.y() - 2, key);
            if (!(g.getNeighbore().containsKey(i))) continue; // if this node is not connectd
            for (int j : g.getNeighbore().get(i).keySet()) {
                if (g.getNeighbore().get(i).equals(null) || g.getNeighbore().get(i).get(j).equals(null)) continue;
                node_data bro = g.getNodeMap().get((g.getNeighbore().get(i).get(j).getDest()));
                Point3D p2 = new Point3D(bro.getLocation());
                StdDraw.setPenColor(Color.black);
                StdDraw.line((int) p.x(), (int) p.y(), (int) p2.x(), (int) p2.y());
                StdDraw.setPenColor(Color.red);
                StdDraw.setFont(new Font("Courier", Font.BOLD, 15));
                String weight = Double.toString(g.getNeighbore().get(i).get(j).getWeight()); // the weight as String
                weight = weight.substring(0, weight.indexOf('.') + 2); //make it 0.44 insted of 0.444444444444
                StdDraw.text((int) ((p.x() + p2.x()) / 2), (int) ((p.y() + p2.y()) / 2), ("*"));
                if ((p.x() == p2.x()) && (p.y() == p2.y())) continue;
                int WX = (int) ((1 * p.x() + 5 * p2.x()) / 6); //section formula ratio 1:6
                int WY = (int) ((1 * p.y() + 5 * p2.y()) / 6); //section formula ratio 1:6
                StdDraw.text(WX, WY - 10, weight);
                StdDraw.setPenColor(Color.BLACK);
                double inX =  ((1 * p.x() + 5 * p2.x()) / 6); //section formula ratio 1:6
                double inY =  ((1 * p.y() + 5 * p2.y()) / 6); //section formula ratio 1:6
                StdDraw.setFont(new Font("Courier", Font.BOLD, 20));
                double inC = angle(p, p2);
                StdDraw.text((int) inX, (int) inY, "->", inC);
            }

        }
    }

    private double angle(Point3D p, Point3D p2) {
        double maxX, maxY, C,M;
        boolean flag=false;
        if (p.x() > p2.x()){
            maxX = p.x();
            flag = true;}
        else maxX = p2.x();
        if (p.y() > p2.y())
            maxY = p.y();
        else maxY = p2.y();
        C = Math.sqrt((maxX * maxX) + (maxY * maxY));

        M=(p.y()-p2.y())/(p.x()-p2.x());
        if (p.x()>p2.x())
        return (C/maxY)+180;
        else return (C/maxY);
    }


}
