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
    public Stock[] loadFile(Stock[] stocks) throws IOException{
            Scanner fr = new Scanner(new File("save.txt"));
            String str = fr.nextLine();
            cash = Double.valueOf(str);
            String name;
            int start, stop, count, countShares;
            boolean flag = true;
            while(fr.hasNextLine()){
                str = fr.nextLine();
                countShares = 0;
                count = 0;
                start = 0;
                stop = 0;
                name = "";
                System.out.println(str);
                for(int x = 0; x<str.length()-1; x++){
                    if(str.charAt(x) == ',' && count == 0){
                        stop = x;
                        countShares = Integer.valueOf(str.substring(start,stop));
                        start = stop+1;
                        count++;
                    }
                    //save.info part 2
                    else if(str.charAt(x) == ',' && count == 1  && countShares > 0){
                        ArrayList<Double> vals = new ArrayList<Double>();
                        stop = x;
                        name = str.substring(start,stop);
                        count++;
                        System.out.println("Name: " + name + "Count: " + countShares);
                        vals = readDays(stocks, x+1, str.substring(x+1));
                        Object[] valsarray = vals.toArray();
                        System.out.println(Arrays.toString(valsarray));
                        break;
                    }
                }
                System.out.println("here");
            }
            
            
            return stocks;
        }
        public ArrayList<Double> readDays(Stock[] stocks, int pos, String str){
            ArrayList<Double> vals = new ArrayList<Double>();
            int daycount = -1, start = 0, stop = 0;
            double currentval = 0;
            for(int x=0; x<str.length(); x++){
                if(str.charAt(x) == ','){
                    stop = x;
                    currentval = Double.valueOf(str.substring(start,stop));
                    x = stop+1;
                    start = stop+1;
                    daycount++;
                    if(currentval > .001){
                        vals.add(currentval);
                    }
                }
            }
            return vals;
        }
        public void saveToFile(Stock[] stocks) throws IOException{
            PrintWriter pw = new PrintWriter(new File("save.txt"));
            
            //get portfolio value
            String data = String.valueOf(cash);
            System.out.println(data);
            pw.println(data);
            
            //save all stocks
            for (Stock cstock: stocks) {
                if(port.get(cstock) == null)
                    data = "0,";
                else
                    data = port.get(cstock) + ",";
                data += cstock.toString();
                System.out.println(data);
                pw.println(data);
                
            }
            pw.close();
            
        }
}
