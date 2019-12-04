import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.awt.event.*;

public class StockInfoContainer extends JPanel implements ActionListener {
    private static DecimalFormat two = new DecimalFormat ("0.00");
    
    private Graph graph, graphAll, weekView, monthView;
    private JLabel price;
    private JButton week, month,all;
    private JPanel extras;
    public StockInfoContainer(Stock s) {
        super();
        int valueSize = s.values().size();
        //add graph
        graphAll = new Graph(s.values());
        graphAll.setPreferredSize(new Dimension(475,430));
        
        extras = new JPanel();
        extras.setLayout(new GridLayout(4,2,0,3));
        
        graph = graphAll;
        add(graph);
        
        //add price
        JLabel price = new JLabel("  Current Stock Price: " + two.format(s.getCurrentPrice()));
        price.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        extras.add(price);
        week= new JButton ("See past week");
        week.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        month= new JButton ("See past month");
        month.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        all=new JButton ("See all time");
        all.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        if(valueSize>0)
        {
            if(valueSize<7)
                weekView=new Graph(s.values(valueSize-1));
            else
                weekView = new Graph(s.values(7));
        
            if(valueSize<30)
                monthView= new Graph(s.values(valueSize-1));
            else
                monthView = new Graph(s.values(30));
        
            weekView.setPreferredSize(new Dimension(400,400));
            extras.add(week);
            week.addActionListener(this);
        
            monthView.setPreferredSize(new Dimension(400,400));
            extras.add(month);
            month.addActionListener(this);
            
            extras.add(all);
            all.addActionListener(this);
            
            add(extras);
        }
    }
    
    public void actionPerformed (ActionEvent e)
    {
        if(e.getSource()==week)
            graph=weekView;
        if(e.getSource()==month)
            graph=monthView;
        if(e.getSource()==all)
            graph=graphAll;
        repaint();
    }
}
