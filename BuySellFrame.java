import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BuySellFrame extends JFrame implements ActionListener 
{
    private JButton enter;
    private JTextField numShares;
    private JComboBox chooseStock;
    private JComboBox buySell;
    private JLabel errorMessage;
    
    private Stock[] stocks = Stock_Simulator_2000.go.stocks;
    private Stock[] portStocks = Stock_Simulator_2000.go.portfolio.getStocks();
    
    private String[] stockNames = Stock_Simulator_2000.go.stockNames;
    private String[] portStockNames = Stock_Simulator_2000.go.portfolio.getStockNames();
    
    private int day = Stock_Simulator_2000.go.day;
    private Portfolio portfolio = Stock_Simulator_2000.go.portfolio;
    
    private Hashtable<String, String[]> changeBuySell = new Hashtable<String, String[]>();
    
    public BuySellFrame() {
        super("Trade Stocks");
        
        String[] options = new String[] {"", "Buy", "Sell"};
        
        //set window
        Container win = getContentPane();
        //win.setLayout(new BorderLayout());
        
        //set the content of the JFrame
        
        //buy/sell option
        buySell = new JComboBox(options);
        buySell.addActionListener(this);
        ////  prevent action events from being fired when the up/down arrow keys are used
        buySell.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        
        //choose the stock, chooseStock changes based on buy or sell option
        numShares = new JTextField(5);
        chooseStock = new JComboBox<String>();
        chooseStock.setPrototypeDisplayValue("XXXXXXX");
        
        //setting up hashtable to change with buy/sell
        changeBuySell.put(options[1], stockNames);
        changeBuySell.put(options[2], portStockNames);
        
        //enter and error message
        enter = new JButton("Enter");
        enter.addActionListener(this);
        errorMessage = new JLabel();
        
        //add items to a panel
        JPanel myPanel = new JPanel();
        myPanel.add(buySell);
        myPanel.add(numShares);
        myPanel.add(new JLabel(" shares of "));
        myPanel.add(chooseStock);
        myPanel.add(enter);
        myPanel.add(errorMessage);
        
        //add panel to frame
        win.add(myPanel);
        
        setSize(300, 150);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            String shares = numShares.getText();
            //controls negative numbers and empty string inputs, uses short-circuit
            if (shares.equals("") || Integer.parseInt(shares) <= 0) {
                errorMessage.setText("Please enter a correct number of shares.");
                return;
            }

            int stockIndex = chooseStock.getSelectedIndex();
            
            //control buy/sell
            if (buySell.getSelectedItem().equals("Buy")) {
                Stock selected = stocks[stockIndex];
                System.out.println("buy " + selected.getName());
                //adds stock to portfolio, portfolio handles cash exchange
                portfolio.addStock(selected, Integer.parseInt(shares));
                Stock_Simulator_2000.go.updateBottomBar();
                setVisible(false);
            } else {
                Stock selected = portStocks[stockIndex];
                System.out.println("sell " + selected.getName());
                //removes stock, returns false if not possible
                boolean possible = portfolio.removeStock(selected, Integer.parseInt(shares));
                if (!possible) {
                    errorMessage.setText("Operation not possible.");
                } else {
                    Stock_Simulator_2000.go.updateBottomBar();
                    setVisible(false);
                }
            }
        } else {
            //controls buy/sell
            String choice = (String)buySell.getSelectedItem();
            Object o = changeBuySell.get(choice);
            
            if (o == null) {
                chooseStock.setModel(new DefaultComboBoxModel());
            } else {
                chooseStock.setModel(new DefaultComboBoxModel((String[]) o));
            }
        }
        
        
    }
}
