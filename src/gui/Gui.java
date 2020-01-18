package gui;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Gamable;
import gameClient.SamCatchRon;
import gameClient.fruit;
/*
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import javafx.scene.paint.Color;
*/
import java.awt.*;
import java.io.FileInputStream;


public class Gui {/*extends Application implements Drawable, EventHandler {

    public static  double screenWidth;
    public static  double screenHeight;
    private static Gamable game;
    private game_service server;
    private  static double maxx ;
    private static double maxy ;
    private  static double miny ;
    private static double minx ;


    //because of this the code stop working
//
//
//    public Gui() {
//        algo=new Graph_Algo();
//        g = new DGraph();
//    }



    @Override
    public void init(Gamable game) {

        if (game instanceof SamCatchRon && !(game.equals(null))) {

            SamCatchRon sGame = (SamCatchRon)game;
            this.game=game;


            if (!sGame.getGameGraph().equals(null)) {
                DGraph g=sGame.getGameGraph().getAlgoGraph();
            }
            else {
                throw new RuntimeException("not a valid graph");
            }


        }
    }



    /**
     *
     * @param data denote some data to be scaled
     * @param r_min the minimum of the range of your data
     * @param r_max the maximum of the range of your data
     * @param t_min the minimum of the range of your desired target scaling
     * @param t_max the maximum of the range of your desired target scaling
     * @return
     **/
    /*
    private double scale(double data, double r_min, double r_max,
                         double t_min, double t_max)
    {

        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }

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
        double maxX = Integer.MIN_VALUE;
        for (int x : g.getNodeMap().keySet()) {
            if (Math.abs(g.getNodeMap().get(x).getLocation().x()) > maxX) {
                maxX = g.getNodeMap().get(x).getLocation().x();

            }
        }
        maxX=maxX;
        return maxX;

    }

    private double maxY(DGraph g) {
        double maxY = Integer.MIN_VALUE;
        for (int x : g.getNodeMap().keySet()) {
            if (Math.abs(g.getNodeMap().get(x).getLocation().y()) > maxY) {
                maxY = g.getNodeMap().get(x).getLocation().y();

            }
        }
        maxy=maxY;
        return maxY;
    }

    private double minY(DGraph g) {
        double minY = Integer.MAX_VALUE;
        for (int x : g.getNodeMap().keySet()) {
            if (g.getNodeMap().get(x).getLocation().y() < minY) {
                minY = g.getNodeMap().get(x).getLocation().y();

            }
        }
        miny=minY;
        return minY;
    }

    private double minX(DGraph g) {
        double minX = Integer.MAX_VALUE;
        for (int x : g.getNodeMap().keySet()) {
            if (g.getNodeMap().get(x).getLocation().x() < minX) {
                minX = g.getNodeMap().get(x).getLocation().x();

            }
        }
        minx=minX;
        return minX;
    }



    private void scaleToGps(OOP_Point3D p ) {
        double p2x = scale(p.x(), minx, maxx, 100, screenWidth * 0.9);
        double p2y = scale(p.y(), miny, maxy, 100, screenHeight * 0.9);
        p= new OOP_Point3D(p2x, p2y, 0);


    }


    @Override
    public void start(Stage stage) throws Exception { //init the graph
        SamCatchRon sGame = (SamCatchRon) game;
        DGraph g = sGame.getGameGraph().getAlgoGraph();
        game_service currServer=sGame.getServer();

        if (g.equals(null)) throw new RuntimeException("Graph is not Exists ");

        stage.setTitle("graph gui");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.height;
        double screenWidth = screenSize.width;


        double maxx = maxX(g);
        double maxy = maxY(g);
        double miny = minY(g);
        double minx = minX(g);

        // load the image
        Image terrotistAImage = new Image(new FileInputStream("1.jpg"));
        Image terrotistBImage = new Image(new FileInputStream("-1.jpg"));





        // Adds a Canvas
        stage.setFullScreen(true);
        // Call getGraphicsContext2D


        ImageView imageView1 = new ImageView();

        imageView1.setFitHeight(screenHeight);
        imageView1.setFitHeight(screenWidth);
        imageView1.setPreserveRatio(true);
        imageView1.setSmooth(true);
        imageView1.setCache(true);


        Group game = new Group();
        Scene scene = new Scene(game);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();


        for (int i : g.getNodeMap().keySet()) {

            //rescale first point
            Point3D p = g.getNodeMap().get(i).getLocation();
            double px = scale(p.x(), minx, maxx, 100, screenWidth * 0.9);
            double py = scale(p.y(), miny, maxy, 100, screenHeight * 0.9);
            p = new Point3D(px, py, 0);

            Circle currVertex = new Circle(p.x(), p.y(), 10);
            currVertex.setFill(Color.BLUE);
            game.getChildren().add(currVertex);
            String key = Integer.toString(i);
            Text textKey = new Text(p.x(), p.y(), key);
            textKey.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 20));
            textKey.setTranslateY(-p.y() * 0.01);
            textKey.setFill(Color.RED);
            game.getChildren().add(textKey);
            if (!(g.getNeighbore().containsKey(i))) continue; // if this node is not connectd
            for (int j : g.getNeighbore().get(i).keySet()) {
                if (g.getNeighbore().get(i).equals(null) || g.getNeighbore().get(i).get(j).equals(null)) continue;
                node_data bro = g.getNodeMap().get((g.getNeighbore().get(i).get(j).getDest()));
                Point3D p2 = new Point3D(bro.getLocation());

                //rescale second point
                double p2x = scale(p2.x(), minx, maxx, 100, screenWidth * 0.9);
                double p2y = scale(p2.y(), miny, maxy, 100, screenHeight * 0.9);
                p2 = new Point3D(p2x, p2y, 0);

                Line Edge = new Line(p.x(), p.y(), p2.x(), p2.y()); //Line(startX,startY,endX,endY)
                Edge.setStyle("-fx-stroke: white;");
                game.getChildren().add(Edge);
                String weight = Double.toString(g.getNeighbore().get(i).get(j).getWeight()); // the weight as String
                weight = weight.substring(0, weight.indexOf('.') + 2); //make it 0.44 insted of 0.444444444444
                if ((p.x() == p2.x()) && (p.y() == p2.y())) continue;
                int WX = (int) ((1 * p.x() + 5 * p2.x()) / 6); //section formula ratio 1:6
                int WY = (int) ((1 * p.y() + 5 * p2.y()) / 6); //section formula ratio 1:6
                Text textWeight = new Text(WX, WY, weight);
                textWeight.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 15));
                textWeight.setTranslateY(-p.y() * 0.01);
                textWeight.setFill(Color.DARKGREEN);
                game.getChildren().add(textWeight);
                Arrow arrow = new Arrow(p.x(), p.y(), WX, WY);
                arrow.setFill(Color.WHITE);
                game.getChildren().add(arrow);


            }


        }
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getSource().toString().equals("Button[id=2, styleClass=button]'Manual'")) {
                    System.out.println("Manual");
                    Text messege = new Text(100, 100,"Manual" );
                    messege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 15));
                    game.getChildren().add(messege);

                } if(e.getSource().toString().equals("Button[id=1, styleClass=button]'Autonomous'")) {
                    System.out.println("Autonomous");
                }}


        };


        // create a button
        Button Autonomous = new Button("Autonomous");
        Autonomous.setOnAction(this);
        Autonomous.setId("1");
        Autonomous.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        Autonomous.setTranslateX(100);
        Autonomous.setTranslateY(20);
        Autonomous.setStyle("-fx-background-color: #008000; ");

        Button Manual = new Button("Manual");
        Manual.setOnAction(this);
        Manual.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
        Manual.setId("2");
        Manual.setTranslateY(20);
        Manual.setStyle("-fx-background-color: #5F9EA0; ");
        StackPane r = new StackPane();

        // add button
        r.getChildren().add(Autonomous);
        r.getChildren().add(Manual);

        game.getChildren().add(r);



        // create a scene
        // create a menu
        Menu m = new Menu("Menu");

        // create menuitems
        MenuItem menuItem1 = new MenuItem("Scenario 1");
        MenuItem menuItem2 = new MenuItem("Scenario 2");
        MenuItem menuItem3 = new MenuItem("Scenario 3");
        MenuItem menuItem4 = new MenuItem("Scenario 4");
        MenuItem menuItem5 = new MenuItem("Scenario 5");
        MenuItem menuItem6 = new MenuItem("Scenario 6");
        MenuItem menuItem7 = new MenuItem("Scenario 7");
        MenuItem menuItem8 = new MenuItem("Scenario 8");
        MenuItem menuItem9 = new MenuItem("Scenario 9");
        MenuItem menuItem10 = new MenuItem("Scenario 10");
        MenuItem menuItem11 = new MenuItem("Scenario 11");
        MenuItem menuItem12 = new MenuItem("Scenario 12");
        MenuItem menuItem13 = new MenuItem("Scenario 13");
        MenuItem menuItem14 = new MenuItem("Scenario 14");
        MenuItem menuItem15 = new MenuItem("Scenario 15");
        MenuItem menuItem16 = new MenuItem("Scenario 16");
        MenuItem menuItem17 = new MenuItem("Scenario 17");
        MenuItem menuItem18 = new MenuItem("Scenario 18");
        MenuItem menuItem19 = new MenuItem("Scenario 19");
        MenuItem menuItem20 = new MenuItem("Scenario 20");
        MenuItem menuItem21 = new MenuItem("Scenario 21");
        MenuItem menuItem22 = new MenuItem("Scenario 22");
        MenuItem menuItem23 = new MenuItem("Scenario 23");
        MenuItem menuItem24 = new MenuItem("Scenario 24");

        // add menu items to menu
        m.getItems().add(menuItem1);
        m.getItems().add(menuItem2);
        m.getItems().add(menuItem3);
        m.getItems().add(menuItem4);
        m.getItems().add(menuItem5);
        m.getItems().add(menuItem6);
        m.getItems().add(menuItem7);
        m.getItems().add(menuItem8);
        m.getItems().add(menuItem9);
        m.getItems().add(menuItem10);
        m.getItems().add(menuItem11);
        m.getItems().add(menuItem12);
        m.getItems().add(menuItem13);
        m.getItems().add(menuItem14);
        m.getItems().add(menuItem15);
        m.getItems().add(menuItem16);
        m.getItems().add(menuItem17);
        m.getItems().add(menuItem18);
        m.getItems().add(menuItem19);
        m.getItems().add(menuItem20);
        m.getItems().add(menuItem21);
        m.getItems().add(menuItem22);
        m.getItems().add(menuItem23);
        m.getItems().add(menuItem24);




        // create a menubar
        MenuBar mb = new MenuBar();

        mb.setTranslateX(200);
        mb.setTranslateY(20);
        mb.setStyle("-fx-background-color: #FFDEAD; ");

        // add menu to menubar
        mb.getMenus().add(m);

        game.getChildren().add(mb);

        stage.setScene(scene);

      stage.show();

        for(int i =0; i<currServer.getFruits().size();i++) { //init forms for the robot's
            fruit f = (fruit) sGame.getFruit()[i];
            ImageView terrotist  = new ImageView();
            if(f.getType()==1) {
                 terrotist  = new ImageView(terrotistAImage);
            }
            else {
                 terrotist  = new ImageView(terrotistBImage);
            }



            OOP_Point3D fPoint = f.getLocation();
            double p2x = scale(fPoint.x(), minx, maxx, 100, screenWidth * 0.9);
            double p2y = scale(fPoint.y(), miny, maxy, 100, screenHeight * 0.9);
            imageView1.setPreserveRatio(true);
            terrotist.setX(p2x-20);
            terrotist.setY(p2y-35);
            terrotist.setFitHeight(70);
            terrotist.setFitWidth(40);
            game.getChildren().add(terrotist);



        }




    }

    @Override
    public void handle(Event event) {
        if(event.getSource().toString()=="b"){
            System.out.println("b");
        }
    }

    public static Gamable getGame() {
        return game;
    }

    public static void setGame(Gamable game) {
        Gui.game = game;
    }

    public game_service getServer() {
        return server;
    }

    public void setServer(game_service server) {
        this.server = server;
    }






    private class Arrow extends Path {
        private static final double defaultArrowHeadSize = 9.0;

        public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize){
            super();
            strokeProperty().bind(fillProperty());
            setFill(Color.BLACK);

            //Line
            getElements().add(new MoveTo(startX, startY));
            getElements().add(new LineTo(endX, endY));

            //ArrowHead
            double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            //point1
            double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
            double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
            //point2
            double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
            double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

            getElements().add(new LineTo(x1, y1));
            getElements().add(new LineTo(x2, y2));
            getElements().add(new LineTo(endX, endY));
        }

        public Arrow(double startX, double startY, double endX, double endY){
            this(startX, startY, endX, endY, defaultArrowHeadSize);
        }
    }


    @Override
    public void PlayGui(String[] args) {
        launch(args);
    }

    public static void main(String[] args) {


    }*/
}
