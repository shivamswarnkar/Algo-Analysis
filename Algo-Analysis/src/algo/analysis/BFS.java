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
    static JSlider speed;
    BFS(ArrayList<Node> problem, Node the_goal, Node the_source, JSlider js){
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
        Queue<Node> visit = new LinkedList<Node>();
        Node curr;
        ArrayList<Node> childs = new ArrayList<>();
        ArrayList<Node> visited = new ArrayList<>();
        
        source.distance=0;
        visit.add(source);
        
        while(!visit.isEmpty()){
            curr = visit.poll();
            if(curr == goal)return curr;
            curr.setColor(Color.blue); //current node
            childs = curr.neighbor(map);
            for(Node n : childs){
                if(n.distance == -1 || n.distance > curr.distance+1){
                    n.parent = curr;
                    n.distance = curr.distance + 1;
                }
                if(!visit.contains(n) && !visited.contains(n)){
                    visit.add(n);
                    n.setColor(Color.MAGENTA);
                     //pause for 1 second, just for visuals
                     try {
                     TimeUnit.MILLISECONDS.sleep(speed.getValue());
                    } catch (InterruptedException ex) {
                    Logger.getLogger(BFS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
               
            }
            visited.add(curr);
            curr.setColor(Color.ORANGE);
            
            
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
   
    
}
