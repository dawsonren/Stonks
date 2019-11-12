//this is for the gui
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Screen extends JFrame implements ActionListener
{
    //these are the buttons for stocks
    private JButton s1,s2,s3,s4,s5;
    //menu for what screen the player is looking at (overview, buy, sell, help)
    private JMenu m1;
    //Jpanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new Jpanel();
    //these are the stocks that the person can look at, initialized in getStockList
    private String stock1, stock2, stock3, stock4, stock5;

    public Screen()
    {
        super("Screen");

        Container win=getContentPane();
        win.setLayout(new BorderLayout());

        p1.setLayout(new GridLayout(4,1));
        getStockList();

        
    }

    //should give the strings the name of the stock they will hold, i just did this basic
    //i dont know how we want to pass the stocks to this
    //then adds buttons to panel
    public static void getStockList()
    {
        stock1 = "stock1";
        stock2 = "stock2";
        stock3 = "stock3";
        stock4 = "stock4";
        stock5 = "stock5";
        setStockList();
    }

    //sets name to button, and adds button to p1
    public static void setStockList()
    {
        s1=new JButton("stock1");
        s2=new JButton("stock2");
        s3=new JButton("stock3");
        s4=new JButton("stock4");
        s5=new JButton("stock5");

        p1.add(s1);
        p1.add(s2);
        p1.add(s3);
        p1.add(s4);
        p1.add(s5);
    }

}
