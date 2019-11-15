//this is for the gui
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//need to change the showGraph(), updateMoneyEarned() and updateTime() to work with actual stocks

public class Screen extends JFrame implements ActionListener
{
    //these are the buttons for stocks
    private JButton s1,s2,s3,s4,s5;
    //Jpanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    //these are the stocks that the person can look at, initialized in getStockList
    private Stock stock1, stock2, stock3, stock4, stock5;
    private JLabel moneyEarned, time, graph;
    int day=0, earnings=0;

    public Screen()
    {
        super("Screen");

        Container win=getContentPane();
        win.setLayout(new BorderLayout());


        p1.setLayout(new GridLayout(5,1));
        getStockList();

        p3.setLayout(new GridLayout(1,2));

        graph = new JLabel(" ");
        p2.add(graph);

        //menu for what screen the player is looking at (overview, buy, sell, help)
        JMenuBar bar = new JMenuBar();
        JMenu menu= new JMenu("Menu");
        JMenuItem m1 = new JMenuItem("Overview");
        m1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        JMenuItem m2 = new JMenuItem("Buy");
        m2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        JMenuItem m3 = new JMenuItem("Sell");
        m3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        JMenuItem m4 = new JMenuItem("Help");
        m4.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //JMenu for swtching what you are seeing
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        menu.add(m4);
        bar.add(menu);
        setJMenuBar(bar);
        menu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        win.add(bar, BorderLayout.NORTH);

        //Total earnings while in the game and the time that has passed
        moneyEarned = new JLabel("Total Earnings: "+ earnings);
        moneyEarned.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        time= new JLabel("                       " + day +" days");
        time.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        p3.add(moneyEarned);
        p3.add(time);

        win.add(p1, BorderLayout.WEST);
        win.add(p2, BorderLayout.EAST);
        win.add(p3, BorderLayout.SOUTH);

        setVisible(true);
        setSize(1000,650);

    }

    public void actionPerformed (ActionEvent e)
    {
        JButton buttonPressed = (JButton)e.getSource();

        if(buttonPressed==s1)
            showGraph(s1);
        else if(buttonPressed==s2)
            showGraph(s2);
        else if(buttonPressed==s3)
            showGraph(s3);
        else if(buttonPressed==s4)
            showGraph(s4);
        else if(buttonPressed==s5)
            showGraph(s5);
    }

    //should give the strings the name of the stock they will hold
    //then adds buttons to panel
    public void getStockList()
    {
        //these are just example names to be replaced later
        stock1 = new Stock("Apple");
        stock2 = new Stock("Microsoft");
        stock3 = new Stock("Samsung");
        stock4 = new Stock("Nintendo");
        stock5 = new Stock("PlayStation");
        setStockList();
    }

    //sets name to button, and adds button to p1
    public void setStockList()
    {
        s1=new JButton("      " + stock1.getName() + "      ");
        s1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        s1.addActionListener(this);

        s2=new JButton("      " + stock2.getName() + "      ");
        s2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        s2.addActionListener(this);

        s3=new JButton("      " + stock3.getName() + "      ");
        s3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        s3.addActionListener(this);

        s4=new JButton("      " + stock4.getName() + "      ");
        s4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        s4.addActionListener(this);

        s5=new JButton("      " + stock5.getName() + "      ");
        s5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        s5.addActionListener(this);

        p1.add(s1);
        p1.add(s2);
        p1.add(s3);
        p1.add(s4);
        p1.add(s5);
    }

    //takes the button given by action performed and displays the corresponding graph
    public void showGraph(JButton b)
    {
        //needs to be replaced with the graphs that match up with the stock
        graph.setText("graph");
    }

    public void updateMoneyEarned()
    {
    }

    public void updateTime()
    {
    }

    public static void main (String [] args)
    {
        Screen go = new Screen();
    }
}
