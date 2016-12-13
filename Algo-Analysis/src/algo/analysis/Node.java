/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.analysis;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 *
 * @author Ramnarayan
 */
public class Node {
    int x; //x cordinate
    int y; //y cordinate
    JButton jb; //button representing this node
    int distance; //distance from the head
    Node parent; //parent node
    Node child; //child node
    boolean digonal;
    
    
    //constructor
    public Node(JButton button, int x_cordinate, int y_cordinate, boolean digonalMov){
        x = x_cordinate; //x cordinate
        y = y_cordinate; //y cordinate
        jb = button;
        digonal = digonalMov;
        
        parent = null;
        child = null;
        distance=-1;
        
    }
    
    //simple distance 
    public double distance(Node aNode){
        return Math.sqrt(Math.pow(x-aNode.x, 2)+Math.pow(y-aNode.y,2));
    } 
    
    public ArrayList<Node> neighbor(ArrayList<Node> map){
        ArrayList<Node> nodes = new ArrayList<>();
        for(Node curr : map){
            if(curr.getColor() != Color.black && isNeighbor(curr.x, curr.y)){
                nodes.add(curr);
            }
        }
        return nodes;
    }
    
    public Color getColor(){
        return jb.getBackground();
    }
    public void setColor(Color newColor){
        jb.setBackground(newColor);
    }
    private boolean isNeighbor(int a, int b){
        //up, down, side neighbors 
        if((a==x && (b==y-1 || b==y+1)) || (b==y && (a==x-1 || a==x+1)))return true;
        //digonal neighbors
        if(digonal && ((a==x-1 && b==y-1) || (a==x+1 && b==y+1) || (a==x+1 && b==y-1)|| (a==x-1 && b==y+1)))return true;
        return false;
    }
    
}
