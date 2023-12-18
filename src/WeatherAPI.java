import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WeatherAPI {
   static String getLL(URL url){
       try{
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
               return response.toString();
           }
           else
               System.out.println("Error response code: "+responseCode);
           connection.disconnect();
       }catch (Exception e){
           e.printStackTrace();
       }
       return "";
   }
    static double[] geoJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        City city = gson.fromJson(jsonString.substring(1,jsonString.length()-1), City.class);
        return new double[]{city.lat, city.lon};
    }
}
class City{
    String name;
    double lon;
    double lat;
}
