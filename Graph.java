import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.lang.*;
import java.text.DecimalFormat;

public class Graph extends JPanel {
    private static DecimalFormat two = new DecimalFormat ("0.00");
    
    final int PAD = 50;
    final int axisThickness = 3;
    //xpos and ypos are offset values because it fits within a larger JFrame
    private ArrayList<Double> values;
    private String name;
    private Color color = Color.WHITE;
    public Graph(Stock s) {
        values = s.values();
        name = s.getName();
        setBackground(color);
    }
    public Graph (Stock s, int backlog) {
        values = s.values(backlog);
        name = s.getName();
        setBackground(color);
    }
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.fillRect(PAD, PAD, axisThickness, height - 2 * PAD);
        g.fillRect(PAD, height - PAD, (width - 2 * PAD), axisThickness);
        
        double y_max = 0;
        double y_min = values.get(0);
        for(int x = 0; x < values.size(); x++){
            if(values.get(x) > y_max){
                y_max = values.get(x);
            }
            if(values.get(x) < y_min){
                y_min = values.get(x);
            }
        }
        
        //TODO: Implement Scaled Imaging based on min and max
        double scaledY = y_max - y_min;
        
        double xScale = (width - 2 * PAD) / (double)(values.size() + 1);
        double yScale = (height - 2 * PAD) / y_max;
        
        for (int x = 0; x < values.size() - 1; x++) {
            //draw the lines
            double xPoint1 = x * xScale + PAD;
            double yPoint1 = (height - PAD) - (values.get(x) * yScale);
            double xPoint2 = (x + 1) * xScale + PAD;
            double yPoint2 = (height - PAD) - (values.get(x + 1) * yScale);
            g2.draw(new Line2D.Double(xPoint1, yPoint1 ,xPoint2, yPoint2));
            
            //labels highest historical price
            g2.drawString(two.format(y_max), 5, PAD);
            
            //labels name of stock
            g2.drawString(name, width / 2 - PAD, PAD / 2);
            
            //labels 0
            g2.drawString(" 0.00", 5, height - PAD);
        }
        g.setColor(Color.BLACK);
    }
    //setSize(), setBackgroundColor() inherited
}
