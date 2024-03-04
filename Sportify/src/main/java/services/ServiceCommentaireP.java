package services;

import entities.Commentaire;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaireP implements IServiceP<Commentaire> {
    private Connection con;

    public ServiceCommentaireP() {
        con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String req = "INSERT INTO post ( ID_User, Date, Heure, description) VALUES (?, ?, ?, ?)";//
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, commentaire.getID_User());
            pre.setString(2, String.valueOf(commentaire.getDate()));
            pre.setString(3, String.valueOf(commentaire.getHeure()));
            pre.setString(4, commentaire.getDescription());
            pre.executeUpdate();
        }
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String req = "UPDATE post SET ID_User=?, Date=?, Heure=?, description=? WHERE ID_Post=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, commentaire.getID_User());
            pre.setString(2, String.valueOf(commentaire.getDate()));
            pre.setString(3, String.valueOf(commentaire.getHeure()));
            pre.setString(3, commentaire.getDescription());
            pre.setInt(4, commentaire.getID_commentaire());
            pre.executeUpdate();
        }
    }

    /*@Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        String req = "DELETE FROM commentaire WHERE ID_commentaire = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, commentaire.getID_commentaire());
            pre.executeUpdate();
        }
    }*/
    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        // Check if any commentaire records reference the post
        String checkCommentaireQuery = "SELECT COUNT(*) FROM commentaire WHERE ID_Post = ?";
        try (PreparedStatement checkCommentaireStatement = con.prepareStatement(checkCommentaireQuery)) {
            checkCommentaireStatement.setInt(1, commentaire.getID_commentaire());
            try (ResultSet resultSet = checkCommentaireStatement.executeQuery()) {
                if (resultSet.next()) {
                    int commentaireCount = resultSet.getInt(1);
                    if (commentaireCount > 0) {
                        // If commentaire records exist, throw an exception to indicate that deletion is not allowed
                        throw new SQLException("Cannot delete the post as it is referenced by commentaire records.");
                    }
                }
            }
        }

        // If no commentaire records reference the post, proceed with deletion
        String deletePostQuery = "DELETE FROM post WHERE ID_Post = ?";
        try (PreparedStatement deletePostStatement = con.prepareStatement(deletePostQuery)) {
            deletePostStatement.setInt(1, commentaire.getID_commentaire());
            deletePostStatement.executeUpdate();
            System.out.println("commentaire deleted successfully.");
        }
    }



    @Override
    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> posts = new ArrayList<>();
        String req = "SELECT * FROM commentaire";
        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Commentaire c = new Commentaire();
                c.setID_commentaire(res.getInt("ID_commentaire"));
                c.setID_User(res.getInt("ID_User"));
                c.setDate(LocalDate.parse(res.getString("Date")));
                c.setHeure(LocalTime.parse(res.getString("Heure")));
                Commentaire.add(c);
            }
        }
        return posts;
    }

    public Commentaire getByID(int commentaireId) throws SQLException {
        String query = "SELECT * FROM commentaire WHERE IDCommentaire = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, commentaireId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Commentaire commentaire = new Commentaire();
                    commentaire.setID_commentaire(resultSet.getInt("IDCommentaire"));
                    commentaire.setID_User(resultSet.getInt("IDUser"));
                    commentaire.setDate(LocalDate.parse(resultSet.getString("date")));
                    commentaire.setHeure(LocalTime.parse(resultSet.getString("heure")));
                    commentaire.setDescription(resultSet.getString("description"));
                    // Assuming you have a method to retrieve IDcom from Commentaire
                    Commentaire commentaire1 = new Commentaire();
                    commentaire1.setID_commentaire(resultSet.getInt("IDCommentaire"));



                    return commentaire;
                } else {
                    return null; // Commentaire with the specified ID not found
                }
            }
        }
    }
}
