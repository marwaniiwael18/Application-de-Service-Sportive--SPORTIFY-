package services;

import entities.Role;
import entities.Utilisateur;
import utils.BCryptPass;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ServicUtilisateur implements IServiceU<Utilisateur> {
    private Connection con;

    public ServicUtilisateur() {
        con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Utilisateur utilisateur) throws SQLException {
        String query = "INSERT INTO utilisateur (nom, prenom, mot_de_passe, email, image, adresse, niveau_competence, date_de_naissance, role,verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, utilisateur.getNom());
        pst.setString(2, utilisateur.getPrenom());
        pst.setString(3, BCryptPass.hashPass(utilisateur.getMot_de_passe()));
        pst.setString(4, utilisateur.getEmail());
        pst.setString(5, utilisateur.getImage());
        pst.setString(6, utilisateur.getAdresse());
        pst.setString(7, utilisateur.getNiveau_competence());
        pst.setDate(8, new java.sql.Date(utilisateur.getDate_de_naissance().getTime()));
        pst.setString(9, utilisateur.getRole().toString());
        pst.setBoolean(10, utilisateur.isVerified());

        pst.executeUpdate();

    }

    @Override
    public void modifier(int id, Utilisateur utilisateur) throws SQLException {
        String query = "UPDATE utilisateur SET nom=?, prenom=?, mot_de_passe=?, email=?, image=?, adresse=?, niveau_competence=?, date_de_naissance=?, role=?, verified=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, utilisateur.getNom());
        pst.setString(2, utilisateur.getPrenom());

        pst.setString(3, utilisateur.getMot_de_passe());
        pst.setString(4, utilisateur.getEmail());
        pst.setString(5, utilisateur.getImage());
        pst.setString(6, utilisateur.getAdresse());
        pst.setString(7, utilisateur.getNiveau_competence());
        pst.setDate(8, new java.sql.Date(utilisateur.getDate_de_naissance().getTime()));
        pst.setString(9, utilisateur.getRole().toString());
        pst.setBoolean(10, utilisateur.isVerified());
        pst.setInt(11, id);

        pst.executeUpdate();

    }


    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM utilisateur WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, id);
            pre.executeUpdate();
        }
    }

    @Override
    public List<Utilisateur> afficher() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setMot_de_passe(rs.getString("mot_de_passe")); // Consider security implications
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setImage(rs.getString("image"));
                utilisateur.setAdresse(rs.getString("adresse"));
                utilisateur.setNiveau_competence(rs.getString("niveau_competence"));
                utilisateur.setDate_de_naissance(rs.getDate("date_de_naissance"));
                utilisateur.setRole(Role.valueOf(rs.getString("role")));
                utilisateur.setVerified(rs.getBoolean("verified"));

                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des utilisateurs: " + e.getMessage());
        }
        return utilisateurs;
    }
    public Utilisateur getById(int id)throws SQLException{
        return afficher().stream().filter(u->u.getId()==id).findFirst().orElse(null);
    }
    public Utilisateur getByEmail(String email)throws SQLException{
        return afficher().stream().filter(u->u.getEmail().equals(email)).findFirst().orElse(null);
    }
    public Utilisateur login(String email, String password) throws SQLException {
        List<Utilisateur> utilisateurs = afficher();

        return utilisateurs.stream()
                .filter(u -> u.getEmail().equals(email) && BCryptPass.checkPass(password,u.getMot_de_passe()))
                .findFirst()
                .orElse(null);
    }


    public Utilisateur authentifier(String email, String password) throws SQLException {
        /*String req = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, email);
            pre.setString(2, password);

            try (ResultSet result = pre.executeQuery()) {
                if (result.next()) {
                    return mapUtilisateur(result);
                } else {
                    return null;
                }
            }
        }*/
        return null;
    }

    public List<Utilisateur> triParCritere(String critere) throws SQLException {
        switch (critere){
            case "Nom":
                return afficher().stream().sorted(Comparator.comparing(Utilisateur::getNom)).collect(Collectors.toList());
            case "Prenom":
                return afficher().stream().sorted((u1,u2)->u1.getPrenom().compareTo(u2.getPrenom())).collect(Collectors.toList());

            case "Email":
                return afficher().stream().sorted((u1,u2)->u1.getEmail().compareTo(u2.getEmail())).collect(Collectors.toList());
            case "Date de naissance":
                return afficher().stream().sorted((u1,u2)->u1.getDate_de_naissance().compareTo(u2.getDate_de_naissance())).collect(Collectors.toList());

        }
        return null;
    }
    public Utilisateur retrieveUtilisateurByEmail(String email) throws SQLException {
        Utilisateur utilisateur = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", null);

            String query = "SELECT * FROM Utilisateur WHERE Email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("image"),
                        resultSet.getString("niveau_competence")

                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return utilisateur;
    }
    public Utilisateur getUserByEmail(String email) throws SQLException {
        Utilisateur utilisateur = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", null);

            String query = "SELECT * FROM Utilisateur WHERE Email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Prenom"),
                        resultSet.getString("Email"),
                        resultSet.getString("Mot_de_passe"),
                        resultSet.getString("Image"),
                        resultSet.getString("Niveau_competence")

                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return utilisateur;
    }


}
