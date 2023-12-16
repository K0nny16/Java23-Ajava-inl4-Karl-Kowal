import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WeatherAPI {
   static void APICall(String city){
       String key = "6e86d40c1ccec69010c71630afb27d8c";
       try{
           URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q="+city+"&limit=5&appid="+key);
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           connection.setRequestMethod("GET");
           int responseCode = connection.getResponseCode();
           if(responseCode == HttpURLConnection.HTTP_OK){
               BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
               String line;
               StringBuilder response = new StringBuilder();
               while((line = reader.readLine()) != null)
                   response.append(line);
               reader.close();





           }
           else
               System.out.println("Error response code: "+responseCode);
           connection.disconnect();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}
