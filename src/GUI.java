import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
class GUI extends JFrame {
    GUI(){
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        JPanel placeHolder = new JPanel();
        placeHolder.setBackground(Color.decode("#FFA756"));
        top.setBackground(Color.decode("#949494"));
        top.setLayout(new FlowLayout());
        selectCity(top);
        add(top,BorderLayout.NORTH);
        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        add(placeHolder, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    void addContent(String[] data){
        Border gray = BorderFactory.createLineBorder(Color.GRAY);
        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.setBorder(gray);
        Color mainPanel = Color.decode("#FFA756");
        center.setBackground(mainPanel);
        try{
            BufferedImage image = ImageIO.read(new URL("https://openweathermap.org/img/wn/"+data[5]+"@2x.png"));
            if(image != null){
                Image resizedImg = image.getScaledInstance(120,120,Image.SCALE_SMOOTH);
                JLabel pic = new JLabel(new ImageIcon(resizedImg));
                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setBackground(mainPanel);
                for(int i = 0; i < data.length-1; i++){
                    JLabel text = new JLabel(data[i]);
                    text.setFont(new Font("Arial",Font.BOLD,16));
                    textPanel.add(text);
                }
                center.add(textPanel, BorderLayout.NORTH);
                center.add(pic,BorderLayout.CENTER);
                add(center,BorderLayout.CENTER);
                revalidate();
            }
            else
                System.out.println("Error");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void selectCity(JPanel panel){
        JButton addUser = new JButton("Pick city!");
        addUser.addActionListener(e -> addCity());
        addUser.setFocusable(false);
        panel.add(addUser);
    }
    void addCity(){
        String city = JOptionPane.showInputDialog("What city do you want the weather of? \n ex Malmö,Trelleborg,Svedala,Ystad");
        if (city != null && !city.isEmpty()){
            try{
                String response = WeatherAPI.apiCall(new URL("http://api.openweathermap.org/geo/1.0/direct?q="+city+",se&limit=1&appid=6e86d40c1ccec69010c71630afb27d8c"));
                double[] coords = WeatherAPI.geoJson(response);
                String weatherResponse = WeatherAPI.apiCall(new URL("https://api.openweathermap.org/data/2.5/weather?lat="+coords[0]+"&lon="+coords[1]+"&appid=6e86d40c1ccec69010c71630afb27d8c&units=metric&lang=sv"));
                String[] weatherData = WeatherAPI.weatherJson(weatherResponse);
                System.out.println(Arrays.toString(weatherData));
                addContent(weatherData);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else
            JOptionPane.showMessageDialog(null,"Error!");
    }
}


