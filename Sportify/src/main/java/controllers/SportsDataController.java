package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SportsDataController {
    @FXML
    private ListView<String> sportsListView;

    @FXML
    private void fetchSportsData(ActionEvent event) {
        try {
            // Your API key obtained from TheSportsDB
            String apiKey = "3";

            // Construct the URL for the API request with the API key
            String urlString = "https://www.thesportsdb.com/api/v1/json/" + apiKey + "/all_sports.php";
            URL url = new URL(urlString);

            // Open a connection to the API URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Error: Failed to fetch data. Response code: " + responseCode);
                return;
            }

            // Read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray sportsArray = jsonResponse.getJSONArray("sports");

            // Clear existing items in the ListView
            sportsListView.getItems().clear();

            // Add sports to the ListView
            for (int i = 0; i < sportsArray.length(); i++) {
                JSONObject sportObject = sportsArray.getJSONObject(i);
                String sportName = sportObject.getString("strSport");
                sportsListView.getItems().add(sportName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
