package services;

import entities.Categorie;
import entities.Equipe;
import entities.Utilisateurs;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipe implements IServiceE<Equipe> {
    private Connection con;

    public ServiceEquipe() {
        con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Equipe equipe) throws SQLException {
        String req = "INSERT INTO equipe (nom, niveau, IDCateg, isRandom, rank) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            pre.setString(1, equipe.getNom());
            pre.setString(2, equipe.getNiveau());
            pre.setInt(3, equipe.getIDCateg().getID_Categ());
            pre.setBoolean(4, equipe.getRandom());
            pre.setInt(5, equipe.getRank());
            pre.executeUpdate();
            try (ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int equipeId = generatedKeys.getInt(1);
                    equipe.setID(equipeId);
                } else {
                    throw new SQLException("Creating Equipe failed, no ID obtained.");
                }
            }
        }
    }



    @Override
    public void supprimer(Equipe equipe) throws SQLException {
        String req = "DELETE FROM equipe WHERE IDEquipe = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, equipe.getID());
            pre.executeUpdate();
        }
    }


    @Override
    public void modifier(Equipe equipe) throws SQLException {
        String req = "UPDATE equipe SET nom=?, niveau=?, IDCateg=?, isRandom=?, rank=? WHERE IDEquipe=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, equipe.getNom());
            pre.setString(2, equipe.getNiveau());
            pre.setInt(3, equipe.getIDCateg().getID_Categ());
            pre.setBoolean(4, equipe.getRandom());
            pre.setInt(5, equipe.getRank());
            pre.setInt(6, equipe.getID());
            pre.executeUpdate();
        }
    }

    public List<Equipe> afficher() throws SQLException {
        List<Equipe> eq = new ArrayList<>();
        String req = "SELECT e.*, c.* " +
                "FROM equipe e " +
                "INNER JOIN categorie c ON e.IDCateg = c.IDCateg " ;
        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Equipe e = new Equipe();
                e.setID(res.getInt("IDEquipe"));
                e.setNom(res.getString("Nom"));
                e.setNiveau(res.getString("Niveau"));
                e.setRank(res.getInt("rank"));
                e.setRandom(res.getBoolean("isRandom"));
/*
                Utilisateur createur = new Utilisateur();
                createur.setID_User(res.getInt("ID_User"));
                createur.setNom(res.getString("Nom"));
                createur.setPrenom(res.getString("Prenom"));
                createur.setEmail(res.getString("Email"));
                createur.setMot_de_passe(res.getString("Mot_de_passe"));
                createur.setImage(res.getString("Image"));
                createur.setNiveau_competence(res.getString("Niveau_competence"));
                createur.setRole(res.getString("Role"));
                createur.setAdresse(res.getString("Adresse"));
                createur.setSexe(res.getString("Sexe"));


                e.setId_createur(createur);
*/
                Categorie categorie = new Categorie();
                categorie.setIDCateg(res.getInt("IDCateg"));
                categorie.setNom(res.getString("Nom"));
                categorie.setDescription(res.getString("Description"));
                categorie.setImage(res.getString("Image"));

                e.setIDCateg(categorie);

                eq.add(e);
            }
        }
        return eq;
    }
    public Equipe getByID(int equipeId) throws SQLException {
        String query = "SELECT * FROM equipe WHERE IDEquipe = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, equipeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Equipe equipe = new Equipe();
                    equipe.setID(resultSet.getInt("IDEquipe"));
                    equipe.setNom(resultSet.getString("Nom"));
                    equipe.setNiveau(resultSet.getString("Niveau"));
                    equipe.setRandom(resultSet.getBoolean("isRandom"));
                    equipe.setRank(resultSet.getInt("rank"));
                    Categorie categorie = new Categorie();
                    categorie.setIDCateg(resultSet.getInt("IDCateg"));
                    equipe.setIDCateg(categorie);
                    Utilisateurs createur = new Utilisateurs();
                    createur.setId(resultSet.getInt("id_createur"));


                    return equipe;
                } else {
                    return null;
                }
            }
        }
    }
    public int getCategoryID(Equipe equipe) throws SQLException {
        int idCategorie = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DB.getInstance().getConnection();
            String query = "SELECT IDCateg FROM Equipe WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, equipe.getIDCateg().getID_Categ());
            rs = stmt.executeQuery();

            if (rs.next()) {
                idCategorie = rs.getInt("IDCateg");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return idCategorie;
    }


}
