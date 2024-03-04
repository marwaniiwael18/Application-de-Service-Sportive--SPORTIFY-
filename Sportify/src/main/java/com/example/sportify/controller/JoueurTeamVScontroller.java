package com.example.sportify.controller;

import entities.Competition;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;
import services.CompetitionServiceX;
import services.ServiceEquipeX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class JoueurTeamVScontroller implements Initializable {
    @FXML
    private VBox PlayersBox;
    @FXML
    private VBox PlayersBoxE2;

    @FXML
    private TextField nomPlayer;

    @FXML
    private VBox detailsVbox;
    @FXML
    private TextField nomEquipe1;

    @FXML
    private TextField nomEquipe2;
    private Competition Competition;

    private int matchId;


    CompetitionServiceX competservice = new CompetitionServiceX();

    ServiceEquipeX equipeservice = new ServiceEquipeX();

    private Competition compet;
    private Utilisateur loggedUser;

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyBa8l76bfCyfPiwJeFLJCMFhRb_lOSS58M";



    @FXML
    private Button weatherButton;

    @FXML
    private Button localisationButton;

    String city = "London"; // Change to your desired city
    String country = "GB"; // Change to your desired country code
    String apiKey = "YOUR_API_KEY"; // Replace with your OpenWeatherMap API key

    String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey;
    String competitionDate ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No need to call loadAll() here as it will be called after matchId is set
    }






    public void initData(Utilisateur User) {
        this.loggedUser = User;
    }

    public void setMatchDetails(int matchId) throws SQLException {
        boolean userInE1 = false;
        boolean userInE2 = false;
        this.matchId = matchId;
        this.compet = competservice.getById(matchId);
        if (compet != null) {
            loadAll();
            if ((compet.getEquipe1() != null) || (compet.getEquipe2() != null)) {
                nomEquipe1.setText(compet.getEquipe1().getNom());
                List<Utilisateur> membresEquipe1 = equipeservice.getEquipeMembres(compet.getEquipe1());
                for (Utilisateur membre : membresEquipe1) {

                    TextField textField = new TextField(membre.getNom());
                    textField.setEditable(false); // Set the text field as read-only
                    textField.setStyle("-fx-text-fill: white; " +
                            "-fx-background-color: #9e1010; " +
                            "-fx-border-color: white; " +
                            "-fx-font-size: 24px; " +
                            "-fx-alignment: center;");
                    PlayersBox.getChildren().add(textField);
                }
                Button joinButton = new Button("JOIN");
                joinButton.setOnAction(this::joinButtonClicked);
                joinButton.setStyle("-fx-background-color: " +
                        "#090a0c," +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                        "linear-gradient(#20262b, #191d22)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5;" +
                        "-fx-background-insets: 0,1,2,0;" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: Arial;" +
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 10 20 10 20;");
                PlayersBox.getChildren().add(joinButton);
                System.out.println();
                System.out.println("Membre equipe");

                //--- EQUIPE 2
                nomEquipe2.setText(compet.getEquipe2().getNom());
                List<Utilisateur> membresEquipe2 = equipeservice.getEquipeMembres(compet.getEquipe2());
                for (Utilisateur membre : membresEquipe2) {


                    TextField textField = new TextField(membre.getNom());
                    textField.setEditable(false); // Set the text field as read-only
                    textField.setStyle("-fx-text-fill: white; " +
                            "-fx-background-color: #9e1010; " +
                            "-fx-border-color: white; " +
                            "-fx-font-size: 24px; " +
                            "-fx-alignment: center;");
                    PlayersBoxE2.getChildren().add(textField);
                }

                Button joinButtonE2 = new Button("JOIN");
                joinButtonE2.setOnAction(this::joinButtonE2Clicked);
                joinButtonE2.setStyle("-fx-background-color: " +
                        "#090a0c," +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                        "linear-gradient(#20262b, #191d22)," +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                        "-fx-background-radius: 5,4,3,5;" +
                        "-fx-background-insets: 0,1,2,0;" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                        "-fx-font-family: Arial;" +
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                        "-fx-font-size: 12px;" +
                        "-fx-padding: 10 20 10 20;");
                PlayersBoxE2.getChildren().add(joinButtonE2);

            } else {
                System.out.println("les 2 equipes not found ");
            }
        } else {
            System.out.println("Competition not found for matchId: " + matchId);
            // Handle the case where competition is null
        }
    }

    @FXML
    private void joinButtonClicked(ActionEvent event) {
        try {
            equipeservice.ajouterMembre(compet.getEquipe1(), loggedUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            reloadMembersE1List();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void fetchWeather(MouseEvent event) {
        String city = "Tunis"; // Change to your desired city
        String apiKey = "3a08efe2a3814f50bdf123437240303"; // Replace with your WeatherAPI key
        competitionDate= "2024-03-18" ;


        String urlString = "https://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=" + city + "&dt=" + competitionDate;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject forecast = jsonResponse.getJSONObject("forecast");
            JSONObject forecastDay = forecast.getJSONArray("forecastday").getJSONObject(0); // Assuming only one forecast for the day
            JSONObject day = forecastDay.getJSONObject("day");
            double temperatureCelsius = day.getDouble("avgtemp_c");

            // Display weather information in an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Weather Information");
            alert.setHeaderText(null);
            alert.setContentText("Temperature on 18th March 2024 in Tunis: " + String.format("%.2f", temperatureCelsius) + "°C");

            alert.showAndWait();

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showTerrainLocation(MouseEvent event) {
        String terrainLocation = "36.89587754985445,10.193551893128049"; // Assuming the getLocation() method returns the location as "latitude,longitude"

        // Construct the URL for displaying the map
        String mapUrl = "https://www.google.com/maps/embed/v1/view?key=" + GOOGLE_MAPS_API_KEY + "&center=" + terrainLocation + "&zoom=15";

        // Create a WebView to display the map
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Load the HTML content with the embedded Google Maps iframe
        String htmlContent = "<html><iframe width=800 height=650 style=border:0 loading=lazy allowfullscreen src=https://www.google.com/maps/embed/v1/place?q=place_id:ChIJO6D7WQvL4hIRu3dDvh-FGaE&key=AIzaSyBa8l76bfCyfPiwJeFLJCMFhRb_lOSS58M></iframe></html>"  ;
        webEngine.loadContent(htmlContent);

        // Create a new stage to display the map
        Stage mapStage = new Stage();
        mapStage.setTitle("Terrain Localisation");
        mapStage.setScene(new Scene(webView, 800, 600));
        mapStage.show();
    }

    @FXML
    private void joinButtonE2Clicked(ActionEvent event) {
        try {
            equipeservice.ajouterMembre(compet.getEquipe2(), loggedUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            reloadMembersE2List();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void QuitButtonClicked(ActionEvent event) {
        try {
            equipeservice.supprimerMembre(compet.getEquipe2(), loggedUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void loadAll() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/itemmatch.fxml"));
            HBox hboxitems = fxmlLoader.load();
            Itemmatch items = fxmlLoader.getController();
            items.DisableButton(false);
            hboxitems.setStyle("-fx-background-color: white;");
            // Iterate through all children of the VBox and set their text color
            for (javafx.scene.Node child : hboxitems.getChildren()) {
                if (child instanceof Label) {
                    ((Label) child).setStyle("-fx-text-fill: black;"); // Change text color to black
                }
            }
            if (compet != null) {
                items.SetText(compet.getID_competiton(), compet.getNom(), compet.getDate(), compet.getHeure(), compet.getTerrain().getNomTerrain());
                // Remove the join button
                detailsVbox.getChildren().add(hboxitems);
            } else {
                System.out.println("Cannot load details for null competition.");
                // Handle the case where competition is null
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void reloadMembersE1List() throws SQLException {

        // Clear the current list of members
        PlayersBox.getChildren().clear();

        // Get the updated list of team members
        List<Utilisateur> membresEquipe1 = equipeservice.getEquipeMembres(compet.getEquipe1());

        // Populate the VBox with updated list of members
        for (Utilisateur membre : membresEquipe1) {
            TextField textField = new TextField(membre.getNom());
            // Apply styles and settings to the TextField
            textField.setStyle("-fx-text-fill: white; -fx-background-color: #9e1010; -fx-border-color: white; -fx-font-size: 24px; -fx-alignment: center;");
            textField.setEditable(false); // Set the text field as read-only
            PlayersBox.getChildren().add(textField);
        }

        // Add the JOIN button below the list of members
        Button QuitButton = new Button("QUITTER");
        QuitButton.setOnAction(this::QuitButtonClicked);
        QuitButton.setStyle("-fx-background-color: " +
                "#090a0c," +
                "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                "linear-gradient(#20262b, #191d22)," +
                "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 5,4,3,5;" +
                "-fx-background-insets: 0,1,2,0;" +
                "-fx-text-fill: white;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                "-fx-font-family: Arial;" +
                "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 20 10 20;");
        PlayersBox.getChildren().add(QuitButton);
    }

    private void reloadMembersE2List() throws SQLException {

        // Clear the current list of members
        PlayersBoxE2.getChildren().clear();

        // Get the updated list of team members
        List<Utilisateur> membresEquipe2 = equipeservice.getEquipeMembres(compet.getEquipe2());

        // Populate the VBox with updated list of members
        for (Utilisateur membre : membresEquipe2) {
            TextField textField = new TextField(membre.getNom());
            // Apply styles and settings to the TextField
            textField.setStyle("-fx-text-fill: white; -fx-background-color: #9e1010; -fx-border-color: white; -fx-font-size: 24px; -fx-alignment: center;");
            textField.setEditable(false); // Set the text field as read-only
            PlayersBoxE2.getChildren().add(textField);
        }

        // Add the JOIN button below the list of members
        Button QuitButton = new Button("QUITTER");
        QuitButton.setOnAction(this::QuitButtonClicked);
        QuitButton.setStyle("-fx-background-color: " +
                "#090a0c," +
                "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%)," +
                "linear-gradient(#20262b, #191d22)," +
                "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));" +
                "-fx-background-radius: 5,4,3,5;" +
                "-fx-background-insets: 0,1,2,0;" +
                "-fx-text-fill: white;" +
                "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );" +
                "-fx-font-family: Arial;" +
                "-fx-text-fill: linear-gradient(white, #d0d0d0);" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 20 10 20;");
        PlayersBoxE2.getChildren().add(QuitButton);
    }

    boolean searchUser(String nom) {
        return this.loggedUser.getNom() == nom;
    }
}