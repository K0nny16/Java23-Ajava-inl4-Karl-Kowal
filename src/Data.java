class City{
    private double lat;         //Latitud.
    private double lon;         //Longitud.
    double[] getLatLon(){
        return new double[]{lat,lon};
    }
}
class WeatherData {
    WeatherInfo[] weather;
    Temperatur main;          //Matchar med det i WeatherData som har samma namn (exempel).
    String name;             //Kan se denna som key / value ? (Objektet = key / datan i den = value).
    Wind wind;
    Country sys;
}
class WeatherInfo {
    String description; //Beskrivning.
    String icon;        //Icon.
}
class Temperatur {
    double temp;        //Grader.
    double feels_like;  //Vad det känns som.
    double humidity;
}
class Wind{
    double speed;      //Vind hastighet.
}
class Country{
    String country;
}
