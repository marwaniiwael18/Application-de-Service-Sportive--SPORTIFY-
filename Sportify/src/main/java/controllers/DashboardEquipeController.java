package controllers;

import entities.Categorie;
import entities.Equipe;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceCategorie;
import services.ServiceEquipe;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardEquipeController {

    ServiceEquipe serviceEquipe=new ServiceEquipe();

    @FXML
    private TableColumn<Equipe, String> colNom;

    @FXML
    private TableColumn<Equipe, Categorie> colIDCateg;

    @FXML
    private TableColumn<Equipe, String> colNiveau;
    @FXML
    private TableColumn<Equipe, Boolean> colRandom;

    @FXML
    private TableColumn<Equipe, Integer> colRank;

    @FXML
    private MenuButton menuSports;
    @FXML
    private TextField id;

    @FXML
    private TableView<Equipe> tvEquipe;
    @FXML
    private Button ajouterButton;
    @FXML
    private TextField idCateg;

    @FXML
    private MenuButton menuButton;

    @FXML
    private Button modifierButton;

    @FXML
    private TextField niveau;

    @FXML
    private TextField nom;

    @FXML
    private TextField random;

    @FXML
    private TextField rank;

    @FXML
    private Button supprimerButton;
    @FXML
    private TextField search;

    public ServiceCategorie serviceCategorie = new ServiceCategorie();


    @FXML
    void initialize(){
        try {
            ObservableList<Equipe> observableList = FXCollections.observableList(serviceEquipe.afficher());
            tvEquipe.setItems(observableList);
            colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            colIDCateg.setCellValueFactory(new PropertyValueFactory<>("IDCateg"));
            colNiveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
            colRandom.setCellValueFactory(cellData -> {
                Equipe equipe = cellData.getValue();
                return new SimpleBooleanProperty(equipe.isRandom);
            });



            colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
            tvEquipe.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    nom.setText(newSelection.getNom());
                    idCateg.setText(String.valueOf(newSelection.getIDCateg().getID_Categ())); // Assuming getId() retrieves the ID
                    niveau.setText(newSelection.getNiveau());
                    random.setText(String.valueOf(newSelection.getRandom()));
                    rank.setText(String.valueOf(newSelection.getRank()));
                }
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }
    @FXML
    void ajouter(ActionEvent event) {
        try {
            if (nom.getText().isEmpty() || niveau.getText().isEmpty() || random.getText().isEmpty() || rank.getText().isEmpty() || idCateg.getText().isEmpty()) {
                afficherErreur("Veuillez remplir tous les champs.");
                return;
            }
            boolean isRandom;
            try {
                isRandom = Boolean.parseBoolean(random.getText());
            } catch (Exception e) {
                afficherErreur("Le champ \"Random\" doit être un booléen (true/false).");
                return;
            }
            int rankEquipe;
            try {
                rankEquipe = Integer.parseInt(rank.getText());
            } catch (NumberFormatException e) {
                afficherErreur("Le champ \"Rank\" doit être un entier.");
                return;
            }
            int categorieId;
            try {
                categorieId = Integer.parseInt(idCateg.getText());
            } catch (NumberFormatException e) {
                afficherErreur("Le champ \"ID Categorie\" doit être un entier.");
                return;
            }
            Categorie categorie = serviceCategorie.getCategorieById(categorieId);
            if (categorie == null) {
                afficherErreur("La catégorie spécifiée n'existe pas.");
                return;
            }
            Equipe newEquipe = new Equipe(nom.getText(), niveau.getText(), categorie, isRandom, rankEquipe);
            serviceEquipe.ajouter(newEquipe);
            refreshTableView();
            clearFields();
            afficherSucces("Équipe créée avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            Equipe selectedEquipe = tvEquipe.getSelectionModel().getSelectedItem();

            if (selectedEquipe != null) {
                String nomEquipe = nom.getText();
                String niveauEquipe = niveau.getText();
                boolean isRandom = Boolean.parseBoolean(random.getText());
                int rankEquipe = Integer.parseInt(rank.getText());
                int categorieId = Integer.parseInt(idCateg.getText());
                Categorie categorie = serviceCategorie.getCategorieById(categorieId);
                selectedEquipe.setNom(nomEquipe);
                selectedEquipe.setIDCateg(categorie);
                selectedEquipe.setNiveau(niveauEquipe);
                selectedEquipe.setRandom(isRandom);
                selectedEquipe.setRank(rankEquipe);

                serviceEquipe.modifier(selectedEquipe);
                refreshTableView();
                clearFields();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        try {
            Equipe selectedEquipe = tvEquipe.getSelectionModel().getSelectedItem();

            if (selectedEquipe != null) {
                serviceEquipe.supprimer(selectedEquipe);
                refreshTableView();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void refreshTableView() throws SQLException {
        ObservableList<Equipe> observableList = FXCollections.observableList(serviceEquipe.afficher());
        tvEquipe.setItems(observableList);
    }

    private void clearFields() {
        nom.clear();
        idCateg.clear();
        niveau.clear();
        random.clear();
        rank.clear();
    }
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void afficherSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void searchForEquipe(ActionEvent event) {
        try {
            tvEquipe.getItems().clear();
            String searchText = search.getText();
            ObservableList<Equipe> observableList = FXCollections.observableList(serviceEquipe.afficher());

            List<Equipe> filteredList = observableList.stream()
                    .filter(e -> e.getNom().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            ObservableList<Equipe> newList = FXCollections.observableList(filteredList);

            tvEquipe.setItems(newList);

            colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            colIDCateg.setCellValueFactory(new PropertyValueFactory<>("IDCateg"));
            colNiveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
            colRank.setCellValueFactory(new PropertyValueFactory<>("rank"));

        }catch ( SQLException e) {
            e.printStackTrace();
        }
    }

}





