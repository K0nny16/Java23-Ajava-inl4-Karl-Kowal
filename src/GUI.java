import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

class GUI extends JFrame {
    static private String selectedUser;
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
        selectLocations(top);

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

            //Väder

            center.add(panel);
        }
    }
    void addUser(JPanel panel){
        JButton addUser = new JButton("Add User");
        addUser.addActionListener(e -> addMenu());
        panel.add(addUser);
    }
    void selectUser(JPanel panel){
        JComboBox<String> users = new JComboBox<>(Database.getNames());
        users.addActionListener(e -> selectedUser = (String) users.getSelectedItem());
        panel.add(users);
        panel.revalidate();
        panel.repaint();
    }
    void selectLocations(JPanel panel){
        JComboBox<String> locations = new JComboBox<>(Database.getLocations());
        panel.add(locations);
    }
    void addMenu() {
        String username = JOptionPane.showInputDialog("Enter your profile name:");
        String unFormattedCities = JOptionPane.showInputDialog("Which cities do you want the weather of? \n ex Malmö,Trelleborg,Svedala,Ystad");
        if ((username != null && !username.isEmpty()) && (unFormattedCities != null && !unFormattedCities.isEmpty())){
            String[] cities = unFormattedCities.split(",");
            Database.putRequest(username, cities);
        }
        else {
            JOptionPane.showMessageDialog(null,"You have to fill in Username and Cities!");
        }
    }
    static String getSelectedUser(){
        return selectedUser;
    }
}


