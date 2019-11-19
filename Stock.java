import java.util.*;

public class Stock {
  //index = days since start, value = price
  private ArrayList<Double> values = new ArrayList<>();
  private double price;
  private int day;
  private String name;
  //volatility is a multiplier, where the change of price is given by:
  //change in price = volatility x (random number between 0.0 and 1.0)
  private double volatility;
  private Random rd = new Random();
  public Stock(String n) {
    name=n;
    //day 0 at initialization
    day = 0;
    //price will be between $5 and $199
    price = rd.nextInt(200) + 5;
    //volatility is scaled to the total price, so percent change per day
    //is capped at 10%
    volatility = price / 10;
    values.add(day, price);
  }
  public void nextDay() {
    //highlow controls whether it's going to up or down
    //highlow = 0 means down, highlow = 1 means up
    int highlow = rd.nextInt(2);
    if (highlow == 0) {
      price -= rd.nextDouble() * volatility;
    } else {
      price += rd.nextDouble() * volatility;
    }
    day++;
    values.add(day, price);
  }
  public double getPrice() {
    return price;
  }
  public double getPrice(int day) {
    return values.get(day);
  }
  public int getDay() {
    return day;
  }
  public String getName()
  {
    return name;
  }
  public ArrayList<Double> values() {
    return values;
  }
}
