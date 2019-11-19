import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.lang.*;

public class Graph extends JPanel {
    final int PAD = 20;
    final int axisThickness = 3;
    //xpos and ypos are offset values because it fits within a larger JFrame
    private ArrayList<Double> values;
    private Color color = Color.WHITE;
    public Graph(ArrayList<Double> v) {
        values = v;
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
        for(int x = 0; x < values.size(); x++){
            if(values.get(x) > y_max){
                y_max = values.get(x);
            }
        }
        
        double xScale = (width - 2 * PAD) / (double)(values.size() + 1);
        double yScale = (height - 2 * PAD) / y_max;
        
        for (int x = 0; x < values.size() - 1; x++) {
            double xPoint1 = x * xScale + PAD;
            double yPoint1 = height - (values.get(x) * yScale + PAD);
            double xPoint2 = (x + 1) * xScale + PAD;
            double yPoint2 = height - (values.get(x + 1) * yScale + PAD);
            g2.draw(new Line2D.Double(xPoint1, yPoint1 ,xPoint2, yPoint2));
        }
        g.setColor(Color.BLACK);
    }
    //setSize(), setBackgroundColor() inherited
    
    public static void showGraph(ArrayList<Double> data) {
        JFrame f = new JFrame();
        f.getContentPane().add(new Graph(data));
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}
