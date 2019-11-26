import java.util.*;

public class Stock {
  //index = days since start, value = price
  private ArrayList<Double> values = new ArrayList<>();
  private double price;
  private int day = 0;
  private String name;
  //volatility is a multiplier, where the change of price is given by:
  //change in price = volatility x (random number between 0.0 and 1.0)
  private Random rd = new Random();
  private final int function = rd.nextInt(5);
  
  //Volatility can be changed by passing the nextDouble() through "function", which is determined
  //randomly.
  //sin(x) is about the same but values above 0.5 are slightly tuned down (> 0.5 down)
  //x^2 makes the values extremely small, but values close to one are preserved (more extreme)
  //sqrt(x) makes the values way big (make them hurt)
  //sinh(x) is about the same but values above 0.5 are slightly tuned up (> 0.5 up)
  public Stock(String n) {
    name=n;
    //price will be between $15 and $200
    price = rd.nextInt(186) + 15;
    values.add(day, price);
  }
  public double nextDay() {
    //volatility must be calculated before bumping day
    double volatility = getCurrentPrice() / 10;
    
    day++;
    
    //highlow controls whether it's going to up or down
    //highlow = 0 means down, highlow = 1 means up
    int highlow = rd.nextInt(2);
    if (highlow == 0) {
      price -= functionize(rd.nextDouble()) * volatility;
    } else {
      price += functionize(rd.nextDouble()) * volatility;
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
    return price;
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
}
