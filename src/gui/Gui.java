package gui;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.node_data;
import gameClient.Gamable;
import gameClient.killTheTerrorists;
import gameClient.fruit;
import gameClient.robot;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oop_utils.OOP_Point3D;
import utils.Point3D;

import javafx.scene.paint.Color;

import java.awt.Dimension;

import java.awt.Toolkit;
import java.io.FileInputStream;

/**
 * This class represent a Gui as JavaFX Application of the Game Catch The Terrorist
 * This class can be changed to Draw all the games the iplement Gamable Interface,
 * The Game presented Here Show a Menu at start when you choose a game mode and afterword's
 * you can choose a game mode outomatic or manual and start tha game at ich mode.
 *
 */
public class Gui extends Application implements Drawable, EventHandler {

    public static  long nowFirst = 100000;
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
    static Image heli ;
    static  int robotCounter ;
    private static Group robotGroup;
    private  static Group fruitGroup;
    private  static Group messeges;
    private  static Group game;
    private  static  int robotMax;


//because of this the code stop working
//
//
//    public Gui() {
//        algo=new Graph_Algo();
//        g = new DGraph();
//    }



    @Override
    public void init(Gamable game) {

        if (game instanceof killTheTerrorists && !(game.equals(null))) {

            killTheTerrorists sGame = (killTheTerrorists)game;
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


    /**
     * Function to find the max of x cordinate in a graph.
     * @param g The Graph Where the search is done
     * @return the max X Value as doublr
     */
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
    /**
     * Function to find the max of y cordinate in a graph.
     * @param g The Graph Where the search is done
     * @return the max Y Value as doublr
     */
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

    /**
     * Function to find the min of y cordinate in a graph.
     * @param g The Graph Where the search is done
     * @return the min y Value as doublr
     */
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
    /**
     * Function to find the min of x cordinate in a graph.
     * @param g The Graph Where the search is done
     * @return the min X Value as doublr
     */
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


    /**
     * Function to scale a point to Acording to the Gps min and max (x,y)
     * @param p The point to be scaled
     */
    private void scaleToGps(OOP_Point3D p ) {
        double p2x = scale(p.x(), minx, maxx, 100, screenWidth * 0.9);
        double p2y = scale(p.y(), miny, maxy, 100, screenHeight * 0.9);
        p= new OOP_Point3D(p2x, p2y, 0);


    }

    /**
     *
     * @param stage Primary stage
     * @throws Exception if anything is Wrong in the input Exeption will come in place
     * This function creat a game menu from where the game can be initiated
     * The function cread 3 main buttons of game scene , Aoutomatic Mode or Manual Mode.
     */
    @Override
    public void start(Stage stage) throws Exception {
        if(server!=(null)&&server.isRunning()) server.stopGame();
        timeGame.stop();
        robotGroup= new Group();
        fruitGroup = new Group();
        messeges = new Group();

        //set the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;


        Group root = new Group();

        // load the image
        terrotistAImage= new Image(new FileInputStream("1.jpg"));
        terrotistBImage= new Image(new FileInputStream("-1.jpg"));
        heli= new Image(new FileInputStream("heli.png"));


        Text startMessege = new Text(screenWidth/4, 50, "Chose a Game Scenario at Menu");
        startMessege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 50));
        messeges.getChildren().add(startMessege);
        ImageView teror = new ImageView(heli);
        teror.setTranslateY(100);
        messeges.getChildren().add(teror);
        startMessege.setFill(Color.DARKGREEN);





        // Adds a Canvas
        stage.setFullScreen(true);
        // Call getGraphicsContext2D

        ImageView imageView1 = new ImageView();

        imageView1.setFitHeight(screenHeight);
        imageView1.setFitHeight(screenWidth);
        imageView1.setPreserveRatio(true);
        imageView1.setSmooth(true);
        imageView1.setCache(true);


         game = new Group();
        root.getChildren().add(game);
        game.getChildren().add(messeges);
        game.getChildren().add(robotGroup);
        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        Menu m = new Menu("Menu");
        MenuItem [] menuItems = new MenuItem[24];
        for(int i =0 ; i<24 ; i++) {
            menuItems[i]= new MenuItem("Scenario"+Integer.toString(i+1));
            m.getItems().add(menuItems[i]);
            int finalI = i;
            int finalI1 = i;
            menuItems[i].setOnAction(e -> {
                messeges.getChildren().clear();
                game.getChildren().clear();
                sgame=new killTheTerrorists();
                sgame.SamCatchRon(finalI1);
                sgame.builderGame();
                sgame.getFruits();
                DrawGame(game);
                Text gameModeMessege = new Text(screenWidth/4, 80, "To Start  Chose a Game Game Mode ");
                gameModeMessege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 50));
                gameModeMessege.setFill(Color.DARKGREEN);
                messeges.getChildren().add(gameModeMessege);


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

        /**
         * Hanle the Mouse Event of The start Method
         */
        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getSource().toString().equals("Button[id=2, styleClass=button]'Manual'")) {
                    System.out.println("Manual");
                    root.getChildren().remove(messeges);
                    game.getChildren().remove(messeges);
                    menualGame(root);

                } else if(e.getSource().toString().equals("Button[id=1, styleClass=button]'Autonomous'")) {
                    automatic();
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




    /**
     * This function Draw a game graph with the Selected scene from the Start (previus ) function
     * Afterword You can Strat Plaing The game Acording to a game mode You choose
     * @param game The ggame group
     */
    public  void DrawGame(Group game ) {
        robotMax=0;
        messeges.getChildren().clear();
        fruitGroup.getChildren().clear();
        robotGroup.getChildren().clear();
        //init the graph
        killTheTerrorists sGame = (killTheTerrorists) sgame;
        DGraph g = sGame.getGameGraph().getAlgoGraph();
       server=sGame.getServer();

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


        for (int i : g.getNodeMap().keySet()) {
            Point3D p = g.getNodeMap().get(i).getLocation();
            double px = scale(p.x(), minx, maxx, 100, screenWidth * 0.9);
            double py = scale(p.y(), miny, maxy, 100, screenHeight * 0.9);
            p = new Point3D(px, py, 0);
            String key = Integer.toString(i);
            Text textKey = new Text(p.x(), p.y(), key);
            textKey.setId(key);
            textKey.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 30));
            textKey.setTranslateY(-p.y() * 0.01);
            textKey.setFill(Color.RED);
            game.getChildren().add(textKey);


        }

        game.getChildren().add(robotGroup);
        drawFruits();
        drawRobots();



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


    /**
     * Depreceted At the moment function But  MAy be Used in Next Update
     * @param event
     */
    @Override
    public void handle(Event event) {
//Come soon
    }


    /**
     * Private class to Draw The Arrows at The graph Edges This Code Was Taken From OpenSource Code
     */
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



    public void updateRobots() {
        sgame.updateRobot();
        for (int i = 0; i < robotGroup.getChildren().size(); i++) { //init forms for the robot's
            robot r = (robot) sgame.getRobots()[i];
            Point3D fPoint = r.getLocation();
            double p2x = scale(fPoint.x(), minx, maxx, 100, screenWidth * 0.9);
            double p2y = scale(fPoint.y(), miny, maxy, 100, screenHeight * 0.9);
            robotGroup.setTranslateX(p2x);
            robotGroup.setTranslateY(p2y);


        }
    }


    public void drawFruits() {
        fruitGroup.getChildren().clear();
        sgame.initFruits();
        for(int i =0; i<server.getFruits().size();i++) { //init forms for the robot's
            fruit f = (fruit) sgame.getFruits()[i];
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

        }



    }


    public void drawRobots() {
        robotCounter=0;
        robotGroup.getChildren().clear();
        sgame.updateRobot();
        for (int i = 0; i < sgame.getRobots().length; i++) { //init forms for the robot's
            robot r = (robot) sgame.getRobots()[i];
            Point3D fPoint = r.getLocation();
            double p2x = scale(fPoint.x(), minx, maxx, 100, screenWidth * 0.9);
            double p2y = scale(fPoint.y(), miny, maxy, 100, screenHeight * 0.9);
            ImageView heliCopter = new ImageView(heli);
            heliCopter.setId("heli: #"+r.getID()+"*");
            robotCounter++;
            heliCopter.setX(p2x-45);
            heliCopter.setY(p2y-30);
            heliCopter.setFitHeight(60);
            heliCopter.setFitWidth(90);
            robotGroup.getChildren().add(heliCopter);
        }



    }




    /**
     * Function To Play in Manual Game Mode
     * TO Use The Function First of all You should Place
     * A Number of "Robot's" (Helicopter's ) AfterWord
     * YOu can Press an helicopter and press on aNode at the graph and the helicopter will move there
     * and The game will Run untill The Server Will Stop it
     * @param game
     */
    public void menualGame(Group game) {


        robotMax =5;
        final int[] currHelicopter = {-1};


        killTheTerrorists mGame = (killTheTerrorists) sgame;
        server=mGame.getServer();
        System.out.println(server.toString());
      //  System.out.println(mGame);



        Text messege =  new Text(screenWidth/6, 50, "Your on Menual Mode To Start place a "+(robotMax-robotCounter) +" Robots");
        messege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 50));
        messege.setFill(Color.DARKGREEN);
        game.getChildren().remove(messeges);
        messeges.getChildren().clear();
        messeges.getChildren().add(messege);
        game.getChildren().add(messeges);
        server.startGame();
        timeGame.start();  // difrent thred
                // choose a vertex to place a robot
        new Thread(new killTheTerrorists()).start();

        /**
         * Create a Mouse event hendelr for the manual Game Mode
         */
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                if(e.toString().contains("Text")&&(e.toString().contains("id"))) { // if the user preset on a vertex add there a robot
                   String s = e.toString();
                    int v = s.indexOf('[',13);
                    int beginKey= s.indexOf('=',45)+1;
                    int endKey= s.indexOf(',',beginKey);
                    int key = Integer.parseInt(s.substring(beginKey,endKey));
                    if(currHelicopter[0]!=-1){ // if the user want to move the robot
                        server.chooseNextEdge(currHelicopter[0],key);
                        System.out.println(currHelicopter[0]+"   "+key);
                        System.out.println(server.getRobots());
                        return;
                    }

                    else{
                        Text messege =  new Text(screenWidth/3, 50, "Game Has started");
                        messege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 50));
                        messege.setFill(Color.DARKGREEN);
                        game.getChildren().remove(messeges);
                        messeges.getChildren().clear();
                        messeges.getChildren().add(messege);
                        game.getChildren().add(messeges);
                        System.out.println("onUpdate");
                        return;
                    }

                }
               if(e.toString().contains("heli")) {
                   String s = e.toString();
                   int beginKey= s.indexOf('#')+1;
                   int endKey= s.indexOf('*',beginKey);
                   int key = Integer.parseInt(s.substring(beginKey,endKey));
                   currHelicopter[0] =key;
                }
            }
        };

        for (int i = 0; i < game.getChildren().size(); i++) {
            game.getChildren().get(i).setOnMouseClicked(eventHandler);
        }
    }

    private void automatic() {
        sgame.Automaticplay();
        timeGame.start();  // difrent thred
    }


    /**
     * A Thred What will Run in Background and Update the scene.
     */
    private  AnimationTimer timeGame = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if(now- nowFirst >70) {
                onUpdate();
                nowFirst=now;

            }
        }
    };

    /**
     * This Function Updates all The Helicopter's And The Robot's in the game
     * Acording TO There Place in the game.
     */
    private  void onUpdate() {

 if (!server.isRunning()){ timeGame.stop();
    server.stopGame();
    Text timeMessege = new Text(screenWidth/5, screenHeight/2,"Game Has Ended");
    timeMessege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 120));
    timeMessege.setFill(Color.RED);
    messeges.getChildren().clear();
    messeges.getChildren().add(timeMessege);
return;}
try {
    Text timeMessege = new Text(50, screenHeight*0.98,"Time Remaining "+ String.valueOf(server.timeToEnd()/1000));
    timeMessege.setFont(javafx.scene.text.Font.font("Verdana", FontWeight.BOLD, 32));
    timeMessege.setFill(Color.RED);
    messeges.getChildren().clear();
    messeges.getChildren().add(timeMessege);
        drawRobots();
        drawFruits();
}

catch (RuntimeException r ) {
System.out.println(r.getCause());

}


}


    /**
     * Function to play The game
     * @param args
     */
    @Override
    public void PlayGui(String[] args) {
        launch(args);
    }

    public static void main(String[] args) {




    }
}
