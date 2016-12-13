/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.analysis;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ramnarayan
 */
public class AStar extends Thread {
    ArrayList<Node> map;
    ArrayList<Node> path;
    Node goal;
    Node source;
    static JSlider speed;
    AStar(ArrayList<Node> problem, Node the_goal, Node the_source, JSlider js){
        map=problem;
        goal=the_goal;
        source=the_source;
        speed = js;
    }
    
    public void run(){
        Node curr = find_goal();
        source.setColor(Color.green);
        goal.setColor(Color.red);
        if(curr==null){
            reset(false);
            return;
        }
        
        curr = curr.parent;
        while(curr.distance !=0){
            curr.setColor(Color.CYAN);
            curr = curr.parent;
        }
        reset(true);
    }
    private Node find_goal(){
        //list of seen 
        Node curr;
        ArrayList<Node> childs = new ArrayList<>();
        PriorityQueue<Node> open = new PriorityQueue<Node>(11, new comparableNodes());
        ArrayList<Node> closed = new ArrayList<>();
        
        source.distance=0;
        open.add(source);
        
        while(!open.isEmpty()){
            curr = open.remove();
            if(curr == goal)return curr;
            curr.setColor(Color.blue); //current node
            childs = curr.neighbor(map);
            for(Node n : childs){
                if((!open.contains(n)) && (!closed.contains(n))){
                    open.add(n);
                    n.parent = curr;
                    n.distance = curr.distance + 1;
                    n.setColor(Color.MAGENTA);
                }
                else if(n.distance > curr.distance + 1){
                    n.parent = curr;
                    n.distance = curr.distance + 1;
                }
                
                try {
                     TimeUnit.MILLISECONDS.sleep(speed.getValue());
                    } catch (InterruptedException ex) {
                    Logger.getLogger(BFS.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            curr.setColor(Color.ORANGE);
            closed.add(curr);
       
                
               
       }
       return null; //didn't find a solution
    }
    
    private void reset(boolean isPath){
        Color myColor = (isPath)?Color.WHITE:Color.GRAY;
        for(Node x : map){
            if(x.getColor()!= Color.red && x.getColor()!= Color.green && x.getColor()!= Color.black &&x.getColor()!= Color.CYAN){
                x.setColor(myColor);
            } 
        }
    }
    
    
    public class comparableNodes implements Comparator<Node>{
       @Override
        public int compare(Node a, Node b) {
        return (int) (a.distance(AlgoAnalysis.goal_node) - b.distance(AlgoAnalysis.goal_node));
    }

    }

    }
   
    

