//Creates GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

//need to change the showGraph(), updateMoneyEarned() and updateTime() to work with actual stocks
//dont forget to format cash, stockValue, and netWorth
public class Screen extends JFrame implements ActionListener
{
    private static DecimalFormat two = new DecimalFormat ("0.00");
    //these are the buttons for stocks
    private JButton s1, s2, s3, s4, s5, buy, sell, newDay; 
    private Stock stockSelected;
    //Jpanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    //these are the stocks that the person can look at, initialized in getStockList
    private Stock stock1, stock2, stock3, stock4, stock5;
    private JLabel cash, stockValue, netWorth, time, graph, stocksOwned;
    private int day=0;
    private double c=0, sv=0, nw=0;
    
    private Player test = new Player();

    public Screen()
    {
        super("Screen");

        Container win=getContentPane();
        win.setLayout(new BorderLayout());


        p1.setLayout(new GridLayout(5,1));
        getStockList();
        
        p2.setLayout(new GridLayout(4,1));
        p3.setLayout(new GridLayout(1,5));
        
        //this is the setup for what player will see when they select a stock
        buy = new JButton("Buy");
        buy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        buy.addActionListener(this);
        
        sell=new JButton("Sell");
        sell.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        sell.addActionListener(this);
        
        stocksOwned = new JLabel ("");
        p2.add(stocksOwned);

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

        
        //Bottom of screen (cash, stockValue, netWorth, day and NewDay button)
        cash = new JLabel("Cash: "+ two.format(c));
        cash.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        stockValue = new JLabel("Stock Value: "+ two.format(sv));
        stockValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        netWorth = new JLabel("Net Worth: "+ two.format(nw));
        netWorth.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        time= new JLabel("                       " + day +" days");
        time.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        newDay=new JButton("New Day");
        newDay.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        p3.add(cash);
        p3.add(stockValue);
        p3.add(netWorth);
        p3.add(time);
        p3.add(newDay);
        
        
        //adding everythign to window and sets size
        graph = new JLabel(" ");
        
        win.add(p1, BorderLayout.WEST);
        win.add(p2, BorderLayout.EAST);
        win.add(p3, BorderLayout.SOUTH);
        win.add(graph, BorderLayout.CENTER);

        setVisible(true);
        setSize(1000,650);

    }

    public void actionPerformed (ActionEvent e)
    {
        //stock selected is the stock most recently clicked on to keep track of which stock we are viewing
        JButton buttonPressed = (JButton)e.getSource();

        if(buttonPressed==s1)
        {
            stockSelected=stock1;
            Graph.showGraph(stock1.values());
        }
        else if(buttonPressed==s2)
        {
            stockSelected=stock2;
            Graph.showGraph(stock2.values());
        }
        else if(buttonPressed==s3)
        {
            stockSelected=stock3;
            Graph.showGraph(stock3.values());
        }
        else if(buttonPressed==s4)
        {
            stockSelected=stock4;
            Graph.showGraph(stock4.values());
        }
        else if(buttonPressed==s5)
        {
            stockSelected=stock5;
            Graph.showGraph(stock5.values());
        }
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
    public void displayStock (JButton b)
    {
        //needs to be replaced with the graphs that match up with the stock
        graph.setText("graph");
        p2.add(buy);
        p2.add(sell);
        //System.out.println(stockSelected.getName());
        //stocksOwned.setText(test.getValue(stockSelected.getName()));
        //System.out.println(s);
        //stocksOwned.setText(" "+s+" ");
    }

    public void updateMoneyEarned()
    {
    }

    public void updateTime()
    {
        day++;
    }

    public static void main (String [] args)
    {
        Screen go = new Screen();
    }
}
