package controllers;

import entities.Team;
import entities.Utilisateurs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServiceUtilisateurs;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class AfficherEquipeController implements Initializable {

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnAfficher;

    @FXML
    private MenuButton menuSports;

    @FXML
    private Text tfListe;

    @FXML
    private TextField tfMembre10;

    @FXML
    private TextField tfMembre11;

    @FXML
    private TextField tfMembre3;

    @FXML
    private TextField tfMembre4;

    @FXML
    private TextField tfMembre5;

    @FXML
    private TextField tfMembre6;

    @FXML
    private TextField tfMembre7;

    @FXML
    private TextField tfMembre8;

    @FXML
    private TextField tfMembre9;

    @FXML
    private Text tfNom;

    @FXML
    private TextField tfNomEquipe;

    @FXML
    private TextField tfmembre1;

    @FXML
    private TextField tfmembre2;
    @FXML
    private Text membersText;
    @FXML
    private TextField existingEmailTextField;

    @FXML
    private VBox vbox1;
    @FXML
    private ImageView qrCodeImageView;

    @FXML
    private URL location;
    @FXML
    private List<TextField> memberTextFields = new ArrayList<>();


    private ServiceUtilisateurs serviceUtilisateurs = new ServiceUtilisateurs();
    private List<String> storedEmails = new ArrayList<>(); // List of stored emails


    public void initData(String teamName, String members) {
        tfNomEquipe.setText("Team Name: " + teamName);
        membersText.setText("Members: " + members);
    }


    @FXML
    private void callExistingUser(ActionEvent event) {
        String selectedEmail = "Achouri.Mariem@gmail.com"; // Replace with actual selected email
        try {
            Utilisateurs utilisateurs = serviceUtilisateurs.getUserByEmail(selectedEmail);
            if (utilisateurs != null) {
                // Populate text fields with user information
                tfmembre1.setText(utilisateurs.getEmail());
                tfNom.setText(utilisateurs.getNom());
                // Populate other fields as needed
            } else {
                showAlert("Utilisateur introuvable pour l'e-mail : " + selectedEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
    }

    @FXML
    void create(ActionEvent event) {
        String nomEquipe = tfNomEquipe.getText();
        String membre1 = tfmembre1.getText();
        String membre2 = tfmembre2.getText();
        String membre3 = tfMembre3.getText();
        String membre4 = tfMembre4.getText();
        String membre5 = tfMembre5.getText();
        String membre6 = tfMembre6.getText();
        String membre7 = tfMembre7.getText();
        String membre8 = tfMembre8.getText();
        String membre9 = tfMembre9.getText();
        String membre10 = tfMembre10.getText();
        String membre11 = tfMembre11.getText();
        if (nomEquipe.isEmpty() || membre1.isEmpty() || membre2.isEmpty()) {
            showAlert("Veuillez remplir le nom de l'équipe et au moins deux membres.");
            return;
        }


        Team equipe = new Team(nomEquipe, Arrays.asList(membre1, membre2, membre3, membre4));
        System.out.println("Equipe ajoutée: " + equipe.toString());
        displayEquipe(equipe, ((Node) event.getSource()).getScene().getWindow());

    }

    @FXML
    void afficher(ActionEvent event) throws IOException {
        String nomEquipe = tfNomEquipe.getText();
        String membre1 = tfmembre1.getText();
        String membre2 = tfmembre2.getText();

        if (nomEquipe.isEmpty() || membre1.isEmpty() || membre2.isEmpty()) {
            showAlert("Veuillez remplir le nom de l'équipe et au moins deux membres.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DisplayEquipe.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayEquipe(Team equipe, Window window) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DisplayEquipe.fxml"));
            Parent root = loader.load();

            DisplayEquipeController controller = loader.getController();
            controller.setData(equipe.getNomEquipe(), equipe.getMembres().toArray(new String[0]));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(window);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

// Populate storedEmails with data from your database or another source
        fetchStoredEmails();
        // Initialize memberTextFields list
        memberTextFields.addAll(Arrays.asList(tfmembre1, tfmembre2, tfMembre3, tfMembre4, tfMembre5,
                tfMembre6, tfMembre7, tfMembre8, tfMembre9, tfMembre10, tfMembre11));
    }

    private void fetchStoredEmails() {
        List<Utilisateurs> utilisateurs = new ArrayList<>(); // Create a new list to hold users
        // Manually add users to the list
       // utilisateurs.add(new Utilisateurs(1, "John", "Doe", "john@example.com", "password", "image1.jpg", "niveau1", "client", "123 Street", "Male"));
        //utilisateurs.add(new Utilisateurs(2, "Jane", "Doe", "jane@example.com", "password", "image2.jpg", "niveau2", "client", "456 Avenue", "Female"));
        //utilisateurs.add(new Utilisateurs(3, "Alice", "Smith", "alice@example.com", "password", "image3.jpg", "niveau3", "client", "789 Road", "Female"));

        // Add more users as needed

        // Add the emails of these users to the storedEmails list
        for (Utilisateurs utilisateur : utilisateurs) {
            storedEmails.add(utilisateur.getEmail());
        }
    }

/*
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
            String reservationDetails = "Détails de la réservation"; // Remplacez ceci par les détails de la réservation réels
            Image qrCodeImage = generateQRCode(reservationDetails, 200, 200);
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            // Gérez l'exception ici
        }
    }*/

    @FXML
    private void handleKeyReleased(KeyEvent event) {
        String enteredText = tfmembre1.getText().toLowerCase(); // Get the text entered by the user
        List<String> suggestions = new ArrayList<>();

        // Filter stored emails to find those that start with the entered text
        for (String storedEmail : storedEmails) {
            if (storedEmail.toLowerCase().startsWith(enteredText)) {
                suggestions.add(storedEmail);
            }
        }

        // Display suggestions (not implemented here)
        // For example, you can display suggestions in a drop-down menu or list

        // If there's only one suggestion and it starts with the entered text, auto-complete the text field
        if (suggestions.size() == 1 && suggestions.get(0).toLowerCase().startsWith(enteredText)) {
            String completion = suggestions.get(0).substring(enteredText.length());
            tfmembre1.setText(tfmembre1.getText() + completion);
            tfmembre1.end(); // Move the caret to the end of the text field
        }
    }
    @FXML
    private void selectExistingUser(ActionEvent event) {
        String selectedEmail = existingEmailTextField.getText();
        try {
            Utilisateurs utilisateurs = serviceUtilisateurs.getUserByEmail(selectedEmail);
            if (utilisateurs != null) {
                // Find the first empty text field and populate it with the email
                for (TextField textField : memberTextFields) {
                    if (textField.getText().isEmpty()) {
                        textField.setText(utilisateurs.getEmail());
                        textField.setUserData(utilisateurs); // Optionally store user data for later use
                        break;
                    }
                }
            } else {
                showAlert("Utilisateur introuvable pour l'e-mail : " + selectedEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
    }

}
