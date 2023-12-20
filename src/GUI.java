import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
class GUI extends JFrame {
    GUI(){
        ImageIcon img = new ImageIcon("programIcon.jpg");
        setIconImage(img.getImage());
        setLayout(new BorderLayout());

        JPanel center = new JPanel();
        center.setBackground(Color.decode("#FFA756"));
        center.setLayout(new GridLayout());
        center.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel top = new JPanel();
        top.setBackground(Color.decode("#949494"));
        top.setLayout(new FlowLayout());

        selectCity(top,center);

        add(top,BorderLayout.NORTH);
        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        add(center, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void addContent(String[] weatherData,JPanel center) {
        center.removeAll();
        center.setLayout(new GridBagLayout());
        Color mainPanel = Color.decode("#FFA756");
        center.setBackground(mainPanel);
        try {
            BufferedImage image = ImageIO.read(new URL("https://openweathermap.org/img/wn/" + weatherData[weatherData.length-1] + "@2x.png"));
            if (image != null) {
                GridBagConstraints gbc = new GridBagConstraints();
                Image resizedImg = image.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                JLabel pic = new JLabel(new ImageIcon(resizedImg));
                gbc.gridx = 0;                                              //Först kolumn.
                gbc.gridy = 0;                                              //Först raden (allt detta är för bilden.)
                gbc.gridheight = GridBagConstraints.REMAINDER;              //REMAINDER = fyller alla lediga celler.
                gbc.insets = new Insets(0,0,0,5);                           //Lägger till padding 5px på alla sidor.
                center.add(pic, gbc);
                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setBackground(mainPanel);
                for (int i = 0; i < weatherData.length - 1; i++) {
                    JLabel text = new JLabel(weatherData[i]);
                    text.setFont(new Font("Arial", Font.BOLD, 16));
                    textPanel.add(text);
                }
                gbc.gridx = 1;                                              //Vilken kolumn i detta fallet den höger om.
                gbc.gridy = 0;                                              //Samma rad.
                gbc.gridheight = 1;                                         //Hur många grids den för ockupera
                center.add(textPanel, gbc);
                add(center, BorderLayout.CENTER);
                revalidate();
                repaint();
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void selectCity(JPanel panel,JPanel center){
        JButton addUser = new JButton("Pick city!");
        addUser.addActionListener(e -> addCity(center));
        addUser.setFocusable(false);
        panel.add(addUser);
    }
    void addCity(JPanel center){
        String city = JOptionPane.showInputDialog("What city do you want the weather of? \n ex Malmö,Trelleborg,Svedala,Ystad");
        if (city != null && !city.isEmpty()){
            try{
                String response = WeatherAPI.apiCall(new URL("http://api.openweathermap.org/geo/1.0/direct?q="+city+"&limit=1&appid=6e86d40c1ccec69010c71630afb27d8c"));
                double[] coords = WeatherAPI.geoJson(response);
                String weatherResponse = WeatherAPI.apiCall(new URL("https://api.openweathermap.org/data/2.5/weather?lat="+coords[0]+"&lon="+coords[1]+"&appid=6e86d40c1ccec69010c71630afb27d8c&units=metric&lang=en"));
                String[] weatherData = WeatherAPI.weatherJson(weatherResponse);
                addContent(weatherData,center);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
            JOptionPane.showMessageDialog(null,"Error!");
    }
}


