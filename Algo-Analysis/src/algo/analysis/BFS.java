/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.analysis;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ramnarayan
 */
public class BFS extends Thread {
    ArrayList<Node> map;
    ArrayList<Node> path;
    Node goal;
    Node source;
    BFS(ArrayList<Node> problem, Node the_goal, Node the_source){
        map=problem;
        goal=the_goal;
        source=the_source;
    }
    
    public void run(){
        Node curr = find_goal();
        source.setColor(Color.green);
        goal.setColor(Color.red);
        if(curr==null)return;
        
        curr = curr.parent;
        while(curr.distance !=0){
            curr.setColor(Color.CYAN);
            curr = curr.parent;
        }
    }
    private Node find_goal(){
        //list of seen 
        Queue<Node> visit = new LinkedList<Node>();
        Node curr;
        ArrayList<Node> childs = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        
        source.distance=0;
        visit.add(source);
        
        while(!visit.isEmpty()){
            curr = visit.poll();
            if(curr == goal)return curr;
            curr.setColor(Color.ORANGE); //current node
            childs = curr.neighbor(map);
            for(Node n : childs){
                if(n.distance != -1 || n.distance > curr.distance+1){
                    n.parent = curr;
                    n.distance = curr.distance + 1;
                }
                if(!visit.contains(n) && !visited.contains(n)){
                    visit.add(n);
                    n.setColor(Color.MAGENTA);
                }
                
                //pause for 1 second, just for visuals
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BFS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
        }
        return null; //didn't find a solution
    }
    
}
