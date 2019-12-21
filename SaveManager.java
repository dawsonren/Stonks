import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SaveManager extends JFrame implements ActionListener 
{
    private final Font SMALL_FONT = new Font("Times New Roman", Font.PLAIN, 18);
    private final Font BIG_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    
    private Container win;
    private JPanel current;
    
    //chooseSave is the menu where you choose to save or load.
    //loadSave is where you load a previous save.
    //enterSave is where you enter in your current game into a save file.
    private JPanel enterSave, loadSave, chooseSave;
    
    private Portfolio portfolio;
    
    //various JComponents for the three JPanels
    private JTextArea fileName1, fileName2;
    private JButton enterName, enterLoad, enter, left, right, back1, back2;
    private JLabel instruction1, instruction2;
    public SaveManager(Portfolio port) {
        super("Save Wizard");
        
        portfolio = port;
        setLocation(450, 250);
        win = getContentPane();
        
        //chooseSave stuff
        chooseSave = new JPanel();
        chooseSave.setLayout(new GridLayout(1, 2));
        left = new JButton("Save current game");
        left.addActionListener(this);
        right = new JButton("Load previous game");
        right.addActionListener(this);
        chooseSave.add(left);
        chooseSave.add(right);
        
        //enterSave stuff
        enterSave = new JPanel();
        instruction1 = new JLabel("<HTML>Please enter the <br>name of your <br>save file. <br>Do not include <br>.txt or any <br>punctuation.");
        fileName1 = new JTextArea("save");
        enterName = new JButton("Enter");
        enterName.addActionListener(this);
        enterName.setFont(SMALL_FONT);
        back1 = new JButton("Back");
        back1.setFont(SMALL_FONT);
        back1.addActionListener(this);
        
        enterSave.add(instruction1);
        enterSave.add(fileName1);
        enterSave.add(enterName);
        enterSave.add(back1);
        
        //loadSave stuff
        loadSave = new JPanel();
        instruction2 = new JLabel("<HTML>Please enter the <br>name of your <br>save file.</HTML>");
        fileName2 = new JTextArea("save");
        enterLoad = new JButton("Select");
        enterLoad.addActionListener(this);
        back2 = new JButton("Back");
        back2.setFont(SMALL_FONT);
        back2.addActionListener(this);
        
        loadSave.add(instruction2);
        loadSave.add(fileName2);
        loadSave.add(enterLoad);
        loadSave.add(back2);
        
        win.add(chooseSave);
        current = chooseSave;
        
        pack();
        setSize(400, 300);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == left) {
            current.setVisible(false);
            win.add(enterSave);
            enterSave.setVisible(true);
            current = enterSave;
        } else if (e.getSource() == right) {
            current.setVisible(false);
            win.add(loadSave);
            loadSave.setVisible(true);
            current = loadSave;
        } else if (e.getSource() == back1 || e.getSource() == back2) {
            current.setVisible(false);
            win.add(chooseSave);
            chooseSave.setVisible(true);
            current = chooseSave;
        } else if (e.getSource() == enterName) {
            String str = fileName1.getText();
            try {
                saveToFile(str);
            } catch (Exception io) {
                System.out.println(io);
            }
            setVisible(false);
        } else if (e.getSource() == enterLoad) {
            try {
                System.out.println(fileName2.getText());
                Portfolio port = loadFile(fileName2.getText() + ".txt");
                
                //creates a new instance of Screen
                Stock_Simulator_2000.go = new Screen(port);
            } catch (Exception io) {
                System.out.println(io);
            }
            setVisible(false);
        }
    }
    
    public static void test() {
        //dummy port
        Portfolio port = new Portfolio(5, 20);
        try {
            port = loadFile("save.txt");
        } catch (Exception io) {
            System.out.println(io);
        }
        //creates a new instance of Screen
        Stock_Simulator_2000.go = new Screen(port);
    }
    
    public void saveToFile(String name) throws IOException {
        PrintWriter pw = new PrintWriter(new File(name + ".txt"));
        
        //prints current cash value
        String cash = String.valueOf(portfolio.getCash());
        pw.println(cash);
        //prints current day
        pw.println(Integer.toString(Stock_Simulator_2000.go.day));
        //prints size of portfolio (maximum number of stocks)
        pw.println(portfolio.getSize());
        
        String stockInfo = "";
        //prints all stocks and their daily prices
        for (Stock cstock : Stock_Simulator_2000.go.stocks) {
            //first adds name
            stockInfo += cstock.getName();
            //same line, adds number of shares (ex. Apple,2)
            if(portfolio.getShares(cstock) == 0) {
                //if 0, that means we don't own any of this stock
                stockInfo += ",0";
            } else {
                stockInfo += ("," + portfolio.getShares(cstock));
            }
            stockInfo += "\n";
            
            //adds important latent values
            //and historical values, one line per day value
            stockInfo += cstock.toString();
            
            //read/print the returned line of "stocksOwned, name, (latent values), (historical values)"
            pw.println(stockInfo);
            stockInfo = "";
        }
        pw.close();
    }
    
    public static Portfolio loadFile(String fileName) throws IOException{
        HashMap<Stock, Integer> stocks = new HashMap<Stock, Integer>();
        
        Scanner fr = new Scanner(new File(fileName));
        
        //gets cash, day, size
        String cashString = fr.nextLine();
        double cash = Double.valueOf(cashString);
        String dayString = fr.nextLine();
        int day = Integer.parseInt(dayString);
        String sizeString = fr.nextLine();
        int size = Integer.parseInt(sizeString);
        
        Stock currentStock;
        String name = "";
        ArrayList<Double> values = new ArrayList<Double>();
        int price = 0;
        int function = 0;
        int volatility = 1;
        double trend20 = 0;
        double luck = 0;
        int shares = 0;
        while(fr.hasNextLine()) {
            String line = fr.nextLine();
            //has two possible types of lines: (Stock Name, Shares) + (Latent Values) 
            //or (Historical Prices)
            if (line.indexOf(",") != -1) {
                //reset a new values array
                values = new ArrayList<Double>();
                //line has comma, therefore must be (Stock Name, Shares)
                //followed by (Latent Values)
                String[] stockInfo = line.split(",");
                name = stockInfo[0];
                shares = Integer.parseInt(stockInfo[1]);
                line = fr.nextLine();
                stockInfo = line.split(",");
                function = Integer.parseInt(stockInfo[0]);
                volatility = Integer.parseInt(stockInfo[1]);
                trend20 = Double.parseDouble(stockInfo[2]);
                luck = Double.parseDouble(stockInfo[3]);
            } else if (!line.equals("")) {
                double nextValue = Double.parseDouble(line);
                //a historical value
                values.add(nextValue);
            } else if (line.equals("")) {
                //empty line signals gap between stocks
                if (shares > 0) {
                    currentStock = new Stock(name, values, day, values.get(day - 1), function, volatility, trend20, luck);
                    stocks.put(currentStock, shares);
                }
            }
        }
        System.out.println(stocks.size());
        return new Portfolio(stocks, cash, size);
    }
}
