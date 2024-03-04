package com.example.sportify;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.ServiceReservation;
import services.ServiceTerrain;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Rating;
import services.ServiceReservation;

public class AdminReservationController {
    private final ServiceReservation SR  = new ServiceReservation();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Reservation> listReservation;

    @FXML
    private Button modres;
    @FXML
    private Button modtr1;

    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TextField nomm;

    @FXML
    private Button supres;

    @FXML
    private TextField typet1;


    @FXML
    private Button Done;

    @FXML
    private Rating Rate;

    @FXML
    void modtr(ActionEvent event) {
        Reservation selectedReservation = listReservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                // Supprimez la réservation sélectionnée
                SR.supprimer(selectedReservation.getID_reservation());


                // Chargez la vue ReservationController pour ajouter une nouvelle réservation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/Reservation.fxml"));
                Parent root = loader.load();

                // Si vous avez besoin de passer des données au nouveau contrôleur, faites-le ici
                // ReservationController controller = loader.getController();

                Stage stage = (Stage) modres.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                // Affichez une alerte d'erreur si la suppression ou le chargement de la vue échoue
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Échec de l'opération");
                errorAlert.setContentText("Une erreur est survenue : " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            // Affichez une alerte si aucune réservation n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    void suptr(ActionEvent event) {
        Reservation selectedReservation = listReservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            ServiceReservation SR  = new ServiceReservation();            try {
                // Call the method to delete the Reservation, assuming there is a method like deleteReservation
                SR.supprimer(selectedReservation.getID_reservation()); // replace getID_Reservation() with your actual method name for getting ID

                // Remove the selected item from the ListView
                listReservation.getItems().remove(selectedReservation);

                // Clear the selection
                listReservation.getSelectionModel().clearSelection();

                // Clear the TextField if needed
                nomm.clear();

                // Show confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Reservation has been successfully deleted.");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Show error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to delete Reservation");
                alert.setContentText("An error occurred: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show warning alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a Reservation to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        loadReservations(); // Chargez les réservations dès l'initialisation

        assert listReservation != null : "fx:id=\"listReservation\" was not injected: check your FXML file 'AdminReservation.fxml'.";
        assert modres != null : "fx:id=\"modres\" was not injected: check your FXML file 'AdminReservation.fxml'.";
        assert nomm != null : "fx:id=\"nomm\" was not injected: check your FXML file 'AdminReservation.fxml'.";
        assert supres != null : "fx:id=\"supres\" was not injected: check your FXML file 'AdminReservation.fxml'.";
        listReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Affichez la Date_Heure dans le TextField `nomm`
                nomm.setText(newValue.getDate_Heure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            }
        });
    }
    public void loadReservations() {
        Set<Reservation> reservations = SR.afficher(); // Récupère les réservations
        ObservableList<Reservation> reservationItems = FXCollections.observableArrayList(reservations);

        listReservation.setItems(reservationItems);
        listReservation.setCellFactory(param -> new ListCell<Reservation>() {
            @Override
            protected void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Date/Heure: %s, Durée: %s",
                            item.getDate_Heure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                            item.getDuree()));
                }
            }
        });
    }

}
