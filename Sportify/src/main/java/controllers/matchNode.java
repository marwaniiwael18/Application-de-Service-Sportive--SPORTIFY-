package controllers;

import entities.Match;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class matchNode extends HBox {
    public matchNode(Match match) {
        // Créer les éléments de votre représentation de match
        Label nomLabel = new Label(match.getNom());
        Label typeLabel = new Label(match.getType());
        Label dateLabel = new Label("" + match.getDate());
        Label heureLabel = new Label("" + match.getHeure());
        Label descriptionLabel = new Label(match.getDescription());
        Label equipe1Label = new Label("" + match.getEquipe1());
        Label equipe2Label = new Label("" + match.getEquipe2());
        Label arbitreLabel = new Label("" + match.getArbitre());

        // Ajouter les éléments à votre représentation de match
        getChildren().addAll(nomLabel, typeLabel, dateLabel, heureLabel, descriptionLabel, equipe1Label, equipe2Label, arbitreLabel);
    }

}
