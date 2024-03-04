package controllers;

import entities.Arbitre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceArbitreA;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AfficherArbitreController implements Initializable {



    @FXML
    private TextField idField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    private Arbitre selectedArbitre;


    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Arbitre> TableView;

    @FXML
    private TableColumn<Arbitre, Integer> ageColumn;

    @FXML
    private TableColumn<Arbitre, String> nomColumn;

    @FXML
    private TableColumn<Arbitre, String> prenomColumn;

    @FXML
    private TableColumn<Arbitre, String> emailColumn;

    @FXML
    private TableColumn<Arbitre, String> phoneColumn;
    ServiceArbitreA s = new ServiceArbitreA();

    @FXML
    private void insertButton() {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        String email = emailField.getText();
        if (!email.matches(emailPattern)) {
            showAlert("Format d'email invalide !");
            return;
        }

        String nom = nomField.getText();
        if (nom.length() > 25||nom.length()<3) {
            showAlert("Le nom doit être entre 18 et 25 caractères !");
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            if (age < 18 || age > 45) {
                showAlert("L'âge doit être entre 18 et 45 !");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("L'âge doit être un nombre !");
            return;
        }


        String phonePattern = "\\d{8}";
        String phone = phoneField.getText();
        if (!phone.matches(phonePattern)) {
            showAlert("Le numéro de téléphone doit contenir 8 chiffres !");
            return;
        }
        try {
            s.ajouter(new Arbitre(Integer.parseInt(ageField.getText()), nomField.getText(), prenomField.getText(), emailField.getText(), phoneField.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        showArbitre();
    }



    @FXML
    private void updateButton() {
        Arbitre selectedArbitre = TableView.getSelectionModel().getSelectedItem();
        if (selectedArbitre != null) {
            int id = selectedArbitre.getId_arbitre();
            System.out.println("L'ID est " + id);

            String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
            String email = emailField.getText();
            if (!email.matches(emailPattern)) {

                showAlert("Format d'email invalide !");
                return;
            }

            String nom = nomField.getText();
            if (nom.length() > 25||nom.length()<3) {
                showAlert("Le nom doit être entre 18 et 25 caractères !");
                return;
            }
            String prenom = prenomField.getText();
            if (prenom.length() > 25||prenom.length()<3) {
                showAlert("Le prenom doit être entre 18 et 25 caractères !");
                return;
            }


            try {
                int age = Integer.parseInt(ageField.getText());
                if (age < 18 || age > 45) {
                    showAlert("L'âge doit être entre 18 et 45 !");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("L'âge doit être un nombre !");
                return;
            }


            String phonePattern = "\\d{8}";
            String phone = phoneField.getText();
            if (!phone.matches(phonePattern)) {
                showAlert("Le numéro de téléphone doit contenir 8 chiffres !");
                return;
            }

            try {
                s.modifier(new Arbitre(id,Integer.parseInt(ageField.getText()),nomField.getText(),prenomField.getText(),emailField.getText(),phoneField.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        showArbitre();
        }



    @FXML
    private void deleteButton() {
        Arbitre selectedArbitre = TableView.getSelectionModel().getSelectedItem();
        int id = selectedArbitre.getId_arbitre();
        String query = "DELETE FROM arbitre WHERE id_arbitre="+id+"";
        executeQuery(query);
        showArbitre();
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Mettre à jour les champs de texte avec les informations de l'arbitre sélectionné
                updateFieldsWithSelectedArbitre(newValue);
            }
        });
        if (!TableView.getItems().isEmpty()) {
            TableView.getSelectionModel().select(0); // Sélectionnez la première ligne par défaut
        }
        TableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedArbitre = newValue; // Mettre à jour l'arbitre sélectionné
                updateFieldsWithSelectedArbitre(selectedArbitre); // Mettre à jour les champs de texte avec les données de l'arbitre sélectionné
            }
        });
        showArbitre();
    }






    private void updateFieldsWithSelectedArbitre(Arbitre arbitre) {
        ageField.setText(String.valueOf(arbitre.getAge()));
        nomField.setText(arbitre.getNom());
        prenomField.setText(arbitre.getPrenom());
        emailField.setText(arbitre.getEmail());
        phoneField.setText(arbitre.getPhone());
    }


    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportify","root","root");
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Arbitre> getArbitreList(){
        ObservableList<Arbitre> arbitresList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM arbitre ";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            Arbitre arbitres;
            while(rs.next()) {
                arbitres = new Arbitre(rs.getInt("id_arbitre"), rs.getInt("age"),rs.getString("Nom"),rs.getString("prenom"),rs.getString("Email"),rs.getString("Phone"));
                arbitresList.add(arbitres);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arbitresList;
    }


    public void showArbitre() {
        ObservableList<Arbitre> list = getArbitreList();

        ageColumn.setCellValueFactory(new PropertyValueFactory<Arbitre,Integer>("age"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<Arbitre,String>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<Arbitre,String>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Arbitre,String>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Arbitre,String>("phone"));

        TableView.setItems(list);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}