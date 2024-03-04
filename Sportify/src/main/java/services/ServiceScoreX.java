package services;

import entities.Competition;
import entities.Equipe;
import entities.Score;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceScoreX implements IServiceX<Score> {

    private Connection con ;

    public ServiceScoreX() {
        this.con = DB.getInstance().getConnection();
    }



    @Override
    public void ajouter(Score score) throws SQLException {
        String query = "INSERT INTO Score (competitionId, winnerId, loserId, equipe1Score, equipe2Score, resultat, reclamation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, score.getCompetition().getID_competiton());
            statement.setInt(2, score.getWinner().getID());
            statement.setInt(3, score.getLoser().getID());
            statement.setInt(4, score.getEquipe1Score());
            statement.setInt(5, score.getEquipe2Score());
            statement.setString(6, score.getResultat());
            statement.setString(7, score.getReclamation());
            statement.executeUpdate();
        }

    }

    @Override
    public void modifier(Score score) throws SQLException {
        String query = "UPDATE Score SET competitionId=?, winnerId=?, loserId=?, equipe1Score=?, equipe2Score=?, resultat=?, reclamation=? WHERE idScore=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, score.getCompetition().getID_competiton());
            statement.setInt(2, score.getWinner().getID());
            statement.setInt(3, score.getLoser().getID());
            statement.setInt(4, score.getEquipe1Score());
            statement.setInt(5, score.getEquipe2Score());
            statement.setString(6, score.getResultat());
            statement.setString(7, score.getReclamation());
            statement.setInt(8, score.getIdScore());
            statement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM Score WHERE idScore=?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void modifiert(int id, Score score) throws SQLException {

    }

    @Override
    public Score authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Score> afficher() throws SQLException {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT * FROM Score";
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Score score = new Score();

                 score.setIdScore(resultSet.getInt("idScore"));

                /*CompetitionService competitionService = new CompetitionService() ;
                //Competition compet=competitionService.getById(resultSet.getInt("competitionId"));
                score.setCompetition(compet); */

                ServiceEquipeX EquipeService = new ServiceEquipeX() ;
                Equipe winner=EquipeService.getById(resultSet.getInt("winnerId"));
                Equipe loser=EquipeService.getById(resultSet.getInt("loserId"));
                score.setWinner(winner);
                score.setLoser(loser);

                 score.setEquipe1Score(resultSet.getInt("equipe1Score"));
                 score.setEquipe2Score(resultSet.getInt("equipe2Score"));
                 score.setResultat(resultSet.getString("resultat"));
                 score.setReclamation(resultSet.getString("reclamation"));
                scores.add(score);
            }
        }
        return scores;
    }

    public Score getScoreByCompet(Competition compet) throws SQLException {
        Score score = null;
        String query = "SELECT * FROM score WHERE competitionId = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, compet.getID_competiton());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    score = new Score();
                    score.setIdScore(resultSet.getInt("idScore"));
                    score.setCompetition(compet);
                    ServiceEquipeX EquipeService = new ServiceEquipeX() ;
                    Equipe winner=EquipeService.getById(resultSet.getInt("winnerId"));
                    Equipe loser=EquipeService.getById(resultSet.getInt("loserId"));
                    score.setWinner(winner);
                    score.setLoser(loser);
                    score.setEquipe1Score(resultSet.getInt("equipe1Score"));
                    score.setEquipe2Score(resultSet.getInt("equipe2Score"));
                    score.setResultat(resultSet.getString("resultat"));
                    score.setReclamation(resultSet.getString("reclamation"));
                }
            }
        }
        return score;
    }


}
