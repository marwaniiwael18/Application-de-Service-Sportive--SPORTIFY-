package com.example.sportify.controller;

import entities.Competition;
import entities.Terrain;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.CompetitionServiceX;
import services.ServiceTerrainX;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class Addcompetiton implements Initializable {

    @FXML
    private DatePicker Datee;

    @FXML
    private TextField DescText;

    @FXML
    private ChoiceBox<String> TypeChoice;

    @FXML
    private Button annuler_btn;

    @FXML
    private ChoiceBox<Integer> hhChoice;

    @FXML
    private ChoiceBox<Integer> mmChoice;

    @FXML
    private MFXTextField nomText;
    @FXML
    private Button save_btn;



    @FXML
    private AnchorPane Pane;


    private CompetitionController competitionController;

    @FXML
    private ChoiceBox<String> terrainliste;
    private boolean update ;

    private int competitonId ;
    CompetitionServiceX competitionService = new CompetitionServiceX();
    ServiceTerrainX TerrainService = new ServiceTerrainX();
    // Create an instance of CompetitionService
    private Map<String, Terrain> terrainMap = new HashMap<>();

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) annuler_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) {



        if (nomText.getText().isEmpty() || DescText.getText().isEmpty() || TypeChoice.getValue()==null || terrainliste.getValue()==null || Datee.getValue()==null)  {
                Alert alertt = new Alert(Alert.AlertType.ERROR);
                alertt.setHeaderText(null);
                alertt.setContentText("Please Fill All DATA");
                alertt.showAndWait(); }
        else {
            try {


                String selectedNomTerrain=terrainliste.getValue() ;
                Terrain selectedTerrain = terrainMap.get(selectedNomTerrain) ;

                int selectedHh = hhChoice.getValue();
                int selectedMm = mmChoice.getValue();


                LocalTime selectedTime = LocalTime.of(selectedHh, selectedMm);

                Competition compet = new Competition(nomText.getText(), DescText.getText(), TypeChoice.getValue(), Time.valueOf(selectedTime), Date.valueOf(Datee.getValue()),selectedTerrain);
                Competition competedit = new Competition(competitonId, nomText.getText(), DescText.getText(), TypeChoice.getValue(), Time.valueOf(selectedTime), Date.valueOf(Datee.getValue()),selectedTerrain);
                if (update == false) {
                    competitionService.ajouter(compet);


                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("competition ajoute");
                    alert.showAndWait();
                    clean();

                } else {
                    competitionService.modifier(competedit);
                    Stage stage = (Stage) save_btn.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Match Competition est modifiée");
                    alert.showAndWait();
                    clean();
                }

                competitionController.loadAll();


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    private void clean() {
        DescText.setText("");
        Datee.setValue(null);
        TypeChoice.setValue(null);
        nomText.setText("");

    }


    void setTextField(int id ,  String nom , String desc , String Type ,int hh,int mm , Date date,String TerrainNomEdit) {
        competitonId=id ;
        nomText.setText(nom);
        DescText.setText(desc);
        TypeChoice.setValue(Type);
        Datee.setValue(date.toLocalDate());
        terrainliste.setValue(TerrainNomEdit) ;
        hhChoice.setValue(hh);
        mmChoice.setValue(mm);
    }

    public void setCompetitionController(CompetitionController competitionController) {
        this.competitionController=competitionController ;
    }

    public void setUpdate( boolean bo) {this.update=bo ; }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for ( int i=0 ; i<60 ; i++) {
            mmChoice.getItems().add(i) ;
        }
        for ( int i=0 ; i<24 ; i++) {
            hhChoice.getItems().add(i) ;
        }
        String[] Types = {"SOLO" , "EQUIPE"} ;
        TypeChoice.getItems().addAll(Types) ;
        try {
            List<Terrain> terrains = TerrainService.afficherNom();
            /*List<String> terrainNoms = terrains.stream()
                    .map(Terrain::getNomTerrain)
                    .collect(Collectors.toList());*/
            for ( Terrain terrain: terrains) {
                String terrainNom=terrain.getNomTerrain()  ;
                terrainliste.getItems().add(terrainNom);
                terrainMap.put(terrainNom , terrain) ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Datee.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }







}
