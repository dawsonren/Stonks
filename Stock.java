import java.util.*;

public class Stock {
    //index = days since start, value = price
    private ArrayList<Double> values = new ArrayList<Double>();
    private double price;
    private int day = 0;
    private String name;
    private Random rd = new Random();
    private final int function;
    private final int volatility;
    private final double luck;
    
    //trend20 represents a 20-day trend
    private double trend20;
    
    //Volatility can be changed by passing the nextDouble() through "function", which is determined
    //randomly.
    //sin(x) is about the same but values above 0.5 are slightly tuned down (> 0.5 down)
    //x^2 makes the values extremely small, but values close to one are preserved (more extreme)
    //sqrt(x) makes the values way big (make them hurt)
    //sinh(x) is about the same but values above 0.5 are slightly tuned up (> 0.5 up)
    public Stock(String n) {
        name = n;
        //price will be between $15 and $200
        price = rd.nextInt(186) + 15;
        values.add(day, price);
        function = rd.nextInt(5);
        //volatility is a number between 10 (10% change between days) 
        //and 33 (3% change between days)
        volatility = (rd.nextInt(24) + 10);
        
        //trend is a central-seeking tendancy that updates every twenty days
        trend20 = price * rd.nextDouble() / 100;
        //luck is a general, never-changing trend with constraints [-0.5, 0.5]
        luck = rd.nextDouble() - 0.5;
    }
    public Stock(String n, ArrayList<Double> val, int d, double pri, int func, int vol, double tre, double lu) {
        name = n;
        values = val;
        day = d;
        
        price = pri;
        function = func;
        volatility = vol;
        trend20 = tre;
        luck = lu;
    }
    public double nextDay() {
        //scaledVolatility must be calculated before bumping day
        //scaledVolatility is scaled to current price
        double scaledVolatility = getCurrentPrice() / volatility;
        
        //change the slope and sign of trend
        if (day % 20 == 0) {
            //% change in price, if current is smaller then trend will be +, vice versa
            double percentChange = (getFirstPrice() - getCurrentPrice()) / getFirstPrice();
            //trend 20 is scaled to current price, at most 1% change
            trend20 = percentChange * getCurrentPrice() / 100;
        }
        price += trend20;
        price = price * (100 + luck) / 100;
        
        day++;
    
        //highlow controls whether it's going to up or down
        //highlow = 0 means down, highlow = 1 means up
        int highlow = rd.nextInt(2);
        if (highlow == 0) {
          price -= functionize(rd.nextDouble()) * scaledVolatility;
        } else {
          price += functionize(rd.nextDouble()) * scaledVolatility;
        }
        values.add(day, price);
        
        //calculates change
        double change = ((values.get(day) - values.get(day - 1)) / values.get(day)) * 100;
        
        //returns the percent change
        return change;
    }
    public double functionize(double given) {
        switch(function) {
            case 0:
              return Math.pow(given, 2);
            case 1:
              return Math.sinh(given);
            case 2:
              return Math.sin(given);
            case 3:
              return Math.sqrt(given);
            case 4:
              return given;
        }
        return given;
    }
    public double getFirstPrice() {
        return values.get(0);
    }
    public double getPrice(int d) {
        return values.get(d);
    }
    public double getCurrentPrice() {
        return values.get(day);
    }
    public String getName()
      {
        return name;
      }
    public ArrayList<Double> values() {
        return values;
    }
    public ArrayList<Double> values(int backlog) {
        //backlog is how far back you want to go
        List<Double> old_values = values.subList(day - backlog, day + 1);
        return new ArrayList<Double>(old_values);
    }
    public int getDay() {
        return day;
    }
    public String toString() {
        String str = "";
        //first part are the latent control values
        str += function + "," + volatility + "," + trend20 + "," + luck + "\n";
        
        //returns something like: "195.0\n194.25\n193.2...etc."
        for (int x = 0; x < values.size(); x++) {
            str += (values.get(x) + "\n");
        }
        return str;
    }
}
