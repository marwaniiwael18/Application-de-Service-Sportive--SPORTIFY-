package com.example.sportify;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceReservation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class AjouterReservationController {
    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;
    @FXML
    private Button button9;


    private int idTerrain;
    private ServiceReservation serviceReservation  = new ServiceReservation();


    public void setID_Terrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }


    public AjouterReservationController() {
        // Vous pouvez initialiser des choses ici si nécessaire
    }



    public void initialiserDonnees(int idReservation) {
        // Utilisez l'ID de la réservation pour charger les données et initialiser les champs de formulaire
    }


    @FXML
    public void initialize() {
        // Configurez ici les actions des boutons, si ce n'est pas déjà fait dans le FXML
        button1.setOnAction(this::handleReservationAction);
        button2.setOnAction(this::handleReservationAction);
        button3.setOnAction(this::handleReservationAction);
        button4.setOnAction(this::handleReservationAction);
        button5.setOnAction(this::handleReservationAction);
        button6.setOnAction(this::handleReservationAction);
        button7.setOnAction(this::handleReservationAction);
        button8.setOnAction(this::handleReservationAction);
        button9.setOnAction(this::handleReservationAction);
        // Répétez pour chaque bouton
    }
    private int reservationID;


    private void handleReservationAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        // Remplacer "id" par l'identifiant réel de l'utilisateur connecté
        int id = 1; // Cet ID doit venir de votre système de gestion d'utilisateurs

        // Initialize DateTimeFormatter before the conditions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime;

        if (clickedButton.equals(button1)) {
            dateTime = LocalDateTime.parse("02/03/2024 10:00:00", formatter);
        } else if (clickedButton.equals(button2)) {
            dateTime = LocalDateTime.parse("05/03/2024 11:00:00", formatter);
        } else if (clickedButton.equals(button3)) {
            dateTime = LocalDateTime.parse("07/03/2024 14:00:00", formatter);
        } else if (clickedButton.equals(button4)) {
            dateTime = LocalDateTime.parse("03/03/2024 17:00:00", formatter);
        } else if (clickedButton.equals(button5)) {
            dateTime = LocalDateTime.parse("05/03/2024 18:00:00", formatter);
        } else if (clickedButton.equals(button6)) {
            dateTime = LocalDateTime.parse("07/03/2024 19:00:00", formatter);
        } else if (clickedButton.equals(button7)) {
            dateTime = LocalDateTime.parse("03/03/2024 21:00:00", formatter);
        } else if (clickedButton.equals(button8)) {
            dateTime = LocalDateTime.parse("05/03/2024 22:00:00", formatter);
        } else if (clickedButton.equals(button9)) {
            dateTime = LocalDateTime.parse("07/03/2024 14:00:00", formatter);
        } else {
            // For other buttons, use the current date and time
            dateTime = LocalDateTime.now();
        }


        // La durée par défaut est d'une heure, mais cela peut être ajusté si nécessaire
        String duree = "1 heure"; // Par exemple, si vous avez besoin de la durée spécifique du bouton, utilisez clickedButton.getText();

        // Créez l'objet Reservation
        Reservation reservation = new Reservation(0, id, idTerrain, dateTime, duree);

        // Utilisez le service pour ajouter la réservation
        serviceReservation.ajouter(reservation);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "La réservation a été effectuée avec succès.", ButtonType.OK);
        alert.setTitle("Réservation effectuée");
               new Thread(() -> {
                    try {
                        send_SMS();
                    } catch (Exception e) {
                        // Gérer les exceptions ici
                    }
                }).start();
        alert.setHeaderText(null); // Vous pouvez mettre à null ou définir un texte d'en-tête
        alert.showAndWait();
        navigateToAfficherReservation();

    }

    private void navigateToAfficherReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/AfficherReservation.fxml"));
            Parent root = loader.load();

            button1.getScene().setRoot(root);

            AfficherReservationController controller = loader.getController();
            controller.loadReservations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   void send_SMS (){
        // Initialisation de la bibliothèque Twilio avec les informations de votre compte
        String ACCOUNT_SID = "AC82e2948146278b8bd4bbd33580b24dd7";
        String AUTH_TOKEN = "232dae99a78ff67c36e9a5d72796e690";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+21625162501,";
        String message = "Bonjour Mr(s) ,\n"
                + "Nous sommes ravis de vous informer qu'un reservation a été ajouté.\n "
                + "Veuillez contactez l'administration pour plus de details.\n "
                + "Merci de votre fidélité(e) et à bientôt chez Sportify.\n"
                + "Cordialement,\n"
                + "Sportify";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+17816607664"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }

   @FXML
   void logout(MouseEvent event) {
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
    void gotoprofile(MouseEvent event) {
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
    void gotoacceuil(MouseEvent event) {
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
}
