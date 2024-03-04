package controllers;

import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceCategorie;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardCategorieController implements Initializable {



    @FXML
    private TableColumn<Categorie, String> colNom;
    @FXML
    private TableColumn<Categorie, String> colDesc;

    @FXML
    private TableColumn<Categorie, String> colImage;

    @FXML
    private TextField imageField;

    @FXML
    private MenuButton menuSports;

    @FXML
    private Button modifierButton;

    @FXML
    private TextField nomField;
    @FXML
    private TextField description;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableView<Categorie> tv2;
    @FXML
    private Button btnChooseImage;
    @FXML
    private Button ajouterButton;
    @FXML
    private HBox hb;
    @FXML
    private Button returnButton;
    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;
    @FXML
    private TextField search;
    ObservableList<Categorie> dataList= FXCollections.observableArrayList();

    ServiceCategorie serviceCategorie=new ServiceCategorie();

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        Stage stage = (Stage) btnChooseImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String imageUrl = selectedFile.toURI().toString();
            imageField.setText(imageUrl);
        }
    }

   /* @FXML
    void initialize() {
        try {
            ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());
             tv2.setItems(observableList);
             colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colImage.setCellValueFactory(new PropertyValueFactory<>("Image"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }*/
   @FXML
   void ajouter(ActionEvent event) {
       try {
           if (nomField.getText().isEmpty() || description.getText().isEmpty() || imageField.getText().isEmpty()) {
               afficherErreur("Veuillez remplir tous les champs.");
               return;
           }
           if (isInteger(nomField.getText()) || isInteger(description.getText()) || isInteger(imageField.getText())) {
               afficherErreur("Veuillez saisir des chaînes de caractères dans les champs appropriés.");
               return;
           }

           Categorie nouvelleCategorie = new Categorie(nomField.getText(), description.getText(), imageField.getText());
           ServiceCategorie serviceCategorie = new ServiceCategorie();
           serviceCategorie.ajouter(nouvelleCategorie);

           ObservableList<Categorie> observableList = tv2.getItems();
           observableList.add(nouvelleCategorie);
           afficherSucces("Catégorie ajoutée");
           clearFields();
       } catch (SQLException e) {
           afficherErreur("Erreur lors de l'ajout de la catégorie: " + e.getMessage());
       }
   }
    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




    @FXML
    void modifier(ActionEvent event) {

        Categorie selectedCategorie = tv2.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {

            selectedCategorie.setNom(nomField.getText());
            selectedCategorie.setDescription(description.getText());
            selectedCategorie.setImage(imageField.getText());
            try {

                serviceCategorie.modifier(selectedCategorie);

                ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());
                tv2.setItems(observableList);

                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a categorie to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void supprimer(ActionEvent event) {

        Categorie selectedCategorie = tv2.getSelectionModel().getSelectedItem();
        if (selectedCategorie != null) {
            try {

                serviceCategorie.supprimer(selectedCategorie);

                ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());
                tv2.setItems(observableList);

                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a categorie to delete.");
            alert.showAndWait();
        }
    }



    private void clearFields() {
        nomField.clear();
        description.clear();
        imageField.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());
            tv2.setItems(observableList);
            colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
            colImage.setCellValueFactory(new PropertyValueFactory<>("Image"));


           // List<Categorie> categorieList = serviceCategorie.afficher();

           // Set items to your TableView (assuming it's named tv2)
           // tv2.setItems(observableList);


          /*  Categorie categorie1= new Categorie("Volleyball","feel it in the air","/img/istockphoto-1371823675-612x612.jpg");
            Categorie categorie2= new Categorie("Football","feel it in the air","/img/clubs-foot-europeens-plus-suivis-reseaux-sociaux-min.jpeg");
            Categorie categorie3= new Categorie("Rugby","feel it in the air","/img/sport-le-nouvel-equipement-a-400-euros-qui-arrive-sur-les-pelouses-de-rugby-1451176.jpg");
            Categorie categorie4= new Categorie("Basketball","feel it in the air","/img/180358.jpg");
            dataList.addAll(categorie1,categorie2,categorie3,categorie4);*/
           // FilteredList<Categorie> filteredData = new FilteredList<>(dataList,b ->true );

         /*   search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(categorie -> {


                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (categorie.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true;
                    } else if (categorie.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    else if (categorie.getImage().toLowerCase().indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else
                        return false;
                });
            });

            SortedList<Categorie> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tv2.comparatorProperty());

            tv2.setItems(sortedData);
*/
            tv2.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    nomField.setText(newSelection.getNom());
                    description.setText(newSelection.getDescription());
                    imageField.setText(newSelection.getImage());
                } else {
                    nomField.clear();
                    description.clear();
                    imageField.clear();
                }
            });
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        for (MenuItem item : menuSports.getItems()) {
            item.setOnAction(this::handleMenuItemAction);
        }
    }
    private void handleMenuItemAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        menuSports.setText(menuItem.getText());
    }
    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DashboardEquipe.fxml"));
            Parent root = loader.load();
            DashboardEquipeController controller = loader.getController();
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        void searchForCategorie(ActionEvent event) {
            try {
                tv2.getItems().clear();
                String searchText = search.getText();
                ObservableList<Categorie> observableList = FXCollections.observableList(serviceCategorie.afficher());

                List<Categorie> filteredList = observableList.stream()
                        .filter(e -> e.getNom().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());

                ObservableList<Categorie> newList = FXCollections.observableList(filteredList);

                tv2.setItems(newList);

                colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
                colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
                colImage.setCellValueFactory(new PropertyValueFactory<>("Image"));


            }catch ( SQLException e) {
                e.printStackTrace();
            }
        }

}
