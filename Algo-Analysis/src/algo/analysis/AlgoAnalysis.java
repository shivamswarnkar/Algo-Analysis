/*
Algorithm Analysis is a program which lets you visually analyze algorithms.
Just select the grid size for a map of mxn, and then make your own map, and
analyze how does algorithms perform.
 */
package algo.analysis;

import java.util.*;
import java.io.*;
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
    static JSlider js;
    public static Node source_node;
    public static Node goal_node;
    static Color curr_color;
    static ArrayList<Node> map;
    static ArrayList<JButton> modes;
    static boolean digonalMov;
    
    static String algo;
    
    public static void main(String[] args) {
        jf = new JFrame("Analyze");
        jf.setSize(800,800);
        jf.setResizable(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        map = new ArrayList<Node>();
        source = null;
        goal = null;
        //initialize the function, get the m and n value
        init();
        
        //show instruction until not next
        instructions();
        
        //show mxn grid
        map_making();
        
        
        //solve the map
        if(algo=="BFS"){
         new BFS(map, goal_node, source_node, js).start();   
        }
        else if(algo=="A*"){
            new AStar(map, goal_node, source_node, js).start();
        }
        else if(algo=="DFS"){
            new DFS(map, goal_node, source_node, js).start();
        }
        else if(algo=="Hill Climbing (modified)"){
            new HillClimbing(map, goal_node, source_node, js).start();
        }
        
        
    }
    
    
    static void init(){
        JPanel jp;
        JLabel jl;
        JButton jb;
        JTextField m;
        JTextField n;
        
        //define a frame
        jp = new JPanel();
        jl = new JLabel("Select Grid Size (map size) : mxn");
        m = new JTextField();
        n = new JTextField();
        jb = new JButton("Next");
        jb.setOpaque(true);
        jb.addActionListener(new InitNext());
        
        m.setToolTipText("Enter m: ");
        m.setColumns(10);

        n.setToolTipText("Enter n: ");
        n.setColumns(10);
        jb.setSize(100,100);
        
        Checkbox cb;
        JLabel jl2;
        jl2 = new JLabel("Allow digonal movement");
        cb = new Checkbox();
        String lst[] = {"BFS", "DFS", "A*", "Hill Climbing (modified)"};
        JComboBox jcb = new JComboBox(lst);
        JLabel jlJcb = new JLabel("Select Algorithm to visualize: ");
        jp.add(jl);
        jp.add(m);
        jp.add(n);
        jp.add(jlJcb);
        jp.add(jcb);
        jp.add(jl2);
        jp.add(cb);         
        jp.add(jb);
        
        jf.add(jp);
        //to set next button defult to work with enter
        jf.getRootPane().setDefaultButton(jb);
        
        jf.setVisible(true);
        done = false;
        while(!done){
            System.out.println("waiting");
        }
        digonalMov = cb.getState();
        
        M =  Integer.parseInt(m.getText());
        N = Integer.parseInt(n.getText());
        algo = (String) jcb.getSelectedItem();
        jf.remove(jp);
    }
    
    static void instructions(){
       
        JPanel jp;
        JLabel jl;
        JButton jb;
        
        
        //define a frame
        jp = new JPanel();
        jl = new JLabel("Instructions: Please \n Read");
        JTextArea j2 = new JTextArea("Instructions: Please \n Read");
        String inst="In next page, you will create a map of size "+Integer.toString(M)+"X"+Integer.toString(N)+"\n";
        inst += "At the bottom of the window, you can select the edit mode.\n Select Goal to set Goal, Source to set starting point.\n";
        inst += "And you can click again to delete the colors. \nOnce you're done, you can procceed to analysis window.\n Where at the bottom,";
        inst += "you can find a control scroll which \ncan be used to control the speed of map exploration\n";
        inst += "Color codes:\n Red: Goal\nGreen: Source\nBlack: Obstacles\nMagenta:seen but yet to visit\n";
        inst +="Orange: visited\nBlue: Current Node\nCyan:Best Path\nDark Grey: No Solution\n";
        j2.setText(inst);
        jb = new JButton("Next");
        jb.setOpaque(true);
        jb.addActionListener(new InitNext());
        
        jb.setSize(100,100);
        jp.add(jl, BorderLayout.PAGE_START);
        jp.add(j2, BorderLayout.CENTER);
        jp.add(jb, BorderLayout.SOUTH);
        jf.add(jp, BorderLayout.CENTER);
        
        //to set next button defult to work with enter
        jf.getRootPane().setDefaultButton(jb);
        jf.setVisible(true);
        
        done = false;
        while(!done){
            System.out.println("waiting");
        }
        jf.remove(jp);
    }
       
    static void map_making(){
        
        //setting up current GUI
        JButton jb;
        JPanel jp = new JPanel();
        JPanel jp2 = new JPanel();
        jp.setLayout(new GridLayout(M,N));
        jp2.setLayout(new GridLayout(1,4));
        modes = new ArrayList<JButton>();
        //height, width of indivdual button, adding buttons
        int x=1;
        int y=1;
        for(int i=0; i<M*N; i++){
            jb = new JButton( );
            jb.setOpaque(true);
            jb.addActionListener(new color());
            jp.add(jb, BorderLayout.PAGE_START);
            
            //add node to map
            map.add(new Node(jb, x,y, digonalMov));
            
            if(x+1<=N)++x;
            else{++y; x=1;}
        }
        
        jb = new JButton("Set Source");
        jb.setOpaque(true);
        jb.setBackground(Color.orange);
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);
        modes.add(jb);
        
        jb = new JButton("Set Goal");
        jb.setOpaque(true);
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);
        modes.add(jb);
        
        jb = new JButton("Set Obstacles");
        jb.setOpaque(true);
        jb.addActionListener(new mode());
        jp2.add(jb, BorderLayout.CENTER);
        modes.add(jb);
        
        jb = new JButton("Play");
        jb.setOpaque(true);
        jb.addActionListener(new InitNext());
        jp2.add(jb, BorderLayout.CENTER);
        
        
        jf.add(jp, BorderLayout.CENTER);
        jf.add(jp2, BorderLayout.PAGE_END);
        jf.setVisible(true);
        
        //setting default edit
        curr_color = Color.green;
        
        done = false;
        //wait for user to go next
        while(!done){
            System.out.println("waiting");
        }
        for(Node curr : map){
            curr.jb.setEnabled(false);
            if(curr.jb==source)source_node=curr;
            if(curr.jb==goal)goal_node=curr;
        }
        //jf.remove(jp2);
        js = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);
        JLabel jsJl = new JLabel("Control Speed");
        jp2.removeAll();
        jp2.add(jsJl, BorderLayout.PAGE_START);
        jp2.add(js, BorderLayout.PAGE_END);
        jf.setVisible(true);
        
    }
    
    //marks done next, indicates to go to next frame
    static class InitNext implements ActionListener{
       @Override
       public void actionPerformed(ActionEvent ae){
           JButton jb = (JButton) ae.getSource();
           if(jb.getText()=="Play"){
               if(goal!=null && source != null){
               done=true;
               }
           }
           else{
           done = true;
           }
       }
    }
 
    //changes color of nodes (buttons)
    static class color implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton jb = (JButton) ae.getSource();
            
            if(curr_color == jb.getBackground()){
                jb.setBackground(null);
                return;
            }
            if(source==jb){source=null;}
            if(goal==jb){goal=null;}
            
            
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
    
    //changes edit mode
    static class mode implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton jb = (JButton) ae.getSource();
            jb.setBackground(Color.ORANGE);
            if(jb.getText() == "Set Source"){
                curr_color = Color.green;
            }
            
            else if(jb.getText() == "Set Goal"){
                curr_color = Color.red;
            }
            else if(jb.getText() == "Set Obstacles"){
                curr_color = Color.black;
            }
            for(JButton x : modes){
                if(x != jb)x.setBackground(null);
            }
            
            
        }
        
    }
}
