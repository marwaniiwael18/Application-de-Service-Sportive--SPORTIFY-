package controllers;

import entities.Match;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

class MatchListCell extends ListCell<Match> {
    @Override
    protected void updateItem(Match item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            String format = "%-10s | %-15s | %-15s | %-13s | %-30s | %-20s | %-30s | %-20s";
            String content = String.format(format, item.getNom(), item.getType(), item.getDate(), item.getHeure(), item.getDescription(), item.getEquipe1().toString1(), item.getEquipe2().toString1(), item.getArbitre().toString1());
            setText(content);
            setGraphic(null);
        }
    }

    @FXML
    private Button modifierButton;

    @FXML
    private Button supprimerButton;



}
