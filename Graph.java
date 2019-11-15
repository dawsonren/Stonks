import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.lang.*;

public class Graph extends JFrame {
    private int width, height;
    private ArrayList<Double> values;

    public Graph(int w, int h, ArrayList<Double> v) {
        height = h;
        width = w;
        values = v;

        Container win = getContentPane();

        //graph has name and trend and points
        ColorPanel canvas = new ColorPanel(Color.WHITE);
        win.add(canvas);
        setSize(w, h);
        setVisible(true);
    }

    public class ColorPanel extends JPanel{
        public ColorPanel(Color c) {
            setBackground(c);
        }
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g.fillRect(20,0, 3, (height - 20));
            g.fillRect(20,(height - 23), (width - 20), 3);
            double y_max = 0;
            for(int x = 0; x < values.size(); x++){
                if(values.get(x) > y_max){
                    y_max = values.get(x);
                }
            }
            System.out.println(y_max);
            for (int x = 0; x < values.size() - 1; x++) {
                double xPoint1 = ((x * (width - 40)) / (double)(values.size() - 1)) + 20;
                double yPoint1 = (values.get(x) / y_max) * (height - 40) + 20;
                double xPoint2 = (((x + 1) * (width - 40)) / (double)(values.size() - 1)) + 20;
                double yPoint2 = (values.get(x + 1) / y_max) * (height - 40) + 20;
                System.out.printf("(%s, %s), (%s, %s) ", xPoint1, yPoint1, xPoint2, yPoint2);
                g2.draw(new Line2D.Double(xPoint1, yPoint1 ,xPoint2, yPoint2));
            }
            g.setColor(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(20.0);
        list.add(40.0);
        list.add(60.0);
        list.add(10.0);
        list.add(100.0);
        Graph g = new Graph(400, 400, list);
    }

}
