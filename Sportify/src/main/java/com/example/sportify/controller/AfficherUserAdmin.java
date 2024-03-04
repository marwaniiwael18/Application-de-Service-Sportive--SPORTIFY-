package com.example.sportify.controller;
import com.example.sportify.HelloApplication;
import entities.Role;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServicUtilisateur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherUserAdmin
{
    @FXML
    private TableView<Utilisateur> tableuser;

    @FXML
    private TableColumn<Utilisateur, String> tcadresse;

    @FXML
    private TableColumn<Utilisateur, Date> tcdate;

    @FXML
    private TableColumn<Utilisateur, String> tcemail;

    @FXML
    private TableColumn<Utilisateur, String> tcimage;



    @FXML
    private TableColumn<Utilisateur, String> tcniveau;

    @FXML
    private TableColumn<Utilisateur, String> tcnom;

    @FXML
    private TableColumn<Utilisateur, String> tcprenom;

    @FXML
    private TableColumn<Utilisateur, Role> tcrole;
    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();
    ObservableList<Utilisateur> data= FXCollections.observableArrayList();
    @FXML
    private TableColumn<Utilisateur,Void> tcsupp;
    @FXML
    private TableColumn<Utilisateur, Void> tcupdate;
    @FXML
    private ImageView imgprofile;
    @FXML
    private Label lusername;
    @FXML
    private TextField tfrecherche;
    @FXML
    private ComboBox<String> cbtri;

    @FXML
    public void initialize() {
        cbtri.getItems().setAll("Nom","Prenom","Email","Date de naissance");

        try {
            Utilisateur u=servicUtilisateur.getById(AuthentificationView.idLogin);
            lusername.setText(u.getNom()+" "+u.getPrenom());
            File file=new File("C:\\Users\\sahar\\OneDrive\\Bureau\\sportifyf\\Sportify-sahar\\src\\main\\resources\\com\\example\\sportify\\img\\"+u.getImage());
            Image img=new Image(file.toURI().toString());

            imgprofile.setImage(img);
            imgprofile.setFitWidth(50);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            refresh(servicUtilisateur.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        recherche_avance();
    }
    public void refresh(List<Utilisateur> users){
        data.clear();
            data=FXCollections.observableArrayList(users);

        tcnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tcadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tcrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));

        tcemail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tcniveau.setCellValueFactory(new PropertyValueFactory<>("niveau_competence"));
        tcprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tcimage.setCellValueFactory(new PropertyValueFactory<>("image"));
        tcimage.setCellFactory(col->new TableCell<Utilisateur,String>(){
            private final ImageView imageView=new ImageView();

            @Override
            protected void updateItem(String image,boolean empty){
                super.updateItem(image,empty);
                if(empty){
                    setGraphic(null);
                }
                else{
                    //File file=new File("C:\\Users\\sahar\\OneDrive\\Bureau\\sahar\\Sportify-sahar\\src\\main\\resources\\com\\example\\sportify\\img\\"+image);
                    File file=new File("C:\\Users\\sahar\\OneDrive\\Bureau\\sportifyf\\Sportify-sahar\\src\\main\\resources\\com\\example\\sportify\\img\\"+image);
                    Image img=new Image(file.toURI().toString());

                    imageView.setImage(img);
                    imageView.setFitWidth(30);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }

        });
        tcsupp.setCellFactory(col->new TableCell<Utilisateur,Void>(){
            private final Button btn=new Button("Supprimer");

            {
                btn.setOnAction(event->{
                    Utilisateur u=getTableView().getItems().get(getIndex());
                    System.out.println(u);
                    try {
                        servicUtilisateur.supprimer(u.getId());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        refresh(servicUtilisateur.afficher());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                btn.setStyle("-fx-background-color: #64dd17;" +
                        "-fx-text-fill:black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 5px;");
            }

            @Override
            protected void updateItem(Void item,boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    setGraphic(null);
                }
                else{
                    setGraphic(btn);
                }
            }
        });
        tcupdate.setCellFactory(col -> new TableCell<Utilisateur, Void>() {
            private final Button btn = new Button("Update");

            {
                btn.setOnAction(event -> {
                    Utilisateur u = getTableView().getItems().get(getIndex());
                    openUpdateForm(u);
                });
                // Style the button as needed
                btn.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black; -fx-background-radius: 10; -fx-padding: 5px;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                else{
                    setGraphic(btn);
                }
            }
        });
        tableuser.setItems(data);

    }
    private void openUpdateForm(Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("getsion-user-admin-view.fxml"));
            Parent root = loader.load();

            GetsionUserAdminView controller = loader.getController();
            controller.setUserForUpdate(user);

            Window currentWindow = tableuser.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }

            // Open the new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("getsion-user-admin-view.fxml"));
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
    public void recherche_avance() {
        try {
            data = FXCollections.observableArrayList(servicUtilisateur.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FilteredList<Utilisateur> filteredData = new FilteredList<>(data, u -> true);

        tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utilisateur -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (utilisateur.getAdresse().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (utilisateur.getNiveau_competence().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(utilisateur.getDate_de_naissance()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(utilisateur.getRole()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else {
                    return false;
                }

            });
            tableuser.setItems(filteredData);
        });
    }
    @FXML
    void triUsers(ActionEvent event) {
        try {
            refresh(servicUtilisateur.triParCritere(cbtri.getValue()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}