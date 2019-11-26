//TODO: Handle buy/sell logic

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
    
    private final int totalStocks = 5;
    private final String[] stockNames = {"Apple", "Microsoft", "Samsung", "Nintendo", "PlayStation"};
    public final double startingCash = 100000.0;
    
    //overall window container
    Container win;
    
    //these are the buttons for stocks
    private JButton[] stockButtons = new JButton[totalStocks];
    private JButton newDay;
    
    //m1 is overview, m2 is buy, m3 is sell, m4 is help
    private JMenuItem m1, m2, m3, m4;
    
    //JPanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    //p2 will contain individual information screens for the number of stocks
    JPanel[] p2 = new JPanel[totalStocks];
    JPanel p3 = new JPanel();
    
    //stockSelected is for further information inside p2
    private JPanel panelSelected;
    
    //stocks is used for accessing stock information in general
    private Stock[] stocks = new Stock[totalStocks];
    
    //portfolio is used for managing the portfolio
    private Portfolio portfolio = new Portfolio(totalStocks, startingCash);
    
    //labels cash, stockvalue, time, and networth go on the bottom. Info controls what's said in p2.
    private JLabel cash, stockValue, netWorth, time, graph, info;
    
    private int day = 0;
    
    //private Player test = new Player();
    
    public Screen()
    {
        super("Screen");
        
        //set window

        win = getContentPane();
        win.setLayout(new BorderLayout());

        //initialize stocks
        getStockList();
        
        //set layouts for JPanel
        p1.setLayout(new GridLayout(5,1));
        
        p3.setLayout(new GridLayout(1,5));

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
        cash = new JLabel("Cash: "+ two.format(portfolio.getCash()));
        cash.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        stockValue = new JLabel("Stock Value: "+ two.format(portfolio.getValue()));
        stockValue.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        
        netWorth = new JLabel("Net Worth: "+ two.format(portfolio.getNetWorth()));
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
        
        win.add(p1, BorderLayout.WEST);
        win.add(p3, BorderLayout.SOUTH);
        
        //default page is apple
        panelSelected = p2[0];

        setVisible(true);
        setSize(1000,650);

    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == m1) {
            
        } else if(e.getSource() == m2) {
            JTextField numShares = new JTextField(5);
            JComboBox chooseStock = new JComboBox(stockNames);
            JLabel errorMessage = new JLabel();
            
            boolean again = false;
            
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Buy "));
            myPanel.add(numShares);
            myPanel.add(new JLabel(" shares of "));
            myPanel.add(chooseStock);
            myPanel.add(errorMessage);
            
            do {
                int result = JOptionPane.showConfirmDialog(null, myPanel, "Buy", JOptionPane.OK_CANCEL_OPTION);
                
                int num = Integer.parseInt(numShares.getText());
                
                if (num <= 0) {
                    errorMessage.setText("Enter a number of shares above 0.");
                    again = true;
                } else {
                    again = false;
                }
                
                if (result == JOptionPane.OK_OPTION) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Confirm", "Are you sure?", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        //which stock selected, how many?
                        //files the information into the portfolio
                        int stockIndex = chooseStock.getSelectedIndex();
                        Stock selected = stocks[stockIndex];
                        
                        double cost = selected.getPrice(day) * num;
                            
                        //adds stock to portfolio
                        portfolio.addStock(selected, num);
                    }
                }
            } while (again);
            //updates the statistics on the bottom
            update();
        } else if (e.getSource() == m3) {
            JTextField numShares = new JTextField(5);
            //only gets the stocks available in the portfolio
            JComboBox chooseStock = new JComboBox(portfolio.getStockNames());
            
            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Sell "));
            myPanel.add(numShares);
            myPanel.add(new JLabel(" shares of "));
            myPanel.add(chooseStock);
            
            boolean again = false;
            
            //do it again if you are trying to sell more than you have
            do {
                int result = JOptionPane.showConfirmDialog(null, myPanel, "Sell", JOptionPane.OK_CANCEL_OPTION);
                
                if (result == JOptionPane.OK_OPTION) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Confirm", "Are you sure?", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        //which stock selected, how many?
                        //files the information into the portfolio
                        int stockIndex = chooseStock.getSelectedIndex();
                        Stock selected = stocks[stockIndex];
                        int num = Integer.parseInt(numShares.getText());
                        
                        double cost = selected.getPrice(day) * num;
                        
                        //stockIndex is the same as the stocks, i.e. 0 = Apple, 1 = etc...
                        again = portfolio.removeStock(selected, num);
                    }
                }
            } while (again);
            //updates the statistics on the bottom
            update();
        } else if (e.getSource() == m4) {
            
        }
        
        //stock buttons, graphs
        for (int x = 0; x < stockButtons.length; x++) {
            if (e.getSource() == stockButtons[x]) {
                panelSelected.setVisible(false);
                win.add(p2[x], BorderLayout.EAST);
                p2[x].setVisible(true);
                panelSelected = p2[x];
                
            }
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
        for (int x = 0; x < stocks.length; x++) {
            stocks[x] = new Stock(stockNames[x]);
        }
        setStockList();
        setStockInfo();
    }

    //sets name to button, and adds button to p1
    public void setStockList()
    {
        for (int x = 0; x < stockButtons.length; x++) {
            stockButtons[x] = new JButton(spacer + stocks[x].getName() + spacer + "0.00%");
            stockButtons[x].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            stockButtons[x].addActionListener(this);
            p1.add(stockButtons[x]);
        }
    }
    
    public void setStockInfo() {
        for (int x = 0; x < p2.length; x++) {
            p2[x] = new StockInfoContainer(stocks[x]);
        }
    }

    public void updateTime()
    {
        //controls button colors
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
    
    public void update() {
        cash.setText("Cash: "+ two.format(portfolio.getCash()));
        stockValue.setText("Stock Value: "+ two.format(portfolio.getValue()));
        netWorth.setText("Net Worth: "+ two.format(portfolio.getNetWorth()));
        time.setText("                       " + day +" days");
        setStockInfo();
    }
}
