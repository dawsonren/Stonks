import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Screen extends JFrame implements ActionListener
{
    private static DecimalFormat two = new DecimalFormat ("0.00");
    private final String SPACER = "    ";
    private final Font SMALL_FONT = new Font("Times New Roman", Font.PLAIN, 18);
    private final Font BIG_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    
    private final int totalStocks = 5;
    public final String[] stockNames = {"Apple", "Microsoft", "Samsung", "Nintendo", "PlayStation"};
    public final double startingCash = 100000.0;
    
    //overall window container
    Container win;
    
    //these are the buttons for stocks
    private JButton[] stockButtons = new JButton[totalStocks];
    private JButton newDay;
    
    //m1 is overview, m2 is buy/sell, m3 is help
    private JMenuItem m1, m2, m3;
    
    //JPanels (p1 is the button panel, p2 is the selected stock screen, and p3 is for total earnings, time, ect.)
    JPanel p1 = new JPanel();
    //p2 will contain individual information screens for the number of stocks
    JPanel[] p2 = new JPanel[totalStocks];
    JPanel p3 = new JPanel();
    
    //infoScreen is for further information inside p2
    private JPanel infoScreen;
    private int stockIndexSelected;
    
    //stocks is used for accessing stock information in general
    public Stock[] stocks = new Stock[totalStocks];
    
    //portfolio is used for managing the portfolio
    public Portfolio portfolio = new Portfolio(totalStocks, startingCash);
    
    //labels cash, stockvalue, time, and networth go on the bottom. Info controls what's said in p2.
    private JLabel cash, stockValue, netWorth, time, graph, info;
    
    public int day = 0;
    
    public Screen()
    {
        super("Screen");
        
        //set window

        win = getContentPane();
        win.setLayout(new BorderLayout());

        //set layouts for buttons and initialize stocks and buttons in p1
        p1.setLayout(new GridLayout(5,1));
        getStockList();
        
        //set panels for p2
        updateStockInfo();
        
        //default page is home screen
        infoScreen = homeScreen();
        stockIndexSelected = 0;
        
        //set layout for bottom bar
        p3.setLayout(new GridLayout(1,5));
        
        //Bottom of screen (cash, stockValue, netWorth, day and NewDay button)
        cash = new JLabel(SPACER + "Cash: "+ two.format(portfolio.getCash()));
        stockValue = new JLabel("Stock Value: "+ two.format(portfolio.getValue()));
        netWorth = new JLabel("Net Worth: "+ two.format(portfolio.getNetWorth()));
        time= new JLabel(SPACER + SPACER + SPACER + day +" days");
        newDay=new JButton("New Day");
        
        //menu for what screen the player is looking at (overview, buy, sell, help)
        JMenuBar bar = new JMenuBar();
        JMenu menu= new JMenu("Menu");
        m1 = new JMenuItem("Overview");
        m1.setFont(SMALL_FONT);
        m1.addActionListener(this);
        m2 = new JMenuItem("Buy and Sell");
        m2.setFont(SMALL_FONT);
        m2.addActionListener(this);
        m3 = new JMenuItem("Info");
        m3.setFont(SMALL_FONT);
        m3.addActionListener(this);

        //JMenu for swtching what you are seeing
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        bar.add(menu);
        setJMenuBar(bar);
        menu.setFont(BIG_FONT);
        
        
        //set Fonts for bottom of screen
        cash.setFont(BIG_FONT);
        stockValue.setFont(BIG_FONT);
        netWorth.setFont(BIG_FONT);
        time.setFont(BIG_FONT);
        newDay.setFont(BIG_FONT);
        newDay.addActionListener(this);
        
        //create bottom bar
        p3.add(cash);
        p3.add(stockValue);
        p3.add(netWorth);
        p3.add(time);
        p3.add(newDay);
        
        //add stock buttons, bottom bar, menu
        win.add(bar, BorderLayout.NORTH);
        win.add(p1, BorderLayout.WEST);
        win.add(p3, BorderLayout.SOUTH);

        setVisible(true);
        setSize(1440,800);
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource() == m1) {
            
        } else if(e.getSource() == m2) {
            BuySellFrame frame = new BuySellFrame();
        } else if (e.getSource() == m3) {
            
        }
        
        //changes out the current viewable p2, or stock information when press
        //the stock's button
        for (int x = 0; x < totalStocks; x++) {
            if (e.getSource() == stockButtons[x]) {
                infoScreen.setVisible(false);
                win.add(p2[x], BorderLayout.EAST);
                p2[x].setVisible(true);
                infoScreen = p2[x];
                stockIndexSelected = 0;
            }
        }
        
        //next day button
        if (e.getSource() == newDay) {
            day++;
            //update buttons must come first because it updates stock information
            updateButtons();
            updateBottomBar();
            updateStockInfo();
            
            JPanel temp = homeScreen();
            infoScreen.setVisible(false);
            win.add(temp, BorderLayout.EAST);
            p2[stockIndexSelected].setVisible(true);
            infoScreen = temp;
        }
    }
    
    public void getStockList()
    {
        for (int x = 0; x < totalStocks; x++) {
            //creates stocks
            stocks[x] = new Stock(stockNames[x]);
            
            //sets stock buttons
            stockButtons[x] = new JButton(SPACER + stocks[x].getName() + SPACER + "0.00%");
            stockButtons[x].setFont(SMALL_FONT);
            stockButtons[x].addActionListener(this);
            
            //adds stock buttons to p1
            p1.add(stockButtons[x]);
        }
    }
    
    public void updateStockInfo() {
        for (int x = 0; x < totalStocks; x++) {
            p2[x] = new StockInfoContainer(stocks[x]);
        }
    }

    public void updateButtons()
    {
        //controls button colors, updates the stock's values
        for (int x = 0; x < stocks.length; x++) {
            double change = stocks[x].nextDay();
            if (change >= 0) {
                stockButtons[x].setForeground(new Color(0, 153, 0));
                stockButtons[x].setText(SPACER + stocks[x].getName() + SPACER + "+" + two.format(change) + "%");
            } else {
                stockButtons[x].setForeground(Color.RED);
                stockButtons[x].setText(SPACER + stocks[x].getName() + SPACER + two.format(change) + "%");
            }
        }
    }
    
    public void updateBottomBar() {
        cash.setText(SPACER + "Cash: "+ two.format(portfolio.getCash()));
        stockValue.setText("Stock Value: "+ two.format(portfolio.getValue()));
        netWorth.setText("Net Worth: "+ two.format(portfolio.getNetWorth()));
        time.setText(SPACER + SPACER + SPACER + day +" days");
    }
    
    public JPanel homeScreen() {
        JPanel home = new JPanel();
        
        JLabel title = new JLabel("Your Portfolio:");
        title.setFont(BIG_FONT);
        home.add(title);
        
        JPanel stockScreen = new JPanel();
        
        //Left column is stock name, right column is number of stocks owned
        stockScreen.setLayout(new GridLayout(portfolio.stocksOwned(), 2));
        for (Stock stock : portfolio.getStocks()) {
            JLabel name = new JLabel(stock.getName() + SPACER);
            name.setFont(SMALL_FONT);
            stockScreen.add(name);
            JLabel num = new JLabel(Integer.toString(portfolio.getShares(stock)));
            num.setFont(SMALL_FONT);
            stockScreen.add(num);
        }
        
        home.add(stockScreen);
        
        return home;
    }
}
