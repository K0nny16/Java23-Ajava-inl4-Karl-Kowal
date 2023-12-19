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
        Gson gson = builder.create();
        City city = gson.fromJson(jsonString.substring(1,jsonString.length()-1),City.class);
        return new double[]{city.lat, city.lon};
    }
    static String[] weatherJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();                                                                      //Gör det lättare att se och tolka Json filen ifall den skrivs ut i consolen.
        Gson gson = builder.create();
        WeatherData weatherData = gson.fromJson(jsonString, WeatherData.class);                          //Parsar json stringen till objektet av klassen WeatherData.
        WeatherInfo weather = weatherData.weather[0];                                                   //Hämtar ut det första elementet i arrayen.
        return new String[]{weatherData.name,                                                           //index 0 namn på stad
                weatherData.weather[0].description.substring(0,1).toUpperCase()+weatherData.weather[0].description.substring(1), //index 1 beskrivning av väder.
                weatherData.main.temp +" degrees celsius",                                               //Index2 Tempratur
                "Feels like: "+ weatherData.main.feels_like +" degrees celsius",                         //Index3 Vilken tempratur det känns som.
                "Windspeed:"+weatherData.wind.speed+"M/S",                                               //Index4 Vindhastighet.
                weather.icon};                                                                           //Index5 Icon för import.

    }
}
class City{
    double lat;         //Latitud.
    double lon;         //Longitud.
}
class WeatherData {
    WeatherInfo[] weather;
    Temperatur main;          //Matchar med det i WeatherData som har samma namn (exempel).
    String name;             //Kan se denna som key / value ? (Objektet = key / datan i den = value).
    Wind wind;
}
class WeatherInfo {
    String description; //Beskrivning.
    String icon;        //Icon.
}
class Temperatur {
    double temp;        //Grader.
    double feels_like;  //Vad det känns som.
}
class Wind{
    double speed;      //Vind hastighet.
}
