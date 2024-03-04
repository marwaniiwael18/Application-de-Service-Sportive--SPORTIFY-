package controllers;

import entities.Categorie;
import entities.Equipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import services.ServiceCategorie;
import services.ServiceEquipe;

import java.io.IOException;
import java.sql.SQLException;

public class CreerController {

    @FXML
    private Button btnafficher;

    ServiceEquipe serviceEquipe= new ServiceEquipe();

    @FXML
    private Button btncreate;

    @FXML
    private MenuButton menuSports;

    @FXML
    private TextField tfIDCateg;

    @FXML
    private TextField tfNiveau;

    @FXML
    private Text tfNom;

    @FXML
    private TextField tfNomEquipe;

    @FXML
    private TextField tfRank;

    @FXML
    private TextField tfid_createur;

    @FXML
    private TextField tfisRandom;

    @FXML
    private VBox vbox1;

    @FXML
    void afficher(ActionEvent event) {
        try{
            Parent root= FXMLLoader.load(getClass().getResource("/FXML/DashboardEquipe.fxml"));
            tfNom.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    @FXML
    void create(ActionEvent event) {
        try{
            Categorie categorie = retrieveCategorieById(Integer.parseInt(tfIDCateg.getText()));
            serviceEquipe.ajouter(new Equipe(tfNomEquipe.getText(), tfNiveau.getText(), categorie, Boolean.parseBoolean(tfisRandom.getText()), Integer.parseInt(tfRank.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Equipe ajouté");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private Categorie retrieveCategorieById(int id) throws SQLException {
        ServiceCategorie serviceCategorie = new ServiceCategorie();
        return serviceCategorie.getCategorieById(id);
    }
    /*private Utilisateur retrieveUtilisateurById(int id) throws SQLException {
        // Assuming you have a service for Utilisateur
        ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
        return serviceUtilisateur.retrieveUtilisateurById(id);
    }
*/
}
