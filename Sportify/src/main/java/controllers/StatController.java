package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import utils.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatController {
    private Connection connection;

    private Connection con= DB.getInstance().getConnection();

     private Statement ste;

    @FXML
    private PieChart pieChart;

    @FXML
    void afficherStatistique(ActionEvent event) {

    }

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            try (Statement ste = con.createStatement();
                 ResultSet resultSet = ste.executeQuery("SELECT  Nom, COUNT(*) FROM categorie GROUP BY nom")) {

                while (resultSet.next()) {
                    String nom = resultSet.getString("Nom");
                    int nombreEvenements = resultSet.getInt(2);

                    PieChart.Data slice = new PieChart.Data( nom + "  :", nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
}