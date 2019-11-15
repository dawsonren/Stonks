import java.util.*;

public class Player
{
    //the player's name
    private String name;
    //a hashmap where the key is the name of the stock and the value is the number of shares
    private HashMap<String,Integer> shares;
    
    public void Player(String n)
    {
        n=name;
        shares = new HashMap<String,Integer> (5);
        createHash();
    }
    
    //this is just a seperate method to initialize an empty hashMap for day 1 (they have 0 shares)
    public void createHash()
    {
        shares.put("stock1",0);
        shares.put("stock2",0);
        shares.put("stock3",0);
        shares.put("stock4",0);
        shares.put("stock5",0);
    }
    
    //updates shares of a certain stock
    public void updateShares(String stock, int value)
    {
        shares.replace(stock,value);
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getValue(String stock)
    {
        return shares.get(stock);
    }
    
}
