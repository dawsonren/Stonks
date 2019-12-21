import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EndFrame extends JFrame implements ActionListener 
{
    private JLabel playAgain;
    private JButton yes;
    private JButton no;
    private JLabel message;
    
    private final Font SMALL_FONT = new Font("Times New Roman", Font.PLAIN, 26);
    private final Font BIG_FONT = new Font("Times New Roman", Font.PLAIN, 36);
    
    public EndFrame(Portfolio portfolio) {
        super();
        
        setLocation(470, 300);
        
        Container win = getContentPane();
        JPanel content = new JPanel();
        
        if (portfolio.getValue() >= 20000.0) {
            this.setTitle("You Win!");
            
            message = new JLabel("You've evaded the dreaded SEC! Go buy yourself something nice...and don't rip off investors!");
            message.setFont(SMALL_FONT);
            content.add(message);
        } else {
            this.setTitle("You Lose!");
            
            message = new JLabel("You've been caught by the SEC...see you in court! Don't rip off investors!");
            message.setFont(SMALL_FONT);
            content.add(message);
        }
        
        JLabel playAgain = new JLabel("Play Again?");
        playAgain.setFont(BIG_FONT);
        content.add(playAgain);
        
        yes = new JButton("Yes");
        yes.setFont(SMALL_FONT);
        yes.addActionListener(this);
        no = new JButton("No");
        no.setFont(SMALL_FONT);
        no.addActionListener(this);
        content.add(yes);
        content.add(no);
        
        win.add(content);
        
        setSize(500, 150);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == no) {
            System.exit(0);
            
            setVisible(false);
            dispose();
        } else if (source == yes) {
            Stock_Simulator_2000.go.dispose();
            Stock_Simulator_2000.main(new String[1]);
            
            setVisible(false);
            dispose();
        }
    }
}
