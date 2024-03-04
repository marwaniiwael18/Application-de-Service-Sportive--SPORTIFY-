package controllers;

import com.example.sportify.HelloApplication;
import entities.Arbitre;
import entities.Equipe;
import entities.Match;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import services.ExcelGenerator;
import services.PDFGenerator;
import services.ServicUtilisateur;
import services.ServiceMatchA;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class AfficherMatchController implements Initializable {
    private ServiceMatchA s= new ServiceMatchA();
    private Connection connection;
    @FXML
    private ImageView imgprofile;

    @FXML
    private Label lusername;

    @FXML
    private MediaView mediaView;
    ServicUtilisateur su=new ServicUtilisateur();
    @FXML
    private Button addButton;
    @FXML
    private Button refrech;
    @FXML
    private ListView<Match> matchListView;
    private ObservableList<Match> matchList = FXCollections.observableArrayList();
    private AjouterMatchController ajouterMatchController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        matchListView.setCellFactory(new Callback<ListView<Match>, ListCell<Match>>() {
            @Override
            public ListCell<Match> call(ListView<Match> param) {
                return new MatchListCell();
            }
        });


        refreshListView();

    }
    @FXML
    private void handleRefreshButtonAction(ActionEvent event) {
        refreshListView();
    }

    // Méthode pour rafraîchir la ListView
    private void refreshListView() {
        Platform.runLater(() -> {
            matchListView.setItems(getMatchesFromDatabase());
            matchListView.refresh();
        });
    }

    private HBox createMatchNode(Match match) {
        Label nomLabel = new Label(String.format("%-20s", match.getNom()));
        Label typeLabel = new Label(String.format("%-15s", match.getType()));
        Label dateLabel = new Label(String.format("%-15s", match.getDate()));
        Label heureLabel = new Label(String.format("%-15s", match.getHeure()));
        Label descriptionLabel = new Label(String.format("%-30s", match.getDescription()));
        Label equipe1Label = new Label(String.format("%-20s (%s - Rang: %d)", match.getEquipe1().getNom(), match.getEquipe1().getNiveau(), match.getEquipe1().getRank()));
        Label equipe2Label = new Label(String.format("%-20s (%s - Rang: %d)", match.getEquipe2().getNom(), match.getEquipe2().getNiveau(), match.getEquipe2().getRank()));
        Label arbitreLabel = new Label(String.format("%-20s", match.getArbitre()));

        HBox hbox = new HBox(nomLabel, typeLabel, dateLabel, heureLabel, descriptionLabel, equipe1Label, equipe2Label, arbitreLabel);
        hbox.setSpacing(10);
        return hbox;

    }

    private ObservableList<Match> getMatchesFromDatabase() {
        ObservableList<Match> matchList = FXCollections.observableArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
            String query = "SELECT * FROM matc";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Match match = new Match();
                match.setID_Matc(rs.getInt("ID_Matc"));
                match.setNom(rs.getString("nom"));
                match.setType(rs.getString("type"));
                LocalDate localDate = LocalDate.parse(rs.getString("Date"));
                Date sqlDate = Date.valueOf(localDate);
                match.setDate(sqlDate);
                match.setHeure(Time.valueOf(rs.getString("Heure")));
                match.setDescription(rs.getString("Description"));
                match.setEquipe1(getTeamName(rs.getInt("Equipe1")));
                match.setEquipe2(getTeamName(rs.getInt("Equipe2")));
                match.setArbitre(getRefereeName(rs.getInt("arbitre")));
                matchList.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return matchList;
    }
    private Equipe getTeamName(int teamId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
        String query = "SELECT Nom,Niveau,`rank` FROM equipe WHERE IDEquipe = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                    Equipe equipe = new Equipe();
                    equipe.setNom(resultSet.getString("Nom"));
                    equipe.setNiveau(resultSet.getString("Niveau"));
                    equipe.setRank(resultSet.getInt("Rank"));
                    return equipe;

            }
        }
        return null;
    }

    private Arbitre getRefereeName(int refereeId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
        String query = "SELECT Nom,Prenom FROM arbitre WHERE id_arbitre = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, refereeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Arbitre arbitre =new Arbitre();
                arbitre.setNom(resultSet.getString("Nom"));
                arbitre.setPrenom(resultSet.getString("Prenom"));


                return arbitre;
            }
        }
        return null;
    }

    public void handleAddButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMatch.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre AjouterMatch.fxml
            AjouterMatchController controller = (AjouterMatchController) loader.getController();

            // Afficher la fenêtre pour ajouter un nouveau match
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Match newMatch = controller.getNewlyAddedMatch();
            stage.close();


            if (newMatch != null) {
                matchList.add(newMatch);
                System.out.println("Nouveau match ajouté : " + newMatch);
                matchListView.setItems(getMatchesFromDatabase()); // Mettre à jour la ListView avec la nouvelle liste de matches
                matchListView.refresh(); // Rafraîchir la ListView pour afficher le nouveau match ajouté
                System.out.println("ListView rafraîchie");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleModifierButtonAction(ActionEvent actionEvent) {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            try {
                // Charger le fichier FXML de la fenêtre de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMatch.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur de la fenêtre de modification
                ModifierMatch modifierMatchController = loader.getController();

                // Passer le match sélectionné au contrôleur de la fenêtre de modification
                modifierMatchController.initData(selectedMatch);

                // Créer une nouvelle fenêtre modale pour la fenêtre de modification
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez choisir de nouveau les équipes et l'arbitre.");

                alert.showAndWait();

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();

                System.out.println("Modification du match : " + selectedMatch);
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            System.out.println("Aucun match sélectionné.");
        }}
    public void handleSupprimerButtonAction(ActionEvent actionEvent) {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            try {
                s.supprimer(selectedMatch);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le match sélectionné ?");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce match ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                matchList.remove(selectedMatch);
                System.out.println("Match supprimé : " + selectedMatch);
            }
        } else {
            System.out.println("Aucun match sélectionné.");
        }
    }

    @FXML
    private TextField searchTextField;
    @FXML
    private void handleGeneratePDFButtonAction(ActionEvent event) {
        Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();
        PDFGenerator.generatePDF(selectedMatch);
    }
    @FXML
    private void searchButtonAction(ActionEvent event) {
        String searchText = searchTextField.getText();
        searchMatches(searchText);
        System.out.println("Search text: " + searchText);
    }
    private void searchMatches(String searchText) {
        ObservableList<Match> searchResult = FXCollections.observableArrayList();
        System.out.println("Search matches for: " + searchText);
        System.out.println("Match list size: " + matchList.size());



        // Recherche par nom d'équipe, nom d'arbitre, type de match ou date
        for (Match match : matchList) {
            if (match.getEquipe1().getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                    match.getEquipe2().getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                    match.getArbitre().getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                    match.getType().toLowerCase().contains(searchText.toLowerCase()) ||
                    match.getDate().toString().contains(searchText)) {
                searchResult.add(match);
            }
        }

        matchListView.setItems(searchResult);
    }
    @FXML
    private void handleRateMatchButtonClick() {
        try {
            Match selectedMatch = matchListView.getSelectionModel().getSelectedItem();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RatingMatch.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de notation
            RatingMatchController ratingController = loader.getController();
            selectedMatch.setRating(ratingController.getRating());

            // Créer une nouvelle fenêtre modale pour la notation du match
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(400);

            stage.showAndWait();

            // Récupérer la note attribuée au match et mettre à jour les données du match
            int rating = ratingController.getRating();
            stage.close();

            // Mettre à jour la note du match dans votre modèle de données

            // Rafraîchir l'affichage pour refléter la nouvelle note
            refreshMatchDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshMatchDetails() {
        // Rafraîchir l'affichage des détails du match pour refléter les changements (y compris la note mise à jour)
    }
    @FXML
    private Button exportButton;

    // Méthode appelée lorsqu'on clique sur le bouton "Exporter"
    @FXML
    private void handleExportButtonAction() {
        List<Match> matches =  matchListView.getItems(); // Récupérer les matchs depuis la base de données
        ExcelGenerator.generateMatchExcel(matches); // Générer le fichier Excel des matchs
        System.out.println("Fichier Excel des matchs généré avec succès !");
    }
    @FXML
    void logout(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.sportify.HelloApplication.class.getResource("authentification-view.fxml"));
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
    void gotoProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.sportify.HelloApplication.class.getResource("user-profile-view.fxml"));
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
    void gotoReservation(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(com.example.sportify.HelloApplication.class.getResource("Reservation.fxml"));
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
    void gototerrain(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Terrain.fxml"));
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
    void gotoacceuil(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("UserAcceuilView.fxml"));
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






