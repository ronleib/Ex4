package gui;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Gamable;
import gameClient.SamCatchRon;
import gameClient.fruit;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileInputStream;


public class Gui extends Application implements Drawable, EventHandler {

    public static  double screenWidth;
    public static  double screenHeight;
    private static Gamable sgame;
    private game_service server;
    private  static double maxx ;
    private static double maxy ;
    private  static double miny ;
    private static double minx ;
    static Image terrotistAImage ;
    static Image terrotistBImage ;

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
            this.sgame =game;


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
     */
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
        maxx=maxX;
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
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        // load the image
       terrotistAImage= new Image(new FileInputStream("1.jpg"));
        terrotistBImage= new Image(new FileInputStream("-1.jpg"));
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
        root.getChildren().add(game);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);
        HBox box = new HBox();


        Menu m = new Menu("Menu");
        MenuItem [] menuItems = new MenuItem[24];
        for(int i =0 ; i<24 ; i++) {
            menuItems[i]= new MenuItem("Scenario"+Integer.toString(i+1));
            m.getItems().add(menuItems[i]);
            int finalI = i;
            int finalI1 = i;
            menuItems[i].setOnAction(e -> {
                game.getChildren().clear();
                sgame=new SamCatchRon();
                sgame.SamCatchRon(finalI1);
                sgame.builderGame();
                sgame.getFruits();
                DrawGame(game);
            System.out.println(finalI+1);

            });
        }

        // create a menubar
        MenuBar mb = new MenuBar();

        mb.setTranslateX(200);
        mb.setTranslateY(20);
        mb.setStyle("-fx-background-color: #FFDEAD; ");

        // add menu to menubar
        mb.getMenus().add(m);
        Group buttonsGroup = new Group();
        buttonsGroup.getChildren().add(mb);

        stage.setScene(scene);



        stage.setTitle("graph gui");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         screenHeight = screenSize.height;
         screenWidth = screenSize.width;
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
                }
                else System.out.println(e.toString());
            }


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

        buttonsGroup.getChildren().add(r);
        root.getChildren().add(buttonsGroup);
        stage.show();
    }




    public  void DrawGame(Group game ) {

        Group terroristGrop = new Group();
        Group fruitGroup = new Group();

        //init the graph
        SamCatchRon sGame = (SamCatchRon) sgame;
        DGraph g = sGame.getGameGraph().getAlgoGraph();
        game_service currServer=sGame.getServer();

        if (g.equals(null)) throw new RuntimeException("Graph is not Exists ");

        double maxx = maxX(g);
        double maxy = maxY(g);
        double miny = minY(g);
        double minx = minX(g);


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





//        Button[] digitButtons = new Button[10];
//        for(int i = 0; i < 10; i++) {
//            final int buttonInd = i;
//            digitButtons[i] = new Button(Integer.toString(i));
//            digitButtons[i].setOnAction(e -> {
//                System.out.println("Button pressed " + ((Button) e.getSource()).getText());
//                lastClickedIndex = buttonInd;
//            });
//        }


        fruitGroup.getChildren().removeAll();
        for(int i =0; i<currServer.getFruits().size();i++) { //init forms for the robot's
            fruit f = (fruit) sGame.getFruit()[i];
            ImageView terrotist  = new ImageView();
            if(f.getType()==1) {
                terrotist  = new ImageView(terrotistAImage);
            }
            else {
                terrotist  = new ImageView(terrotistBImage);
            }
            Point3D fPoint = f.getLocation();
            double p2x = scale(fPoint.x(), minx, maxx, 100, screenWidth * 0.9);
            double p2y = scale(fPoint.y(), miny, maxy, 100, screenHeight * 0.9);
            terrotist.setX(p2x-20);
            terrotist.setY(p2y-35);
            terrotist.setFitHeight(70);
            terrotist.setFitWidth(40);
            fruitGroup.getChildren().add(terrotist);
            game.getChildren().remove(fruitGroup);
            game.getChildren().add(fruitGroup);
           // terrotist.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }


    }

    public static Gamable getSgame() {
        return sgame;
    }

    public static void setSgame(Gamable sgame) {
        Gui.sgame = sgame;
    }

    public game_service getServer() {
        return server;
    }

    public void setServer(game_service server) {
        this.server = server;
    }

    @Override
    public void handle(Event event) {

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


    }
}
