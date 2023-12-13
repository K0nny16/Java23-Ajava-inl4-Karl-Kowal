import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

class GUI extends JFrame {
    GUI(){
        new Database();
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
        JPanel center = new JPanel();
        Border gray = BorderFactory.createLineBorder(Color.GRAY);
        center.setLayout(new GridLayout(0,4,0,1));

        addUser(top);
        addContent(gray,center);
        selectUser(top);

        add(top,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setVisible(true);
    }
    void addContent(Border border,JPanel center){
        for(int i = 0; i < 4; i++){
            JPanel panel = new JPanel();
            panel.setBorder(border);
            center.add(panel);
        }
    }
    void addUser(JPanel panel){
        JButton addUser = new JButton("Add User");
        addUser.addActionListener(e -> userMenu());
        panel.add(addUser);
    }
    void selectUser(JPanel panel){
        JComboBox<String> users = new JComboBox<>(Database.getNames());

        panel.add(users);
    }
    void userMenu(){
        String username = JOptionPane.showInputDialog("Enter your profile name:");
        String unFormatted = JOptionPane.showInputDialog("Which cities do you want the weather of?(ex Malmö,Trelleborg,Svedala,Ystad)");
        String[] formatted = unFormatted.split(",");
        System.out.println(Arrays.toString(formatted));
    }
}
