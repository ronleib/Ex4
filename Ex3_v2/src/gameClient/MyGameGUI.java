package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class MyGameGUI {
    private game_service[] Game ;


    public void MyGameGUI(){
    for(int x=0;x<24;x++)
        Game[x]= Game_Server.getServer(x);
}
    public  void start(int num){
        String temp=this.Game[num].getGraph();
        Graph_Algo gameDGrap=new Graph_Algo();
        gameDGrap.initJson(temp);
        try {
            JSONObject line = new JSONObject(Game[num].toString());
            JSONObject Server = line.getJSONObject("GameServer");
            int robot = Server.getInt("robots");
            System.out.println(Game[num].toString());
            System.out.println(gameDGrap.toString());
            // the list of fruits should be considered in your solution
            Iterator<String> f_iter = Game[num].getFruits().iterator();
            while(f_iter.hasNext()) {System.out.println(f_iter.next());}
            /// sort fruits
            /// TSP MOD ROBOT
            //
            int src_node = 0;  // arbitrary node, you should start at one of the fruits
            for(int a = 0;a<robot;a++) {
        //        game.addRobot(src_node+a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

