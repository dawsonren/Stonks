import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class HelpFrame extends JFrame
{
    String story, basics, stocks, bas;
    JLabel storyline, basicLabel, stockInfo, basLabel;
    JLabel Title2= new JLabel("View Stocks"), Title1= new JLabel("Game View and Basics"),Title3= new JLabel("How to Buy/Sell");
    private final Font SMALL_FONT = new Font("Times New Roman", Font.PLAIN, 18);
    private final Font BIG_FONT = new Font("Times New Roman", Font.PLAIN, 20);
    private final Font HEADINGS = new Font("Times New Roman", Font.BOLD, 22);
    
    public HelpFrame()
    {
        new JFrame("How to Play");
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        Story();
        Basics();
        StockInfo();
        BuyAndSell();
        
        Title1.setFont(HEADINGS);
        Title2.setFont(HEADINGS);
        Title3.setFont(HEADINGS);
        
        add(storyline);
        
        add(Title1);
        add(basicLabel);
        
        add(Title2);
        add(stockInfo);
        
        add(Title3);
        add(basLabel);
        
        setVisible(true);
        setLocation(470, 300);
        setSize(1440,800);
    }
    
    public void Story ()
    {
        story="<HTML>You're a \"Wall Street\" broker, and you've fradulently promised that you'd be able to double the amount of money invested within 100 days...<br>";
        story+="Unforunately, your computer science degree has left you ill-prepared to deal with the fast-paced changes of a volatile stock market.<br>";
        story+="You decide to forgo the penny stocks and just invest in the blue chip stocks.<br>";
        story+="Will you be able to pay your clients? Or, will you break the bank and be prosecuted by the SEC?<br><br></HTML>";
        
        storyline=new JLabel(story);
        storyline.setFont(SMALL_FONT);
    }
    
    public void Basics()
    {
        basics= "<HTML>•The buttons on the left side of the screen are for stocks<br>";
        basics+="•Your portfolio is on the right side of the screen, Your portfolio displays the number of shared of each stock you own<br>";
        basics+="•Along the bottom is your cash, stock value, net worth, and days that have passed<br>";
        basics+="•Your cash is the amount of money you currently have available to buy stocks<br>";
        basics+="•The stock value is the value of all stocks you currently own, and net worth is the worth of your assets minus your liabilities<br>";
        basics+="•When you hit next day in the bottom right, it will default back to your portfolio until you select a stock<br><br></HTML>";
        
        basicLabel= new JLabel(basics);
        basicLabel.setFont(SMALL_FONT);
    }
    
    public void StockInfo ()
    {
        stocks="<HTML>•Click on a stock name to see it's value over all time.<br>";
        stocks+="•If you would like to view weekly or monthy simply click \"See past month\" or \"See past week\"<br>";
        stocks+="•This screen also displays the current stock price (the cost of 1 share)<br>";
        stocks+="•In addition, when you hit next day next to stock names you will see the percent increase or decrease of value from yesterday.<br><br></HTML>";
        
        stockInfo = new JLabel(stocks);
        stockInfo.setFont(SMALL_FONT);
    }
    
    public void BuyAndSell()
    {
        bas="<HTML>In order to buy or sell shares, click the menu in the top left corner that says \"Menu\" when you are on the default page<br>";
        bas+="A window will pop up on your screen. On the first drop down menu, select either buy or sell<br>";
        bas+="In the space, type in how many shares you would like to buy/sell, then in the second drop down, select what stock you would like to buy or sell<br>";
        bas+="Hit enter. If it says \"Operation not possible\", it is either because you have insufficient funds for buying, or you are trying to sell more shares of a stock than you currently own.<br><br></HTML>";
        
        basLabel= new JLabel(bas);
        basLabel.setFont(SMALL_FONT);
    }
}
