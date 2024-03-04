package com.example.sportify.controller;

import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceClassementEquipeX;
import services.ServiceClassementUserX;
import services.ServiceEquipeX;
import services.ServiceScoreX;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PropScoreInterfaceController implements Initializable {
    @FXML
    private Button annuler_btn;

    @FXML
    private Text equipe1;

    @FXML
    private Text equipe2;




   

    @FXML
    private Button save_btn;

    private Competition competsave ;

    @FXML
    private TextField scoreequipe1;

    @FXML
    private TextField scoreequipe2;

    private Equipe winner ;
    private Equipe loser ;
    private ServiceScoreX scoreService= new ServiceScoreX() ;

    private Score score ;

    private ServiceClassementEquipeX classementEquipeService = new ServiceClassementEquipeX(); // Initialize your service
    private ServiceClassementUserX classementUserService = new ServiceClassementUserX(); // Initialize your service
    private ServiceEquipeX EquipeMembreService = new ServiceEquipeX(); // Initialize your service


    @FXML
    void close(MouseEvent event) {
            Stage stage = (Stage) annuler_btn.getScene().getWindow();
            stage.close();
        }


    void setScore(Competition compet) {
        equipe1.setText(compet.getEquipe1().getNom());
        equipe2.setText(compet.getEquipe2().getNom());

        try {
            score = scoreService.getScoreByCompet(compet);
            if (score != null) {
                scoreequipe1.setText(String.valueOf(score.getEquipe1Score()));
                scoreequipe2.setText(String.valueOf(score.getEquipe2Score()));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        competsave=compet ;
    }

    @FXML
    void save(MouseEvent event) throws SQLException {


        if (scoreequipe1.getText().equals("-") || scoreequipe2.getText().equals("-")) {
            Alert alertt = new Alert(Alert.AlertType.ERROR);
            alertt.setHeaderText(null);
            alertt.setContentText("Please Fill All DATA");
            alertt.showAndWait();
        } else if (equipe1.getText().isEmpty() || scoreequipe2.getText().isEmpty() ) {
            Alert alertt = new Alert(Alert.AlertType.ERROR);
            alertt.setHeaderText(null);
            alertt.setContentText("Les équipes ne sont pas entrées dans le match");
            alertt.showAndWait(); }
        else {
            try {

                int scoreE1 = Integer.parseInt(scoreequipe1.getText());
                int scoreE2 = Integer.parseInt(scoreequipe2.getText());


                boolean isDraw = false;
                if (scoreE1 > scoreE2) {
                    winner = competsave.getEquipe1();
                    loser = competsave.getEquipe2();
                } else if (scoreE1 < scoreE2) {
                    winner = competsave.getEquipe2();
                    loser = competsave.getEquipe1();
                } else {
                    winner = null;
                    loser = null;
                    isDraw = true;
                }
                if (score == null) {
                    score = new Score(competsave, winner, loser, scoreE1, scoreE2, "", "");
                    scoreService.ajouter(score);
                } else {
                    Score scoree = new Score(score.getIdScore(), competsave, winner, loser, scoreE1, scoreE2, "", "");
                    // Update the existing score in the database
                    scoreService.modifier(scoree);
                }
                if  (competsave.getType() == "Equipe" ) {
                    updateClassement(winner, loser, isDraw);
                }
                else {
                    updateClassementUser(winner, loser, isDraw);
                }



                Stage stage = (Stage) save_btn.getScene().getWindow();
                stage.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Score Enregistrer ");
                alert.showAndWait();
            } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    }

   /* private void updateClassement(Equipe winner, Equipe loser, boolean isDraw) {
         boolean newwinner=true ;
         boolean newloser=true ;

        try {
            List<ClassementEquipe> AllClassement = classementEquipeService.afficher() ;
            for ( ClassementEquipe table:AllClassement) {
                if ( winner.getID() == table.getEquipe().getID()) {
                    newwinner=false ;
                }
                 if ( loser.getID() == table.getEquipe().getID()) {
                    newloser=false ;
                }
            }
            if (newwinner==true) {
                ClassementEquipe AddWinner= new ClassementEquipe(winner,3,1,1,1,0,0) ;
                classementEquipeService.ajouter(AddWinner);
            }
           if (newloser==true) {
                ClassementEquipe Addloser= new ClassementEquipe(loser,0,1,1,0,1,0) ;
                classementEquipeService.ajouter(Addloser);
            }



            List<ClassementEquipe> classementEquipeList = classementEquipeService.afficher();

            for (ClassementEquipe classementEquipe : classementEquipeList) {
                if (classementEquipe.getEquipe().getID() == winner.getID()) {
                    classementEquipe.setPoints(classementEquipe.getPoints() +  3);
                    classementEquipe.setWin(classementEquipe.getWin() + 1);
                    classementEquipe.setNbre_de_match(classementEquipe.getNbre_de_match() + 1);
                } else if (classementEquipe.getEquipe().getID() == loser.getID()) {
                    classementEquipe.setLoss(classementEquipe.getLoss() + 1);
                    classementEquipe.setNbre_de_match(classementEquipe.getNbre_de_match() + 1);

                } else if (isDraw) {
                    classementEquipe.setDraw(classementEquipe.getDraw() + 1);
                }

                // Calculate the rank based on points (you need to implement this logic)
                // Update the classementEquipe entry in the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }*/

    private void updateClassement(Equipe winner, Equipe loser, boolean isDraw) {
        try {
            // Check if winner and loser are already in the classement
            boolean newWinner = true;
            boolean newLoser = true;

            List<ClassementEquipe> allClassement = classementEquipeService.afficher();
            for (ClassementEquipe table : allClassement) {
                if (winner.getID() == table.getEquipe().getID()) {
                    newWinner = false;
                }
                if (loser.getID() == table.getEquipe().getID()) {
                    newLoser = false;
                }
            }

            // If winner is not in the classement, add them
            if (newWinner) {
                ClassementEquipe addWinner = new ClassementEquipe(winner, 0, 0, 0, 0, 0, 0);
                classementEquipeService.ajouter(addWinner);
            }

            // If loser is not in the classement, add them
            if (newLoser) {
                ClassementEquipe addLoser = new ClassementEquipe(loser, 0, 0, 0, 0, 0, 0);
                classementEquipeService.ajouter(addLoser);


            }
            else {

            // Update points, wins, and matches played for both winner and loser
            List<ClassementEquipe> classementEquipeList = classementEquipeService.afficher();
            for (ClassementEquipe classementEquipe : classementEquipeList) {
                if (classementEquipe.getEquipe().getID() == winner.getID()) {
                    classementEquipe.setPoints(classementEquipe.getPoints() + 3);
                    classementEquipe.setWin(classementEquipe.getWin() + 1);
                    classementEquipe.setNbre_de_match(classementEquipe.getNbre_de_match() + 1);
                } else if (classementEquipe.getEquipe().getID() == loser.getID()) {
                    classementEquipe.setLoss(classementEquipe.getLoss() + 1);
                    classementEquipe.setNbre_de_match(classementEquipe.getNbre_de_match() + 1);
                }

                // Calculate rank based on points
                // You may need to implement this logic
            }

            // Update ranks
            updateRanks(classementEquipeList);
        }} catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClassementUser(Equipe winner, Equipe loser, boolean isDraw) {
        try {
            // Check if winner and loser are already in the classement
            boolean newWinner = true;
            boolean newLoser = true;

            List<Utilisateur> playersWinner = EquipeMembreService.getEquipeMembres(winner);
            List<Utilisateur> playersLoser = EquipeMembreService.getEquipeMembres(loser);

            // For solo matches, handle each player individually
                for (Utilisateur playerW : playersWinner) {
                    newWinner = true; // Reset for each player
                    for (ClassementUser table : classementUserService.afficher()) {
                        if (playerW.getId() == table.getUser().getId()) {
                            newWinner = false;
                            break; // No need to continue checking if found
                        }
                    }
                    if (newWinner) {
                        ClassementUser addWinner = new ClassementUser(playerW, 0, 0, 0, 0, 0, 0);
                        classementUserService.ajouter(addWinner);
                    }
                }


                for (Utilisateur playerL : playersLoser) {
                    newLoser = true; // Reset for each player
                    for (ClassementUser table : classementUserService.afficher()) {
                        if (playerL.getId() == table.getUser().getId()) {
                            newLoser = false;
                            break; // No need to continue checking if found
                        }
                    }
                    if (newLoser) {
                        ClassementUser addLoser = new ClassementUser(playerL, 0, 0, 0, 0, 0, 0);
                        classementUserService.ajouter(addLoser);
                    }
                }

            List<ClassementUser> classementUserList = classementUserService.afficher();
            for (ClassementUser classementUser : classementUserList) {
                for (Utilisateur player : playersWinner) {
                    if (classementUser.getUser().getId() == player.getId()) {
                        classementUser.setPoints(classementUser.getPoints() + 3);
                        classementUser.setWin(classementUser.getWin() + 1);
                        classementUser.setNbre_de_match(classementUser.getNbre_de_match() + 1);
                    }
                }
                for (Utilisateur player : playersLoser) {
                    if (classementUser.getUser().getId() == player.getId()) {
                        classementUser.setLoss(classementUser.getLoss() + 1);
                        classementUser.setNbre_de_match(classementUser.getNbre_de_match() + 1);
                    }
                }
            }




            updateUserRanks(classementUserList);
            // Note: You may need to call updateRanks method if the ranks are not updated automatically.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRanks(List<ClassementEquipe> classementEquipeList) {
        // Sort teams by points in descending order
        classementEquipeList.sort(Comparator.comparingInt(ClassementEquipe::getPoints).reversed());

        // Update ranks based on sorted list
        int rank = 1;
        for (ClassementEquipe classementEquipe : classementEquipeList) {
            classementEquipe.setRank(rank++);
            try {
                classementEquipeService.modifier(classementEquipe);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUserRanks(List<ClassementUser> classementUserList) {
        // Sort users by points in descending order
        classementUserList.sort(Comparator.comparingInt(ClassementUser::getPoints).reversed());

        // Update ranks based on sorted list
        int rank = 1;
        for (ClassementUser classementUser : classementUserList) {
            classementUser.setRank(rank++);
            try {
                classementUserService.modifier(classementUser);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreequipe1.setText("-");
        scoreequipe2.setText("-");
    }

}
