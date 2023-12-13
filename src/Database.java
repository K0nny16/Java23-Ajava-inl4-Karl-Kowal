import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String baseURL = "https://api-och-firebase-default-rtdb.europe-west1.firebasedatabase.app/";
    static List<String[]> data;
    Database(){
        getRequest();
    }
    void getRequest(){
        try{
            URL url = new URL(baseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null )
                    response.append(line);
                reader.close();
                JSON(response);
            }
            else
                System.out.println("Error response code: "+responseCode);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void JSON (StringBuilder string) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(string));
        JSONArray jsonArray = (JSONArray) obj;

        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject) json;
            List<String> rowData = new ArrayList<>();

            for (Object key : jsonObject.keySet())
                rowData.add(jsonObject.get(key).toString());
            data.add(rowData.toArray(new String[0]));
        }
    }
    static String[] getNames(){
        return data.getFirst();
    }
}

