import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.awt.event.*;

public class StockInfoContainer extends JPanel implements ActionListener {
    private static DecimalFormat two = new DecimalFormat ("0.00");
    private final Font TNR18 = new Font("Times New Roman", Font.PLAIN, 18);
    
    private Graph graph, allView, weekView, monthView;
    private JLabel price;
    private JButton week, month,all;
    private JPanel extras;
    public StockInfoContainer(Stock s) {
        super();
        
        int valueSize = s.values().size();
        
        //add graphs
        allView = new Graph(s);
        allView.setPreferredSize(new Dimension(650, 650));
        
        if(valueSize>0) {
            if(valueSize<=7) {
                weekView=new Graph(s, valueSize - 1);
            } else {
                weekView = new Graph(s, 7);
            }
            if(valueSize<=30) {
                monthView= new Graph(s, valueSize - 1);
            } else {
                monthView = new Graph(s, 30);
            }
            weekView.setPreferredSize(new Dimension(650, 650));
            monthView.setPreferredSize(new Dimension(650, 650));
        }

        extras = new JPanel();
        extras.setLayout(new GridLayout(4,1,0,3));

        //add price label, three buttons
        JLabel price = new JLabel("Current Stock Price: $" + two.format(s.getCurrentPrice()));
        price.setFont(TNR18);
        week= new JButton ("See past week");
        week.setFont(TNR18);
        week.addActionListener(this);
        month= new JButton ("See past month");
        month.setFont(TNR18);
        month.addActionListener(this);
        all=new JButton ("See all time");
        all.setFont(TNR18);
        all.addActionListener(this);
        
        extras.add(price);
        extras.add(all);
        extras.add(month);
        extras.add(week);
        
        
        add(extras);
        
        //default graph is allView, add graph
        graph = allView;
        add(graph);
    }
    
    public void actionPerformed (ActionEvent e)
    {
        //swap components
        if(e.getSource()==week) {
            graph.setVisible(false);
            add(weekView);
            graph = weekView;
            graph.setVisible(true);
        }
        if(e.getSource()==month) {
            graph.setVisible(false);
            add(monthView);
            graph = monthView;
            graph.setVisible(true);
        }
        if(e.getSource()==all) {
            graph.setVisible(false);
            add(allView);
            graph = allView;
            graph.setVisible(true);
        }
    }
}
