package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


public class AdminInterfaceController implements Initializable {


    @FXML
    private Label lusername;


    @FXML
    private ImageView imgprofile;

    @FXML
    private Label competitionButton;

    @FXML
    private AnchorPane interfacechanger;

    @FXML
    private AnchorPane navbar;

    @FXML
    private Label profilebutton;

    @FXML
    private Label ArbitreButton;
    @FXML
    private AnchorPane sidebar;

    ServicUtilisateur servicUtilisateur=new ServicUtilisateur();

    @FXML
    void competitionButton(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sportify/AdminMatchCompetition.fxml"));
        AnchorPane competitioninterface = fxmlLoader.load();
        interfacechanger.getChildren().add(competitioninterface) ;
    }

    @FXML
    void profilebutton(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("AfficherUserAdmin.fxml"));
            AnchorPane arbitreInterface = loader.load();
            interfacechanger.getChildren().setAll(arbitreInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void arbitreButton(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("AfficherUserAdmin.fxml"));
            AnchorPane ProfilUser = loader.load();
            interfacechanger.getChildren().setAll(ProfilUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/sportify/AfficherArbitre.fxml"));
                AnchorPane arbitreInterface = loader.load();
                interfacechanger.getChildren().setAll(arbitreInterface);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* Utilisateur u= null;
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
        imgprofile.setPreserveRatio(true);*/
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
}
