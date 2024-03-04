package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class DisplayEquipeController {

    @FXML
    private ListView<String> membersListView;
    @FXML
    private Button bntCreate;
    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;
    private ObservableList<String> allMembers; // Store all members

    @FXML
    private Button btnAfficher;

    @FXML
    private MenuButton menuSports;

    @FXML
    private Text tfListe;

    @FXML
    private TextField tfMembre10;

    @FXML
    private TextField tfMembre11;

    @FXML
    private TextField tfMembre3;

    @FXML
    private TextField tfMembre4;

    @FXML
    private TextField tfMembre5;

    @FXML
    private TextField tfMembre6;

    @FXML
    private TextField tfMembre7;

    @FXML
    private TextField tfMembre8;

    @FXML
    private TextField tfMembre9;

    @FXML
    private Text tfNom;

    @FXML
    private Text tfNomEquipe;

    @FXML
    private TextField tfmembre1;

    @FXML
    private TextField tfmembre2;

    @FXML
    private VBox vbox1;


    @FXML
    private Text membersText;
    @FXML
    private Button bntRetour;
    @FXML
    private Button exporter;

    public void initData(String teamName, String[] members) {
        tfNomEquipe.setText("Team Name: " + teamName);
        ObservableList<String> observableList = FXCollections.observableArrayList(members);
        membersListView.setItems(observableList);
    }
    public void setData(String teamName, String[] members) {
        tfNomEquipe.setText("Team Name: " + teamName);
        ObservableList<String> observableList = FXCollections.observableArrayList(members);
        membersListView.setItems(observableList);
    }
    @FXML
    void Retourner(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Equipes.fxml"));
        try {
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
    void search(ActionEvent event) {
        String searchText = searchTextField.getText().toLowerCase().trim();

        // Check if allMembers is null and initialize it if necessary
        if (allMembers == null) {
            allMembers = FXCollections.observableArrayList();
        }

        // Filter members based on search text
        List<String> filteredMembers = allMembers.stream()
                .filter(member -> member.toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Update the ListView with filtered members
        membersListView.setItems(FXCollections.observableArrayList(filteredMembers));
    }

    public void exportToPdf(ActionEvent actionEvent) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            if (printerJob.showPrintDialog(membersListView.getScene().getWindow())) {
                PageLayout pageLayout = printerJob.getPrinter().getDefaultPageLayout();
                double scaleX = pageLayout.getPrintableWidth() / membersListView.getBoundsInParent().getWidth();
                double scaleY = pageLayout.getPrintableHeight() / membersListView.getBoundsInParent().getHeight();
                double scale = Math.min(scaleX, scaleY);
                Scale printScale = new Scale(scale, scale);
                membersListView.getTransforms().add(printScale);
                boolean success = printerJob.printPage(membersListView);
                if (success) {
                    System.out.println("Le PDF a été téléchargé avec succès !");
                    printerJob.endJob();
                }else{  System.out.println("Une erreur s'est produite lors du téléchargement du PDF.");}

                membersListView.getTransforms().remove(printScale); // Réinitialiser la transformation après l'impression
            }
        }
    }
}







