package services;

import entities.Equipe;
import entities.Utilisateur;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipeX implements IServiceX<Equipe> {

    private Connection con ;

    public ServiceEquipeX() {
        this.con = DB.getInstance().getConnection();
    }


    @Override
    public void ajouter(Equipe equipe) throws SQLException {
        String req = "INSERT INTO equipe (nom, niveau, IDCateg, isRandom, rank) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            pre.setString(1, equipe.getNom());
            pre.setString(2, equipe.getNiveau());
           // pre.setInt(3, equipe.getIDCateg().getID_Categ());
            pre.setBoolean(4, equipe.getRandom());
            pre.setInt(5, equipe.getRank());
            pre.executeUpdate();

            // Retrieve the generated keys (including id) after insertion
            try (ResultSet generatedKeys = pre.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int equipeId = generatedKeys.getInt(1); // Get the generated id for the Equipe
                    equipe.setID(equipeId); // Set the id of the Equipe object
                } else {
                    throw new SQLException("Creating Equipe failed, no ID obtained.");
                }
            }
        }


    }

    @Override
    public void modifier(Equipe equipe) throws SQLException {

    }

    @Override
    public void supprimer(int t) throws SQLException {

        String req = "DELETE FROM equipe WHERE IDEquipe = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, t);
            pre.executeUpdate();
        }
    }

    @Override
    public void modifiert(int id, Equipe equipe) throws SQLException {

    }

    @Override
    public Equipe authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Equipe> afficher() throws SQLException {
        return null;
    }


    public List<Equipe> afficherNom() throws SQLException {
            List<Equipe> equipes = new ArrayList<>() ;
            String query = "SELECT IDEquipe , Nom FROM Equipe ";
            try (PreparedStatement statement = con.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Equipe equipe = new Equipe();
                        equipe.setID(resultSet.getInt("IDEquipe"));
                        equipe.setNom(resultSet.getString("Nom"));

                        equipes.add(equipe) ;
                    }
                }
            }
            return equipes;

    }
    public Equipe getById(int id) throws SQLException {
        Equipe equipe = null;
        String query = "SELECT * FROM Equipe WHERE IDEquipe = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    equipe = new Equipe();
                    equipe.setID(resultSet.getInt("IDEquipe"));
                    equipe.setNom(resultSet.getString("Nom"));
                }
            }
        }
        return equipe;
    }


    public void ajouterMembre(Equipe equipe, Utilisateur utilisateur) throws SQLException {
        String req = "INSERT INTO equipe_members (equipe_id, user_id) VALUES (?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, equipe.getID());
            pre.setInt(2, utilisateur.getId());
            pre.executeUpdate();
        }
    }
    public void supprimerMembre(Equipe equipe, Utilisateur utilisateur) throws SQLException {
        String req = "DELETE FROM equipe_members WHERE equipe_id = ? AND user_id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, equipe.getID());
            pre.setInt(2, utilisateur.getId());
            pre.executeUpdate();
        }
    }

    public List<Utilisateur> getEquipeMembres(Equipe equipe) throws SQLException {
        List<Utilisateur> membres = new ArrayList<>();
        String req = "SELECT u.* FROM equipe_members em " +
                "JOIN utilisateur u ON em.user_id = u.id " +
                "WHERE em.equipe_id = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, equipe.getID());
            try (ResultSet res = pre.executeQuery()) {
                while (res.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(res.getInt("id"));
                    utilisateur.setNom(res.getString("Nom"));
                    // Set other user properties as needed
                    membres.add(utilisateur);
                }
            }
        }
        return membres;
    }

}