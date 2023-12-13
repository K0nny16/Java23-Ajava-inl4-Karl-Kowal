import javax.swing.*;
import java.awt.*;

class GUI extends JFrame {
    GUI(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        add(top,BorderLayout.NORTH);
        JPanel center = new JPanel();
        add(center,BorderLayout.CENTER);
        center.setLayout(new GridLayout(0,4,0,1));


        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setVisible(true);
    }
}
