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
    private final Font tnr18 = new Font("Times New Roman", Font.PLAIN, 18);
    private final Font tnr20 = new Font("Times New Roman", Font.PLAIN, 20);
    
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
    private JPanel stockSelected;
    private int stockIndexSelected;
    
    //stocks is used for accessing stock information in general
    private Stock[] stocks = new Stock[totalStocks];
    
    //portfolio is used for managing the portfolio
    private Portfolio portfolio = new Portfolio(totalStocks, startingCash);
    
    //labels cash, stockvalue, time, and networth go on the bottom. Info controls what's said in p2.
    private JLabel cash, stockValue, netWorth, time, graph, info;
    
    private int day = 0;
    
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
        
        //default page is apple
        stockSelected = p2[0];
        stockIndexSelected = 0;
        
        //set layout for bottom bar
        p3.setLayout(new GridLayout(1,5));
        
        //Bottom of screen (cash, stockValue, netWorth, day and NewDay button)
        cash = new JLabel("Cash: "+ two.format(portfolio.getCash()));
        stockValue = new JLabel("Stock Value: "+ two.format(portfolio.getValue()));
        netWorth = new JLabel("Net Worth: "+ two.format(portfolio.getNetWorth()));
        time= new JLabel("                       " + day +" days");
        newDay=new JButton("New Day");
        
        //menu for what screen the player is looking at (overview, buy, sell, help)
        JMenuBar bar = new JMenuBar();
        JMenu menu= new JMenu("Menu");
        m1 = new JMenuItem("Overview");
        m1.setFont(tnr18);
        m1.addActionListener(this);
        m2 = new JMenuItem("Buy");
        m2.setFont(tnr18);
        m2.addActionListener(this);
        m3 = new JMenuItem("Sell");
        m3.setFont(tnr18);
        m3.addActionListener(this);
        m4 = new JMenuItem("Help");
        m4.setFont(tnr18);
        m4.addActionListener(this);
        
        //JMenu for swtching what you are seeing
        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        menu.add(m4);
        bar.add(menu);
        setJMenuBar(bar);
        menu.setFont(tnr20);
        
        
        //set Fonts for bottom of screen
        cash.setFont(tnr20);
        stockValue.setFont(tnr20);
        netWorth.setFont(tnr20);
        time.setFont(tnr20);
        newDay.setFont(tnr20);
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
            updateBottomBar();
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
            updateBottomBar();
        } else if (e.getSource() == m4) {
            
        }
        
        //changes out the current viewable p2, or stock information when press
        //the stock's button
        for (int x = 0; x < totalStocks; x++) {
            if (e.getSource() == stockButtons[x]) {
                stockSelected.setVisible(false);
                win.add(p2[x], BorderLayout.EAST);
                p2[x].setVisible(true);
                stockSelected = p2[x];
                stockIndexSelected = 0;
            }
        }
        
       //next day button
        if (e.getSource() == newDay) {
            System.out.println("Day Forward");
            day++;
            //update buttons must come first because it updates stock information
            updateButtons();
            updateBottomBar();
            updateStockInfo();
            
            stockSelected.setVisible(false);
            win.add(p2[stockIndexSelected], BorderLayout.EAST);
            p2[stockIndexSelected].setVisible(true);
            stockSelected = p2[stockIndexSelected];
        }
    }

    public void getStockList()
    {
        for (int x = 0; x < totalStocks; x++) {
            //creates stocks
            stocks[x] = new Stock(stockNames[x]);
            
            //sets stock buttons
            stockButtons[x] = new JButton(spacer + stocks[x].getName() + spacer + "0.00%");
            stockButtons[x].setFont(tnr18);
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
                stockButtons[x].setText(spacer + stocks[x].getName() + spacer + "+" + two.format(change) + "%");
            } else {
                stockButtons[x].setForeground(Color.RED);
                stockButtons[x].setText(spacer + stocks[x].getName() + spacer + two.format(change) + "%");
            }
        }
    }
    
    public void updateBottomBar() {
        cash.setText("Cash: "+ two.format(portfolio.getCash()));
        stockValue.setText("Stock Value: "+ two.format(portfolio.getValue()));
        netWorth.setText("Net Worth: "+ two.format(portfolio.getNetWorth()));
        time.setText("                       " + day +" days");
    }
}
