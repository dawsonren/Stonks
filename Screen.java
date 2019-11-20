//Creates GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Screen extends JFrame implements ActionListener
{
    private static DecimalFormat two = new DecimalFormat ("0.00");
    private final String spacer = "    ";
    
    //these are the buttons for stocks
    private JButton[] stockButtons = new JButton[5];
    private JButton newDay; 
    
    //m1 is overview, m2 is buy, m3 is sell, m4 is help
    private JMenuItem m1, m2, m3, m4;
    
    //stockSelected is for further information inside p2
    private Stock stockSelected;
    
    //JPanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    //p2 will contain individual information screens for the 
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    
    //these are the stocks that the person can look at, will change with time (hopefully)
    private Stock[] stocks = new Stock[5];
    private ArrayList<Stock> stocksOwned = new ArrayList<Stock>();
    
    //labels cash, stockvalue, time, and networth go on the bottom. Info controls what's said in p2.
    private JLabel cash, stockValue, netWorth, time, graph, info;
    
    private int day = 0;
    
    private double c = 100000, sv = 0, nw = c + sv;
    
    //private Player test = new Player();
    
    public Screen()
    {
        super("Screen");
        
        //set window

        Container win = getContentPane();
        win.setLayout(new BorderLayout());

        //initialize stocks
        getStockList();
        
        //set layouts for JPanel
        p1.setLayout(new GridLayout(5,1));
        
        
        p2.setLayout(new GridLayout(4,1));
        p3.setLayout(new GridLayout(1,5));
        
        //the information that's in p2
        info = new JLabel("");
        p2.add(info);

        //menu for what screen the player is looking at (overview, buy, sell, help)
        JMenuBar bar = new JMenuBar();
        JMenu menu= new JMenu("Menu");
        m1 = new JMenuItem("Overview");
        m1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        m1.addActionListener(this);
        m2 = new JMenuItem("Buy");
        m2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        m2.addActionListener(this);
        m3 = new JMenuItem("Sell");
        m3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        m3.addActionListener(this);
        m4 = new JMenuItem("Help");
        m4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        m4.addActionListener(this);
        
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
        newDay.addActionListener(this);
        
        p3.add(cash);
        p3.add(stockValue);
        p3.add(netWorth);
        p3.add(time);
        p3.add(newDay);
        
        
        //adding everything to window and sets size
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
        if (e.getSource() == m1) {
            
        } else if(e.getSource() == m2) {
            ArrayList<String> stockNames = new ArrayList<String>();
            for (Stock s : stocks) {
                stockNames.add(s.getName());
            }
            
            JTextField numShares = new JTextField(5);
            JComboBox chooseStock = new JComboBox(stockNames.toArray(new String[0]));
            
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Buy "));
            myPanel.add(numShares);
            myPanel.add(new JLabel(" shares of "));
            myPanel.add(chooseStock);
            
            int result = JOptionPane.showConfirmDialog(null, myPanel, "Buy", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                int confirm = JOptionPane.showConfirmDialog(null, "Confirm", "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    double cost = stocks[chooseStock.getSelectedIndex()].getPrice() * (Integer.parseInt(numShares.getText()));
                    System.out.println(stocks[chooseStock.getSelectedIndex()].getPrice());
                    System.out.println(Integer.parseInt(numShares.getText()));
                    sv += cost;
                    c -= cost;
                }
            }
            update();
        } else if (e.getSource() == m3) {
            
        } else if (e.getSource() == m4) {
            
        }

        
        //stock buttons, graphs
        if(e.getSource()==stockButtons[0])
        {
            stockSelected=stocks[0];
            Graph.showGraph(stocks[0].values());
        }
        else if(e.getSource()==stockButtons[1])
        {
            stockSelected=stocks[1];
            Graph.showGraph(stocks[1].values());
        }
        else if(e.getSource()==stockButtons[2])
        {
            stockSelected=stocks[2];
            Graph.showGraph(stocks[2].values());
        }
        else if(e.getSource()==stockButtons[3])
        {
            stockSelected=stocks[3];
            Graph.showGraph(stocks[3].values());
        }
        else if(e.getSource()==stockButtons[4])
        {
            stockSelected=stocks[4];
            Graph.showGraph(stocks[4].values());
        }
        
        //next day button
        
        if (e.getSource()==newDay) {
            updateTime();
        }
    }

    //should give the strings the name of the stock they will hold
    //then adds buttons to panel
    public void getStockList()
    {
        //these are just example names to be replaced later
        stocks[0] = new Stock("Apple");
        stocks[1] = new Stock("Microsoft");
        stocks[2] = new Stock("Samsung");
        stocks[3] = new Stock("Nintendo");
        stocks[4] = new Stock("PlayStation");
        setStockList();
    }

    //sets name to button, and adds button to p1
    public void setStockList()
    {
        stockButtons[0]=new JButton(spacer + stocks[0].getName() + spacer + "0.00%");
        stockButtons[0].setFont(new Font("Times New Roman", Font.PLAIN, 18));
        stockButtons[0].addActionListener(this);

        stockButtons[1]=new JButton(spacer + stocks[1].getName() + spacer + "0.00%");
        stockButtons[1].setFont(new Font("Times New Roman", Font.PLAIN, 18));
        stockButtons[1].addActionListener(this);

        stockButtons[2]=new JButton(spacer + stocks[2].getName() + spacer + "0.00%");
        stockButtons[2].setFont(new Font("Times New Roman", Font.PLAIN, 18));
        stockButtons[2].addActionListener(this);

        stockButtons[3]=new JButton(spacer + stocks[3].getName() + spacer + "0.00%");
        stockButtons[3].setFont(new Font("Times New Roman", Font.PLAIN, 18));
        stockButtons[3].addActionListener(this);

        stockButtons[4]=new JButton(spacer + stocks[4].getName() + spacer + "0.00%");
        stockButtons[4].setFont(new Font("Times New Roman", Font.PLAIN, 18));
        stockButtons[4].addActionListener(this);

        p1.add(stockButtons[0]);
        p1.add(stockButtons[1]);
        p1.add(stockButtons[2]);
        p1.add(stockButtons[3]);
        p1.add(stockButtons[4]);
    }

    public void updateMoneyEarned()
    {
        
    }

    public void updateTime()
    {
        for (int x = 0; x < stocks.length; x++) {
            double change = stocks[x].nextDay();
            if (change >= 0) {
                stockButtons[x].setForeground(new Color(0, 153, 0));
                stockButtons[x].setText(spacer + stocks[x].getName() + spacer + "+" + two.format(change) + "%");
            } else {
                stockButtons[x].setForeground(Color.RED);
                stockButtons[x].setText(spacer + stocks[x].getName() + spacer + two.format(change) + "%");
            }
            
            
        }
        day++;
        update();
    }
    
    public static void main (String [] args)
    {
        Screen go = new Screen();
    }
    
    public void update() {
        cash.setText("Cash: "+ two.format(c));
        stockValue.setText("Stock Value: "+ two.format(sv));
        netWorth.setText("Net Worth: "+ two.format(nw));
        time.setText("                       " + day +" days");
        
        
    }
}
