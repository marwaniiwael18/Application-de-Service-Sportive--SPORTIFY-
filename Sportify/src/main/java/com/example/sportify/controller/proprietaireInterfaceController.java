package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ServicUtilisateur;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class proprietaireInterfaceController  implements Initializable {

    @FXML
    private AnchorPane interfacechanger;

    @FXML
    private Label lusername;

    @FXML
    private AnchorPane navbar;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Button TerrainButton;

    @FXML
    private Label logButton;

    @FXML
    private Label profilebutton;


    @FXML
    private Label competitionButton;

    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();

    @FXML
    private ImageView imgprofile;

    @FXML
    public void backToAccueil(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/user-acceuil-view.fxml"));
        AnchorPane classementinterface = fxmlLoader.load();
        interfacechanger.getChildren().add(classementinterface) ;
    }

    public void profilebutton(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/user-profile-view.fxml"));
        AnchorPane classementinterface = fxmlLoader.load();
        interfacechanger.getChildren().add(classementinterface) ;
    }

    @FXML
    void competition(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/propListeCompetition.fxml"));
        AnchorPane Competition = fxmlLoader.load();
        interfacechanger.getChildren().add(Competition) ;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/user-acceuil-view.fxml"));
        AnchorPane Competition = null;
        try {
            Competition = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        interfacechanger.getChildren().add(Competition) ;

        Utilisateur u= null;
        try {
            u = servicUtilisateur.getById(AuthentificationView.idLogin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lusername.setText(u.getNom()+" "+u.getPrenom());

        File file=new File("C:\\Users\\sahar\\OneDrive\\Bureau\\sportifyf\\Sportify-sahar\\src\\main\\resources\\com\\example\\sportify\\img\\"+u.getImage());
        Image img=new Image(file.toURI().toString());

        imgprofile.setImage(img);
        imgprofile.setFitWidth(30);
        imgprofile.setPreserveRatio(true);
    }

    @FXML
    void gototerrain(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/Terrain.fxml"));
        AnchorPane terrain = null;
        try {
            terrain = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        interfacechanger.getChildren().add(terrain) ;
    }

    @FXML
    void logoutt( MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/sportify/authentification-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = lusername.getScene().getWindow();
            if (currentWindow instanceof Stage) {
                ((Stage) currentWindow).close();
            }
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            Image icon = new Image("https://i.ibb.co/dgpN1Hj/logo-SPORTIFY.png") ;
            stage.getIcons().add(icon) ;
            stage.setTitle("Sportify");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void gotoEvent(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Event.fxml"));
        AnchorPane eventt = null;
        try {
            eventt = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        interfacechanger.getChildren().add(eventt) ;
    }
}
