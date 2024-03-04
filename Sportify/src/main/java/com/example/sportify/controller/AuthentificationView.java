package com.example.sportify.controller;

import com.example.sportify.HelloApplication;
import entities.Role;
import entities.Utilisateur;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;
import services.ServicUtilisateur;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AuthentificationView {

    @FXML
    private Button swapbtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private Label varLB;

    @FXML
    private Label helloLB;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfimage;

    @FXML
    private ImageView imguser;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfadresse;

    @FXML
    private DatePicker dpdate;

    @FXML
    private Button signUpBtn1;

    @FXML
    private Button signUpBtn11;

    @FXML
    private TextField tfloginemail;

    @FXML
    private ImageView icon1;

    @FXML
    private ImageView icon3;

    @FXML
    private ImageView icon4;

    @FXML
    private ImageView icon5;

    @FXML
    private ImageView icon6;

    @FXML
    private ImageView icon7;
    @FXML
    private Button btnForgot;

    @FXML
    private ImageView iconlogin1;

    @FXML
    private ImageView iconlogin2;

    @FXML
    private Label labeltitre;
    @FXML
    private PasswordField tfmpd;
    @FXML
    private PasswordField tfloginmdp;
    @FXML
    private Circle reduceBtn;
    @FXML
    private Circle closeBtn;
    @FXML
    private ImageView icon2;
    boolean swap=false;
    @FXML
    private AnchorPane defaultLayer;
    @FXML
    private AnchorPane animLayer;
    @FXML
    private AnchorPane mainLayer;
    ServicUtilisateur su=new ServicUtilisateur();
    @FXML
    private Button uploadbtn;

    public static int idLogin=0;
    @FXML
    private ComboBox<Role> cbrole;
    public static String email;

    @FXML
    public void initialize() {
        cbrole.getItems().setAll(Role.USER,Role.PROPRIETAIRE);
        varLB.setVisible(false);
        tfloginemail.setVisible(false);
        tfloginmdp.setVisible(false);
        signUpBtn11.setVisible(false);
        btnForgot.setVisible(false);
        //settingLB1.setVisible(false);
        iconlogin1.setVisible(false);
        iconlogin2.setVisible(false);
        signUpBtn.setVisible(false);
        swapbtn.setText("Login");
        tfimage.setDisable(true);
    }


    private void resetSignUpTF() {
        tfadresse.setText("");
        tfemail.setText("");
        tfmpd.setText("");
        tfimage.setText("");
        tfnom.setText("");
        tfprenom.setText("");
        dpdate.setValue(null);
    }
    private void resetSignInTF() {
        tfloginmdp.setText("");
        tfloginemail.setText("");
    }
    private void setIconVisibility(boolean state) {
        icon1.setVisible(state);
        icon2.setVisible(state);
        icon3.setVisible(state);
        icon4.setVisible(state);
        icon5.setVisible(state);
        icon6.setVisible(state);
        icon7.setVisible(state);

    }

    @FXML
    void swapForm(ActionEvent event) {


        if(swap==false)
        {

            System.out.println("form swapped");
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(animLayer);

            mainLayer.setTranslateX(-400);

            resetSignInTF();

            setIconVisibility(false);
            swapbtn.setText("SignUp");
            labeltitre.setText("Login");
            tfmpd.setVisible(false);
            tfnom.setVisible(false);
            dpdate.setVisible(false);
            tfprenom.setVisible(false);
            tfemail.setVisible(false);
            tfadresse.setVisible(false);
            tfimage.setVisible(false);
            signUpBtn1.setVisible(false);
            uploadbtn.setVisible(false);
            cbrole.setVisible(false);
            varLB.setVisible(true);
            helloLB.setVisible(false);



            iconlogin1.setVisible(true);
            iconlogin2.setVisible(true);
            tfloginemail.setVisible(true);
            tfloginmdp.setVisible(true);
            signUpBtn11.setVisible(true);
            btnForgot.setVisible(true);


            slide.setToX(689);
            slide.play();
            slide.setOnFinished((e -> {

            }));
            swap=true;
        }
        else{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(animLayer);

            mainLayer.setTranslateX(0);

            resetSignUpTF();

            //Shwoing Icons
            setIconVisibility(true);
            swapbtn.setText("Login");
            labeltitre.setText("SignUP");

            signUpBtn.setVisible(false);
            tfmpd.setVisible(true);
            tfnom.setVisible(true);
            dpdate.setVisible(true);
            tfprenom.setVisible(true);
            tfemail.setVisible(true);
            tfadresse.setVisible(true);
            tfimage.setVisible(true);
            signUpBtn1.setVisible(true);
            uploadbtn.setVisible(true);
            cbrole.setVisible(true);
            varLB.setVisible(false);
            helloLB.setVisible(true);



            iconlogin1.setVisible(false);
            iconlogin2.setVisible(false);
            tfloginemail.setVisible(false);
            tfloginmdp.setVisible(false);
            signUpBtn11.setVisible(false);
            btnForgot.setVisible(false);

            slide.setToX(0);

            slide.play();

            slide.setOnFinished((e -> {

            }));
            swap=false;
        }

    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(tfimage.getScene().getWindow());
        if(file!=null){
            String filename=file.getName();
            tfimage.setText(filename);
        }


    }

    @FXML
    public void reduceOnClick(Event event) {
        Stage currentStage;
        currentStage = (Stage) ((Node) (event.getSource())).getScene().getWindow().getScene().getWindow();
        currentStage.setIconified(true);
    }

    @FXML
    public void closeOnClick(Event event) {
        System.exit(0);
    }
    @FXML
    void signUpForm(ActionEvent event) {

    }

    @FXML
    public void signUp(ActionEvent actionEvent) {
        try {

            if(controleDeSaisie().length()>0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("invalide");
                alert.setContentText(controleDeSaisie());
                alert.showAndWait();
            }
            else{
                // Create a new Utilisateur object with data from the text fields and date picker
                Utilisateur newUser = new Utilisateur();
                newUser.setNom(tfnom.getText());
                newUser.setPrenom(tfprenom.getText());
                newUser.setEmail(tfemail.getText());
                newUser.setImage(tfimage.getText());
                newUser.setAdresse(tfadresse.getText());
                newUser.setMot_de_passe(tfmpd.getText()); // Assuming tfmpd is your password field

                // Convert LocalDate to java.util.Date
                LocalDate localDate = dpdate.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                newUser.setDate_de_naissance(Date.from(instant));
                newUser.setRole(cbrole.getValue());
                newUser.setNiveau_competence("NONE");
                su.ajouter(newUser);

                resetSignUpTF();
                swapForm(new ActionEvent());


                System.out.println("User signed up successfully");
            }

        } catch (SQLException e) {
            // Handle SQL Exception
            System.out.println("Error during sign up: " + e.getMessage());
        }
    }


    @FXML
    public void login(ActionEvent actionEvent) {
        Utilisateur u;
        try {
            u=su.login(tfloginemail.getText(),tfloginmdp.getText());
            System.out.println(u);
            if(u==null){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("invalide");
                alert.setContentText("Email ou mot de passe invalide");
                alert.showAndWait();
            } else if (!u.isVerified()) {
                email=u.getEmail();
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("User unverified");
                alert.setContentText("You need to verifie your account");
                alert.showAndWait();
                try {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("otp-verification.fxml"));
                    Parent root = loader.load();
                    Window currentWindow = tfloginmdp.getScene().getWindow();
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
            } else{
                idLogin=u.getId();
                if(u.getRole().equals(Role.ADMIN)){
                    System.out.println("------------");
                    //open admin:
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("afficher-user-admin.fxml"));
                   //    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("AdminInterface.fxml"));
                        Parent root = loader.load();
                        Window currentWindow = tfloginmdp.getScene().getWindow();
                        if (currentWindow instanceof Stage) {
                            ((Stage) currentWindow).close();
                        }

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        Image icon = new Image("https://i.ibb.co/dgpN1Hj/logo-SPORTIFY.png") ;
                        stage.getIcons().add(icon) ;
                        stage.setTitle("Sportify");
                        stage.show();
                        //NOTIFICATION
                        Notifications.create().title("Welcom").text("Login successfuls!").showConfirm();
                        //NOTIFICATION
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (u.getRole().equals(Role.USER)) {
                    //open user
                    try {
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("PlayerInterface.fxml"));
                        Parent root = loader.load();
                        Window currentWindow = tfloginmdp.getScene().getWindow();
                        if (currentWindow instanceof Stage) {
                            ((Stage) currentWindow).close();
                        }

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        Image icon = new Image("https://i.ibb.co/dgpN1Hj/logo-SPORTIFY.png") ;
                        stage.getIcons().add(icon) ;
                        stage.setTitle("Sportify");
                        stage.show();
                        Notifications.create().title("Welcom").text("Login successfuls!").showConfirm();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        //FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-acceuil-view.fxml"));
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/sportify/proprietaireinterface.fxml"));
                        Parent root = loader.load();
                        Window currentWindow = tfloginmdp.getScene().getWindow();
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
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String controleDeSaisie(){
        String erreur="";
        if(tfemail.getText().trim().isEmpty() || !tfemail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            erreur+="- Email invalide ou vide.\n";
        }
        if(tfadresse.getText().trim().isEmpty()){
            erreur+="- Adresse vide.\n";
        }
        if(tfnom.getText().trim().isEmpty()){
            erreur+="- Nom vide.\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="- Prenom vide.\n";
        }
        if(tfimage.getText().trim().isEmpty()){
            erreur+="- Image vide.\n";
        }
        if(tfmpd.getText().trim().isEmpty() || tfmpd.getText().length() < 4){
            erreur += "- Mot de passe vide ou trop court (moins de 4 caractères).\n";
        }
        if(dpdate.getValue() == null){
            erreur += "- Date de naissance non spécifiée.\n";
        }
        if(cbrole.getValue() == null){
            erreur += "- Role non spécifiée.\n";
        }
        return erreur;

    }
    @FXML
    void gotoforgotPassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("forgot-password-view.fxml"));
            Parent root = loader.load();
            Window currentWindow = tfimage.getScene().getWindow();
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

}
