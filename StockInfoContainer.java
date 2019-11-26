import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class StockInfoContainer extends JPanel {
    private static DecimalFormat two = new DecimalFormat ("0.00");
    
    private Graph graph;
    private JLabel price;
    
    public StockInfoContainer(Stock s) {
        super();
        
        //add graph
        Graph g = new Graph(s.values());
        g.setPreferredSize(new Dimension(400, 400));
        add(g);
        
        //add price
        JLabel price = new JLabel("Current Stock Price: " + two.format(s.getCurrentPrice()));
        add(price);
    }
}
