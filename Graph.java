import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Graph extends JFrame{
    //graph has name and trend and points
    private ColorPanel canvas;
    private int width, height;
    private ArrayList<E> values;
    public Graph(int w, int h, ArrayList<E> v){
        Container win = getContentPane();
        canvas = new ColorPanel(Color.WHITE);
        win.add(canvas);
        setSize(300,300);
        setVisible(true);

    }
    public class ColorPanel extends JPanel{
        public ColorPanel(Color c){
            setBackground(c);
        }
        public void paintComponent(Graphics g){
            g.drawLine(0,0,20,20);
            g.setColor(Color.BLACK);
        }

    }
    public static void main(String [] args){
        Graph app = new Graph();
    }
}
