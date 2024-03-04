package controllers;

import entities.Arbitre;
import entities.Equipe;
import entities.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceArbitreA;
import services.ServiceMatchA;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierMatch implements Initializable {
    ServiceArbitreA serviceArbitre=new ServiceArbitreA();
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker dateDatePicker;
    @FXML
    private TextField heureTextField;
    @FXML
    private TextField minTextField;
    @FXML
    private ComboBox<Equipe> equipe1ComboBox;
    @FXML
    private ComboBox<Equipe> equipe2ComboBox;
    @FXML
    private ComboBox<Arbitre> arbitreComboBox;

    private ServiceMatchA serviceMatch  = new ServiceMatchA();

    private Match selectedMatch;
    @FXML
    private Button addButton;



    public void initData(Match match) {
        selectedMatch = match;

        // Utilisez les données du match pour initialiser les champs du formulaire de modification
        nomTextField.setText(selectedMatch.getNom());
        typeTextField.setText(selectedMatch.getType());
        descriptionTextField.setText(selectedMatch.getDescription());
        dateDatePicker.setValue(selectedMatch.getDate().toLocalDate());
        heureTextField.setText(selectedMatch.getHeure().toString().substring(0,2));
        minTextField.setText(selectedMatch.getHeure().toString().substring(3,5));
        equipe1ComboBox.setValue(selectedMatch.getEquipe1());
        equipe2ComboBox.setValue(selectedMatch.getEquipe2());
        arbitreComboBox.setValue(selectedMatch.getArbitre());
    }

    @FXML
    private void handleModifierButtonAction(ActionEvent event) {

        String nom = nomTextField.getText();
        if (nom.isEmpty() || nom.length() < 3 || nom.length() > 25) {
            showAlert("Le nom doit contenir entre 3 et 25 caractères !");
            return;
        }

        String type = typeTextField.getText();
        if (type == null || type.isEmpty()) {
            showAlert("Veuillez sélectionner un type !");
            return;
        }

        String description = descriptionTextField.getText();
        if (description.isEmpty() || description.length() < 5 || description.length() > 50) {
            showAlert("La description doit contenir entre 5 et 50 caractères !");
            return;
        }

        LocalDate localDate = dateDatePicker.getValue();
        if (localDate == null) {
            showAlert("Veuillez sélectionner une date !");
            return;
        }

        if (localDate.isBefore(LocalDate.now())) {
            showAlert("Veuillez sélectionner une date future !");
            return;
        }

        if (localDate.isAfter(LocalDate.now().plusDays(15))) {
            showAlert("La date sélectionnée ne peut pas être plus de 15 jours dans le futur !");
            return;
        }

        int heures;
        try {
            heures = Integer.parseInt(heureTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer une valeur numérique pour les heures !");
            return;
        }

        if (heures < 0 || heures > 23) {
            showAlert("Veuillez entrer une valeur entre 0 et 23 pour les heures !");
            return;
        }

        int minutes;
        try {
            minutes = Integer.parseInt(minTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Veuillez entrer une valeur numérique pour les minutes !");
            return;
        }

        if (minutes < 0 || minutes > 59) {
            showAlert("Veuillez entrer une valeur entre 0 et 59 pour les minutes !");
            return;
        }

        String heureString = heureTextField.getText() + ":" + minTextField.getText() + ":00";
        Time heure = Time.valueOf(heureString);

        Equipe equipe1 = equipe1ComboBox.getValue();
        if (equipe1 == null) {
            showAlert("Veuillez sélectionner une équipe 1 !");
            return;
        }

        Equipe equipe2 = equipe2ComboBox.getValue();
        if (equipe2 == null) {
            showAlert("Veuillez sélectionner une équipe 2 !");
            return;
        }

        if (equipe1.equals(equipe2)) {
            showAlert("Les équipes sélectionnées ne peuvent pas être les mêmes !");
            return;
        }

        Arbitre arbitre = arbitreComboBox.getValue();
        if (arbitre == null) {
            showAlert("Veuillez sélectionner un arbitre !");
            return;
        }

        Match modifiedMatch = new Match();
        modifiedMatch.setID_Matc(selectedMatch.getID_Matc());
        modifiedMatch.setNom(nom);
        modifiedMatch.setType(type);
        modifiedMatch.setDescription(description);
        modifiedMatch.setDate(Date.valueOf(localDate));
        modifiedMatch.setHeure(heure);
        modifiedMatch.setEquipe1(equipe1);
        modifiedMatch.setEquipe2(equipe2);
        modifiedMatch.setArbitre(arbitre);

        try {
            serviceMatch.modifier(modifiedMatch);
            System.out.println("Match modifié : " + modifiedMatch);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadEquipes() {
        try {
            ObservableList<Equipe> equipes = FXCollections.observableArrayList(serviceMatch.getAllEquipes());
            equipe1ComboBox.setItems(equipes);
            equipe2ComboBox.setItems(equipes);
        } catch (SQLException e) {
            showAlert("Erreur lors du chargement des équipes : " + e.getMessage());
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadArbitres() {
        try {
            ObservableList<Arbitre> arbitres = FXCollections.observableArrayList(serviceArbitre.getAllArbitres());
            arbitreComboBox.setItems(arbitres);

        } catch (SQLException e) {
            showAlert("Erreur lors du chargement des arbitres : " + e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadEquipes();
        loadArbitres();
    }
}
