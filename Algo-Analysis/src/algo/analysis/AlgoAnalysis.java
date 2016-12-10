/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo.analysis;

import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 *
 * @author Ramnarayan
 */
public class AlgoAnalysis {

    /**
     * @param args the command line arguments
     */
    static int M,N;
    static boolean done;
    static JFrame jf;
    static JButton source;
    static JButton goal;
    static Color curr_color;
    public static void main(String[] args) {
        jf = new JFrame("Analyze");
        jf.setSize(800,800);
        jf.setResizable(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        source = null;
        goal = null;
        //initialize the function, get the m and n value
        init();
        
        //show instruction until not next
        instructions();
        
        //show mxn grid
        map_making();
        
    }
    
    
    static void init(){
        JPanel jp;
        JLabel jl;
        JButton jb;
        JTextField m;
        JTextField n;
        
        //define a frame
        jp = new JPanel();
        jl = new JLabel("Select Grid Size: mxn");
        m = new JTextField();
        n = new JTextField();
        jb = new JButton("Next");
        jb.addActionListener(new InitNext());
        
        m.setToolTipText("Enter m: ");
        m.setColumns(10);

        n.setToolTipText("Enter n: ");
        n.setColumns(10);
        jb.setSize(100,100);
        
        jp.add(jl, BorderLayout.CENTER);
        jp.add(m, BorderLayout.WEST);
        jp.add(n, BorderLayout.EAST);
        jp.add(jb, BorderLayout.SOUTH);
                 
        jf.add(jp);
        jf.setVisible(true);
        done = false;
        while(!done){
            System.out.println("waiting");
        }
        M =  Integer.parseInt(m.getText());
        N = Integer.parseInt(n.getText());
        jf.remove(jp);
    }
    
    static void instructions(){
       
        JPanel jp;
        JLabel jl;
        JButton jb;
      
        //define a frame
        jp = new JPanel();
        jl = new JLabel("Instructions: Please \n Read");
        jb = new JButton("Next");
        jb.addActionListener(new InitNext());
        
        jb.setSize(100,100);
        jp.add(jl, BorderLayout.CENTER);
        jp.add(jb, BorderLayout.SOUTH);
                 
        jf.add(jp);
        jf.setVisible(true);
        
        done = false;
        while(!done){
            System.out.println("waiting");
        }
        jf.remove(jp);
    }
    
    static class InitNext implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            done = true;
        }
    }
    
    static void map_making(){
        
        //setting up current GUI
        JButton jb;
        JPanel jp = new JPanel();
        JPanel jp2 = new JPanel();
        jp.setLayout(new GridLayout(M,N));
        jp2.setLayout(new GridLayout(1,4));
        
        //height, width of indivdual button, adding buttons
        int x=1;
        int y=1;
        for(int i=0; i<M*N; i++){
            jb = new JButton( );
            jb.setName(Integer.toString(x)+","+ Integer.toString(y));
            jb.addActionListener(new color());
            jp.add(jb, BorderLayout.PAGE_START);
            
            if(x+1<=N)++x;
            else{++y; x=1;}
        }
        
        jb = new JButton("Set Source");
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);
        
        jb = new JButton("Set Goal");
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);
        
        jb = new JButton("Set Obstacles");
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);

        jb = new JButton("Play");
        jb.addActionListener(new InitNext());
        jp2.add(jb, BorderLayout.CENTER);
        
        
        jf.add(jp, BorderLayout.CENTER);
        jf.add(jp2, BorderLayout.PAGE_END);
        jf.setVisible(true);
        
        //setting default edit
        curr_color = Color.RED;
        
        done = false;
        //wait for user to go next
        while(!done){
            System.out.println("waiting");
        }
        jf.remove(jp2);
        jf.setVisible(true);
        
    }
    
    static class color implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton jb = (JButton) ae.getSource();
            if(curr_color == Color.red){
                if (goal != null)
                    goal.setBackground(null);
                goal = jb;
            }
            
            if(curr_color == Color.green){
                if (source != null)
                    source.setBackground(null);
                source = jb;
            }
            jb.setBackground(curr_color);
            
        }
        
    }
    
    static class mode implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton jb = (JButton) ae.getSource();
            if(jb.getText() == "Set Source"){
                curr_color = Color.red;
            }
            
            else if(jb.getText() == "Set Goal"){
                curr_color = Color.green;
            }
            else if(jb.getText() == "Set Obstacles"){
                curr_color = Color.black;
            }
            
            
        }
        
    }
}
