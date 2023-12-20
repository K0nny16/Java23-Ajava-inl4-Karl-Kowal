class City{
    private double lat;         //Latitud.
    private double lon;         //Longitud.
    double[] getLatLon(){
        return new double[]{lat,lon};
    }
}
class WeatherData {
    WeatherInfo[] weather;
    Temperature main;          //Matchar med det i WeatherData som har samma namn (exempel).
    String name;             //Kan se denna som key / value ? (Objektet = key / datan i den = value).
    Wind wind;
    Country sys;
    static class WeatherInfo {
        private String description; //Beskrivning.
        private String icon;        //Icon.
    }
    static class Temperature {
        private double temp;        //Grader.
        private double feels_like;  //Vad det känns som.
        private double humidity;
    }
    static class Wind{
        private double speed;      //Vind hastighet.
    }
    static class Country{
        private String country;
    }
    public String getWeatherDescription() {
        return weather[0].description;
    }
    public String getIcon() {
        return weather[0].icon;
    }
    public double getTemp() {
        return main.temp;
    }
    public double getHumidity() {
        return main.humidity;
    }
    public String getName() {
        return name;
    }
    public double getWind() {
        return wind.speed;
    }
    String getCountry(){
        return sys.country;
    }
    double getFeelsLike(){
        return main.feels_like;
    }
}

