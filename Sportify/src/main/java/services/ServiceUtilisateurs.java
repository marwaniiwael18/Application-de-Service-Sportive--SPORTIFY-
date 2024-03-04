package services;

import entities.Utilisateurs;
import utils.DB;

import java.sql.*;
import java.util.List;

public class ServiceUtilisateurs implements IServiceE<Utilisateurs> {

    private Connection con;

    public ServiceUtilisateurs() {
        con = DB.getInstance().getConnection();
    }

    public Utilisateurs retrieveUtilisateurByEmail(String email) throws SQLException {
        Utilisateurs utilisateurs = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", null);

            String query = "SELECT * FROM utilisateur WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateurs = new Utilisateurs(

                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("image"),
                        resultSet.getString("niveau_competence"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("date_de_naissance"),
                        resultSet.getBoolean("verified")
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

        return utilisateurs;
    }
    public Utilisateurs getUserByEmail(String email) throws SQLException {
        Utilisateurs utilisateurs = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", null);

            String query = "SELECT * FROM utilisateur WHERE email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateurs = new Utilisateurs(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("image"),
                        resultSet.getString("niveau_competence"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("date_de_naissance"),
                        resultSet.getBoolean("verified")

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

        return utilisateurs;
    }


    @Override
    public void ajouter(Utilisateurs utilisateurs) throws SQLException {

    }

    @Override
    public void modifier(Utilisateurs utilisateurs) throws SQLException {

    }

    @Override
    public void supprimer(Utilisateurs utilisateurs) throws SQLException {

    }

    @Override
    public List<Utilisateurs> afficher() throws SQLException {
        return null;
    }
}
