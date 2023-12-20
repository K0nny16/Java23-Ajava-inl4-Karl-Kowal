import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
class WeatherAPI {
    static String apiCall(URL url){
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
        return city.getLatLon();
    }
    static String[] weatherJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        WeatherData weatherData = gson.fromJson(jsonString, WeatherData.class);                                                     //Parsar json stringen till objektet av klassen WeatherData.//Hämtar ut det första elementet i arrayen.
        return new String[]{"Location: "+weatherData.getName()+", "+weatherData.getCountry(),                                       //index 0 namn på stad
                weatherData.getWeatherDescription().substring(0,1).toUpperCase()+weatherData.getWeatherDescription().substring(1),  //index 1 beskrivning av väder.
                weatherData.getTemp() +"° Celsius.",                                                                                //Index2 Tempratur
                "Feels Like: "+ weatherData.getFeelsLike() +"° Celsius.",                                                           //Index3 Vilken tempratur det känns som.
                "Windspeed: "+weatherData.getWind()+" M/S.",                                                                        //Index4 Vindhastighet.
                "Humidity: "+weatherData.getHumidity()+"%",                                                                         //Index5 luftfuktighet.
                weatherData.getIcon()};                                                                                             //Index6 Icon för import. Ska alltid vara sist!
    }
}