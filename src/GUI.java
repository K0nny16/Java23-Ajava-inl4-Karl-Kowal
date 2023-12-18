import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

class GUI extends JFrame {
    static private String selectedUser;
    static private String selectLocations;
    GUI(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        JPanel center = new JPanel();
        Border gray = BorderFactory.createLineBorder(Color.GRAY);
        center.setLayout(new BorderLayout());

        selectCity(top);
        addContent(gray,center);

        add(top,BorderLayout.NORTH);
        add(center,BorderLayout.CENTER);
        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);
        setVisible(true);
    }
    void addContent(Border border,JPanel center){
       JPanel panel = new JPanel();
       panel.setBorder(border);

       //Väder

       center.add(panel);
    }
    void selectCity(JPanel panel){
        JButton addUser = new JButton("Pick city!");
        addUser.addActionListener(e -> {
            try {
                addMenu();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(addUser);
    }
    void addMenu() throws MalformedURLException {
        String city = JOptionPane.showInputDialog("What city do you want the weather of? \n ex Malmö,Trelleborg,Svedala,Ystad");
        if (city != null && !city.isEmpty()){
            String response = WeatherAPI.getLL(new URL("http://api.openweathermap.org/geo/1.0/direct?q="+city+",se&limit=1&appid=6e86d40c1ccec69010c71630afb27d8c"));
            double[] coords = WeatherAPI.geoJson(response);
            String weatherResponse = WeatherAPI.getLL(new URL("https://api.openweathermap.org/data/3.0/onecall?lat="+coords[0]+"&lon="+coords[1]+"&exclude=&appid=6e86d40c1ccec69010c71630afb27d8c"));
        }
        else
            JOptionPane.showMessageDialog(null,"Error!");
    }
}


