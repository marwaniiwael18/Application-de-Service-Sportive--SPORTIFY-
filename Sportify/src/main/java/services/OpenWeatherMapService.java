package services;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OpenWeatherMapService {
    private static final String API_KEY = "63b8339f1895a016c3ee305c6b1762f9";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";





    public static JSONObject getWeatherDataForCity(String city) throws IOException {
        String apiUrl = String.format(API_URL, city, API_KEY);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            JSONObject weatherData = new JSONObject(response.toString());

            // Ajouter les détails météorologiques supplémentaires au JSON
            JSONObject weatherDetails = new JSONObject();
            JSONObject main = weatherData.getJSONObject("main");
            weatherDetails.put("temperature", main.getDouble("temp") - 273.15); // Température en Celsius
            weatherDetails.put("humidity", main.getDouble("humidity")); // Humidité en pourcentage
            weatherDetails.put("pressure", main.getDouble("pressure")); // Pression atmosphérique en hPa
            weatherDetails.put("visibility", weatherData.getInt("visibility")); // Visibilité en mètres

            JSONObject wind = weatherData.getJSONObject("wind");
            weatherDetails.put("wind_speed", wind.getDouble("speed")); // Vitesse du vent en m/s
            weatherDetails.put("wind_direction", wind.getDouble("deg")); // Direction du vent en degrés

            JSONObject clouds = weatherData.getJSONObject("clouds");
            weatherDetails.put("cloudiness", clouds.getInt("all")); // Nuages en pourcentage

            // Ajouter les détails météorologiques supplémentaires au JSON principal
            weatherData.put("weather_details", weatherDetails);

            return weatherData;
        } else {
            throw new IOException("Erreur lors de la récupération des données météorologiques. Code de réponse : " + responseCode);
        }
    }
}