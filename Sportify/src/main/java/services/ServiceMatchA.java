package services;

import entities.Arbitre;
import entities.Equipe;
import entities.Match;
import entities.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMatchA implements IServiceA<Match> {
    private Connection con;
    private ServiceArbitreA s;
    public ServiceMatchA(){
        con = utils.DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Match match) throws SQLException {
        String req = "INSERT INTO `matc` (nom, type, description, date, heure, equipe1, equipe2,arbitre) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setString(1, match.getNom());
        pstmt.setString(2, match.getType());
        pstmt.setString(3, match.getDescription());
        pstmt.setDate(4, match.getDate());
        pstmt.setTime(5, match.getHeure());
        pstmt.setInt(6, match.getEquipe1().getID());
        pstmt.setInt(7, match.getEquipe2().getID());
        pstmt.setInt(8, match.getArbitre().getId_arbitre());


        pstmt.executeUpdate();
    }

    @Override
    public void modifier(Match match) throws SQLException {
        String req = "UPDATE `matc` " +
                "SET nom=?, type=?, description=?, date=?, heure=?, equipe1=?, equipe2=? ,arbitre=? " +
                "WHERE ID_Matc=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, match.getNom());
        pre.setString(2, match.getType());
        pre.setString(3, match.getDescription());
        pre.setDate(4, match.getDate());
        pre.setTime(5, match.getHeure());
        pre.setInt(6, match.getEquipe1().getID());
        pre.setInt(7, match.getEquipe2().getID());
        pre.setInt(8, match.getArbitre().getId_arbitre());
        pre.setInt(9, match.getID_Matc());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(Match match) throws SQLException {
        String req = "DELETE FROM `matc` WHERE ID_Matc = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, match.getID_Matc());
        pre.executeUpdate();
    }
    public Equipe getEquipeById(int equipeId) throws SQLException {
        Equipe equipe = null;
        String req = "SELECT * FROM equipe WHERE IDEquipe = ?";
        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setInt(1, equipeId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            equipe = new Equipe();
            equipe.setID(rs.getInt("IDEquipe"));
            equipe.setNom(rs.getString("nom"));

        }

        return equipe;
    }


    @Override
    public List<Match> afficher() throws SQLException {
        List<Match> matches = new ArrayList<>();


            String query = "SELECT * FROM matc";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID_Matc");
                        String nom = resultSet.getString("nom");
                        String type = resultSet.getString("type");
                        String description = resultSet.getString("description");
                        Date date = resultSet.getDate("date");
                        Time heure = resultSet.getTime("heure");
                       Equipe equipe1Nom= getTeamName(resultSet.getInt("Equipe1"));
                        Equipe equipe2Nom= getTeamName(resultSet.getInt("Equipe1"));
                        Arbitre arbitre= getRefereeName(resultSet.getInt("Arbitre"));







                        Match match = new Match(id, nom, type, description, date, heure, equipe1Nom, equipe2Nom,  arbitre);
                        matches.add(match);
                    }
                }
            }


        return matches;
    }
    private Equipe getTeamName(int teamId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
        String query = "SELECT Nom,Niveau,`rank`,id_createur FROM equipe WHERE IDEquipe = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                Equipe equipe = new Equipe();
                equipe.setID(resultSet.getInt("IDEquipe"));
                equipe.setNom(resultSet.getString("Nom"));
                equipe.setNiveau(resultSet.getString("Niveau"));
                equipe.setRank(resultSet.getInt("Rank"));
                int id_createur = resultSet.getInt("id_createur");

                // Utiliser la méthode getUserById pour obtenir l'objet Utilisateur correspondant à id_createur
                Utilisateur createur = getUserById(id_createur);
                return equipe;

            }
        }
        return null;
    }

    private Arbitre getRefereeName(int refereeId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
        String query = "SELECT Age,Nom,Prenom FROM arbitre WHERE id_arbitre = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, refereeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Arbitre arbitre =new Arbitre();
                arbitre.setAge(resultSet.getInt("Age"));
                arbitre.setNom(resultSet.getString("Nom"));
                arbitre.setPrenom(resultSet.getString("Prenom"));


                return arbitre;
            }
        }
        return null;
    }


    public List<Equipe> getAllEquipes() throws SQLException {
        List<Equipe> equipes = new ArrayList<>();
        String query = "SELECT * FROM equipe";
        try (        PreparedStatement pstmt = con.prepareStatement(query);

                     ResultSet resultSet = pstmt.executeQuery(query)) {
            while (resultSet.next()) {
                Equipe equipe = new Equipe();
                equipe.setID( resultSet.getInt("IDEquipe"));
                equipe.setNom(resultSet.getString("Nom"));
                equipe.setNiveau(resultSet.getString("Niveau"));
                equipe.setRank(resultSet.getInt("Rank"));

                int id_createur = resultSet.getInt("id_createur");

                // Utiliser la méthode getUserById pour obtenir l'objet Utilisateur correspondant à id_createur
                Utilisateur createur = getUserById(id_createur);
                equipe.setId_createurs(createur);


                equipes.add(equipe);
            }
        }
        return equipes;
    }

    public Utilisateur getUserById(int userId) {
        // Code pour récupérer l'utilisateur en fonction de son ID depuis votre source de données (base de données, fichier, etc.)
        // Par exemple, si vous utilisez une base de données :
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilisateur WHERE id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Créer un objet Utilisateur avec les détails récupérés de la base de données
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setEmail(resultSet.getString("email"));
                // Définir d'autres attributs de l'utilisateur selon votre structure de base de données

                return utilisateur;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions appropriées
        }

        return null; // Retourner null si aucun utilisateur n'est trouvé avec l'ID donné
    }


}
