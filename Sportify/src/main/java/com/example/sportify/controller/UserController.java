package com.example.sportify.controller;

public class UserController {/*
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField prenom2Field;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField niveauCompetenceField;

    @FXML
    private TextField roleField;

    @FXML
    private TextField dateNaissanceField;

    @FXML
    private TextField loginEmailField;

    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button connexionButton;
    @FXML
    private TableView<Utilisateur> utilisateurTableView;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField dateNaissanceTextField;
    @FXML
    private TextField niveauCompetenceTextField;
    @FXML
    private TextField roleTextField;
    @FXML
    private TextField sexeTextField;
    @FXML
    private TextField adresseTextField;
    @FXML
    private Button modifierProfilButton;

    private Utilisateur loggedInUser;
    @FXML
    private Button importerImageButton;



    @FXML
    private void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            imageField.setText(imagePath);
        }
    }



    @FXML
    public void initialize() {
        if (loggedInUser != null) {
            nomField.setText(loggedInUser.getNom());
            prenomField.setText(loggedInUser.getPrenom());
            prenom2Field.setText(loggedInUser.getPrenom());
            passwordField.setText(loggedInUser.getMot_de_passe());
            emailField.setText(loggedInUser.getEmail());
            adresseField.setText(loggedInUser.getAdresse());
            niveauCompetenceField.setText(loggedInUser.getNiveau_competence());
            //roleField.setText(loggedInUser.getRole());
            //dateNaissanceField.setText(loggedInUser.getDate_de_naissance());
        }
    }



    private final ServicUtilisateur userService;

    public UserController() {
        this.userService = new ServicUtilisateur();
    }

    @FXML
    public void modifierProfilAction(ActionEvent event) {
        try {
            if (loggedInUser != null) {
                Utilisateur modifiedUser = new Utilisateur();

                // Set the modified data from the text fields
                modifiedUser.setNom(nomField.getText());
                modifiedUser.setPrenom(prenomField.getText());
                modifiedUser.setImage(imageField.getText());
                modifiedUser.setMot_de_passe(passwordField.getText());
                modifiedUser.setAdresse(adresseField.getText());
               // modifiedUser.setDate_de_naissance(dateNaissanceField.getText());
               // modifiedUser.setRole(roleField.getText());
                modifiedUser.setEmail(emailField.getText());
                modifiedUser.setNiveau_competence(niveauCompetenceField.getText());

                userService.modifier(loggedInUser.getId(), modifiedUser);

                loggedInUser = modifiedUser;

                System.out.println("User profile modified successfully!");
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (show an error message, log, etc.)
            e.printStackTrace();
            System.out.println("Error modifying user profile: " + e.getMessage());
        }
    }



    @FXML
    public void deleteUserById(ActionEvent actionEvent) {
        if (loggedInUser != null) {
            try {
                userService.supprimer(loggedInUser.getId());
                System.out.println("User deleted successfully!");


            } catch (SQLException e) {
                System.out.println("Error deleting user: " + e.getMessage());
            }
        }
    }

    @FXML
    public void addUser(ActionEvent actionEvent) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String motDePasse = passwordField.getText();
        String adresse = adresseField.getText();
        String dateDeNaissance = dateNaissanceField.getText();
        String role = roleField.getText();
        String email = emailField.getText();
        String niveauCompetence = niveauCompetenceField.getText();

        String image = imageField.getText();

        Utilisateur newUser = new Utilisateur(nom, prenom, image, motDePasse, adresse, dateDeNaissance, role, email, niveauCompetence);

        try {
            userService.ajouter(newUser);
            System.out.println("User added successfully!");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            ((Stage) nomField.getScene().getWindow()).close();
        } catch (SQLException | IOException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }


    @FXML
    public void login(ActionEvent actionEvent) {
        String email = loginEmailField.getText();
        String password = loginPasswordField.getText();

        try {
            Utilisateur user = userService.authentifier(email, password);

            if (user != null) {
                System.out.println("Login successful!");
                System.out.println("User email: " + user.getEmail());
                System.out.println("User password: " + user.getMot_de_passe());

                if ("admin".equals(user.getEmail()) && "admin".equals(user.getMot_de_passe())) {
                    redirectToAdminPage();
                } else {
                    loggedInUser = user;

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/profile.fxml"));
                    Parent root = loader.load();

                    UserController profileController = loader.getController();
                    profileController.initData(loggedInUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Profile");
                    stage.show();

                    ((Stage) loginEmailField.getScene().getWindow()).close();
                }
            } else {
                errorLabel.setText("Invalid email or password. Please try again.");
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @FXML
    public void goToProfilePage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/profile.fxml"));
            Parent root = loader.load();

            UserController profileController = loader.getController();
            profileController.initData(loggedInUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Profile");
            stage.show();

            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            System.out.println("Error loading profile page: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void goToHomePage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/Media.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Acceuil");
            stage.show();

            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {

            showAlert("Error", "Unable to load the home page. Please try again later.");

        }
    }

    // Helper method to show a simple alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void redirectToAdminPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/admin.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Admin Page");
        stage.show();

        ((Stage) loginEmailField.getScene().getWindow()).close();
    }



 public void initData(Utilisateur user) {
        if (user != null) {
            loggedInUser = user;
           System.out.println("Setting nomField: " + user.getNom());
            System.out.println("Setting prenomField: " + user.getPrenom());
     System.out.println("Setting passwordField: " + user.getMot_de_passe());
     System.out.println(("email" + user.getEmail()));

    nomField.setText(user.getNom());
    prenomField.setText(user.getPrenom());
    imageField.setText(user.getImage());
    passwordField.setText(user.getMot_de_passe());
    adresseField.setText(user.getAdresse());
    dateNaissanceField.setText(user.getDate_de_naissance());
    roleField.setText(user.getRole());

    emailField.setText(user.getEmail());
     niveauCompetenceField.setText(user.getNiveau_competence());
    } else {
      System.out.println("User object is null.");
     }
      }



    @FXML
    public void goToLoginPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            // Optionally, close the current window (register window)
            ((Stage) connexionButton.getScene().getWindow()).close();
        } catch (IOException e) {
            System.out.println("Error loading login page: " + e.getMessage());
        }
    }
    @FXML
    public void goToREGISTER(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("register");
            stage.show();

            // Optionally, close the current window (register window)
            ((Stage) connexionButton.getScene().getWindow()).close();
        } catch (IOException e) {
            System.out.println("Error loading login page: " + e.getMessage());
        }
    }


    private void updateTextFields(Utilisateur selectedUser) {
    }



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utilisateurTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateTextFields(newValue);
            }
        });



        if (loggedInUser != null) {
            nomField.setText(loggedInUser.getNom());
            prenomField.setText(loggedInUser.getPrenom());
            prenom2Field.setText(loggedInUser.getPrenom());
            imageField.setText(loggedInUser.getImage());
            passwordField.setText(loggedInUser.getMot_de_passe());
            adresseField.setText(loggedInUser.getAdresse());
            dateNaissanceField.setText(loggedInUser.getDate_de_naissance());
            roleField.setText(loggedInUser.getRole());
            emailField.setText(loggedInUser.getEmail());
            niveauCompetenceField.setText(loggedInUser.getNiveau_competence());
        } else {
            System.out.println("loggedInUser is null.");
        }
    }

    @FXML
    private void deconnexion(ActionEvent event) {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/login.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            // Get the stage from the action event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.setScene(scene);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}

