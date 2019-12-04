import java.util.HashMap;

public class Portfolio
{
    private HashMap<Stock, Integer> port;
    private double cash;
    private int size;
    //size is how many stocks it can hold
    
    public Portfolio(int siz, double funds) {
        port = new HashMap<Stock, Integer>();
        cash = funds;
        size = siz;
    }
    public void addStock(Stock s, int num) {
        for (Stock stock : port.keySet()) {
            //compare different stocks in portfolio to the one requested
            //if it's the same, add to value
            if (stock.getName().equals(s.getName())) {
                //replace(key, oldValue, newValue)
                port.replace(s, port.get(s), port.get(s) + num);
                cash -= s.getCurrentPrice() * num;
                return;
            }
        }
        port.put(s, num);
        
        cash -= s.getCurrentPrice() * num;
    }
    //boolean value shows if they have enough stock to sell
    public boolean removeStock(Stock s, int num) {
        System.out.println(port.get(s));
        if (port.get(s) < num) {
            //returns false if you're trying to sell more than you have
            return false;
        } else if (port.get(s) == num) {
            //gets rid of stock if you're selling all you have
            port.remove(s);
        } else {
            //replace(key, oldValue, newValue)
            port.replace(s, port.get(s), port.get(s) - num);
        }
        //adds the stock value to cash
        cash += s.getCurrentPrice() * num;
        return true;
    }
    
    public int getShares(Stock s) {
        return port.get(s);
    }
    
    public double getCash() {
        return cash;
    }
    
    public double getValue()
    {
        double total = 0;
        for (Stock stock : port.keySet()) {
            total += stock.getCurrentPrice() * port.get(stock);
        }
        return total;
    }
    
    public String[] getStockNames() {
        String[] names = new String[port.size()];
        int counter = 0;
        for (Stock stock : port.keySet()) {
            names[counter] = stock.getName();
            counter++;
        }
        return names;
    }
    
    public Stock[] getStocks() {
        Stock[] stocks = new Stock[port.size()];
        int counter = 0;
        for (Stock stock : port.keySet()) {
            stocks[counter] = stock;
            counter++;
        }
        return stocks;
    }
    
    public double getNetWorth() {
        return getCash() + getValue();
    }
    
    public int stocksOwned() {
        return port.size();
    }
}

