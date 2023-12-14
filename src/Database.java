import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Database {
    private static final String baseURL = "https://api-och-firebase-default-rtdb.europe-west1.firebasedatabase.app/";
    private static List<String[]> data = new ArrayList<>();
    private static HashMap<String,String[]> hashData = new HashMap<>();

    Database(){
        getRequest();
    }
    void getRequest() {
        try {
            URL url = new URL(baseURL + ".json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    response.append(line);
                reader.close();
                JSON(response);
            } else
                System.out.println("Error response code: " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void putRequest(String user,String[] cities){
        try{
            URL url = new URL(baseURL+".json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            Map<String,Object> dataMap = new HashMap<>();                                   //Gör om hashmapen till en map eftersom att JSONObeject kan ta map men inte hashmap. Eller den kan men får att det kan ge compilerings fel.
            dataMap.put(user,cities);
            JSONObject jsonData = new JSONObject(dataMap);

            try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                os.write(jsonData.toJSONString().getBytes());
                os.flush();                                                             //Ser till att alla data är skriven innan streamen stängs.

                int responeseCode = connection.getResponseCode();
                if(responeseCode == HttpURLConnection.HTTP_OK)
                    System.out.println("Data pushed to Firebase.");                     //Kollar så att min push gick igenom.
                else
                    System.out.println("Error code: "+responeseCode);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void JSON(StringBuilder string) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(string));              //Gör stringen från StringBuilder till ett objekt.
        JSONObject jsonObject = (JSONObject) obj;                       //Gör om objektet till ett json objekt.
        hashData.clear();                                               //Tömmer hashmap. Detta kan vara ineffektivt men gör så att det så kan ha bättre koll på ordningen av mappen.
        for (Object key : jsonObject.keySet()) {                        //Loopar igen keys i json objektet.
           String user = key.toString();
           JSONArray citiesArray = (JSONArray) jsonObject.get(key);     //Lägger alla städer som är associerade med den nyckel i en JSONArray.
           String[] cities = new String[citiesArray.size()];            //Skapar en array cities som har samma längd som json arrayen.
           for (int i = 0; i < citiesArray.size(); i++)
              cities[i] = citiesArray.get(i).toString();                //Loopar sedan igenom och lägger till varje objekt.(Egentligen strängar).
           hashData.put(user, cities);                                  //Sedan sätter vi user som key och cities som valuen.
           data.add(new String[]{user, Arrays.toString(cities)});       //Backup lägger till använder och citiesen i en array där jag har använderen på index 0 följt av cities.
        }
    }
    static String[] getNames(){
        return hashData.keySet().toArray(new String[0]);                //Tar ut alla keys fårn hashmappen.
    }
    static String[] getLocations() {
        String[] values = new String[0];
        for(String key : hashData.keySet()){
            if(key.equalsIgnoreCase(GUI.getSelectedUser())){            //Tar stringen fån profilen som blev klickad jämför med samtiliga nycklar och tar sedan ut värdet av den nyckel när den hittar rätt.
                values = hashData.get(key);
            }
        }
        return values;
    }
}

