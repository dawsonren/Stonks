public class Stock_Simulator_2000
{
    public static Screen go;
    
    public static final String[] stockNames = {"Apple", "Microsoft", "Samsung", "Nintendo", "PlayStation", "Amazon"};
    public static final int totalStocks = stockNames.length;
    public static final double startingCash = 100000.0;
    
    public static void main(String[] args) {
        go = new Screen();
    }
}
