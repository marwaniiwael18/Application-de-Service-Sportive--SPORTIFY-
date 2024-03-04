package com.example.sportify;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import entities.Terrain;
import entities.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.Rating;
import services.ServiceReservation;
import services.ServiceTerrain;

public class AfficherReservationController {
    private final ServiceReservation SR  = new ServiceReservation();

    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;
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
    void Submit(ActionEvent event) {
        double ratingValue = Rate.getRating();
        System.out.println("Rating submitted: " + ratingValue);
        // Show confirmation alert
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Réservation Confirmée");
        confirmationAlert.setContentText("Votre réservation a été effectuée avec succès. Bon courage pour votre match!");
        confirmationAlert.showAndWait();
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

        assert listReservation != null : "fx:id=\"listReservation\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        assert modres != null : "fx:id=\"modres\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        assert modtr1 != null : "fx:id=\"modtr1\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        assert nomm != null : "fx:id=\"nomm\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        assert supres != null : "fx:id=\"supres\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        assert typet1 != null : "fx:id=\"typet1\" was not injected: check your FXML file 'AfficherReservation.fxml'.";
        listReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Affichez la Date_Heure dans le TextField `nomm`
                nomm.setText(newValue.getDate_Heure().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                showQRCode();
            }
        });
    }

    public Image generateQRCode(String text, int width, int height) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        // Return the QR Code as a JavaFX image
        return new Image(new ByteArrayInputStream(pngData));
    }
    public void showQRCode() {
        try {
            String phoneNumber = "tel:+21625162501"; // Remplacez ceci par les détails de la réservation réels
            Image qrCodeImage = generateQRCode(phoneNumber, 200, 200);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            // Gérez l'exception ici
        }
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
    void logout(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("authentification-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoprofile(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-profile-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoacceuil(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("UserAcceuilView.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotoreservation(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Reservation.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoTerrain(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Terrain.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}


