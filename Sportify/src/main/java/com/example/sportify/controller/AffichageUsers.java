package com.example.sportify.controller;

public class AffichageUsers {
/*
  ServicUtilisateur userService = new ServicUtilisateur();

  @FXML
  private TableColumn<Utilisateur, String> nomColumn;

  @FXML
  private TableColumn<Utilisateur, String> prenomColumn;

  @FXML
  private TableColumn<Utilisateur, String> emailColumn;

  @FXML
  private TableColumn<Utilisateur, String> dateNaissanceColumn;

  @FXML
  private TableColumn<Utilisateur, String> niveauCompetenceColumn;

  @FXML
  private TableColumn<Utilisateur, String> roleColumn;
  @FXML
  private TableColumn<Utilisateur, String> imageColumn;
  @FXML
  private TableColumn<Utilisateur, String> adresseColumn;

  @FXML
  private TableColumn<Utilisateur, String> idColumn;

  @FXML
  private TableView<Utilisateur> userTableView;

  @FXML
  private TextField nomField;
  @FXML
  private TextField idField;
  @FXML
  private TextField prenomField;

  @FXML
  private TextField emailField;

  @FXML
  private TextField imageField;

  @FXML
  private TextField dateNaissanceField;

  @FXML
  private TextField niveauCompetenceField;

  @FXML
  private TextField roleField;

  @FXML
  private TextField adresseField;

  @FXML
  private PasswordField passwordField;
  @FXML
  private Button importerImageButton;



  @FXML
  private void importerImage(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      String imageName = selectedFile.getName();
      imageField.setText(imageName);
    }
  }
  @FXML
  private void deconnexion(ActionEvent event) {
    try {
      // Load the login.fxml file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sportify/login.fxml"));
      Parent root = loader.load();


      Scene scene = new Scene(root);

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


      stage.setScene(scene);


      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  @FXML
  void initialize() {
    try {
      refreshTableView();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void refreshTableView() throws SQLException {
    List<Utilisateur> utilisateurs = userService.afficher();
    ObservableList<Utilisateur> observableList = FXCollections.observableArrayList(utilisateurs);
    userTableView.setItems(observableList);

    nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
    prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
    niveauCompetenceColumn.setCellValueFactory(new PropertyValueFactory<>("niveau_competence"));
    roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
    imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
  }
  @FXML
  private void ajouterUtilisateur(ActionEvent event) {
    try {
      Utilisateur nouvelUtilisateur = new Utilisateur();
      updateUserFields(nouvelUtilisateur);
      userService.ajouter(nouvelUtilisateur);
      refreshTableView();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void AffichagePersonne(ActionEvent actionEvent) {
    try {
      refreshTableView();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleRowClick(MouseEvent event) {
    if (event.getClickCount() == 1) {
      Utilisateur selectedUser = userTableView.getSelectionModel().getSelectedItem();
      updateUIWithUserData(selectedUser);
    }
  }

  private void updateUIWithUserData(Utilisateur user) {
    if (user != null) {
      nomField.setText(user.getNom());
      prenomField.setText(user.getPrenom());
      emailField.setText(user.getEmail());
      imageField.setText(user.getImage());
      dateNaissanceField.setText(user.getDate_de_naissance());
      niveauCompetenceField.setText(user.getNiveau_competence());
      roleField.setText(user.getRole());
      adresseField.setText(user.getAdresse());
      passwordField.setText(user.getMot_de_passe());
    }
  }


  @FXML
  private void updateUser(ActionEvent event) {
    try {
      Utilisateur selectedUser = userTableView.getSelectionModel().getSelectedItem();
      if (selectedUser != null) {
        updateUserFields(selectedUser);
        userService.modifier(selectedUser.getId(), selectedUser);
        refreshTableView();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void deleteUser(ActionEvent event) {
    try {
      Utilisateur selectedUser = userTableView.getSelectionModel().getSelectedItem();
      if (selectedUser != null) {
        userService.supprimer(selectedUser.getId());
        refreshTableView();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void updateUserFields(Utilisateur user) {
    user.setNom(nomField.getText());
    user.setPrenom(prenomField.getText());
    user.setEmail(emailField.getText());
    user.setImage(imageField.getText());
    user.setDate_de_naissance(dateNaissanceField.getText());
    user.setNiveau_competence(niveauCompetenceField.getText());
    user.setRole(roleField.getText());
    user.setAdresse(adresseField.getText());
  }*/
}
