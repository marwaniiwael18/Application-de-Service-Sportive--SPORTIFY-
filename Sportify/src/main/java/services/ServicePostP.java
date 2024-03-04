package services;

import entities.Post;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServicePostP implements IServiceP<Post> {
    private Connection con;

    public ServicePostP() {
        con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String req = "INSERT INTO post ( ID_User, Date, Heure, description) VALUES (?, ?, ?, ?)";//
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getID_User());
            pre.setString(2, String.valueOf(post.getDate()));
            pre.setString(3, String.valueOf(post.getHeure()));
            pre.setString(4, post.getDescription());
            pre.executeUpdate();
        }
    }

    @Override
    public void modifier(Post post) throws SQLException {
        String req = "UPDATE post SET ID_User=?, Date=?, Heure=?, description=? WHERE ID_Post=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getID_User());
            pre.setString(2, String.valueOf(post.getDate()));
            pre.setString(3, String.valueOf(post.getHeure()));
            pre.setString(3, post.getDescription());
            pre.setInt(4, post.getID_post());
            pre.executeUpdate();
        }
    }

    /*@Override
    public void supprimer(Post post) throws SQLException {
        String req = "DELETE FROM post WHERE ID_post = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, post.getID_post());
            pre.executeUpdate();
        }
    }*/
    @Override
    public void supprimer(Post post) throws SQLException {
        // Check if any post records reference the post
        String checkpostQuery = "SELECT COUNT(*) FROM post WHERE ID_Post = ?";
        try (PreparedStatement checkpostStatement = con.prepareStatement(checkpostQuery)) {
            checkpostStatement.setInt(1, post.getID_post());
            try (ResultSet resultSet = checkpostStatement.executeQuery()) {
                if (resultSet.next()) {
                    int postCount = resultSet.getInt(1);
                    if (postCount > 0) {
                        // If post records exist, throw an exception to indicate that deletion is not allowed
                        throw new SQLException("Cannot delete the post as it is referenced by post records.");
                    }
                }
            }
        }

        // If no post records reference the post, proceed with deletion
        String deletePostQuery = "DELETE FROM post WHERE ID_Post = ?";
        try (PreparedStatement deletePostStatement = con.prepareStatement(deletePostQuery)) {
            deletePostStatement.setInt(1, post.getID_post());
            deletePostStatement.executeUpdate();
            System.out.println("post deleted successfully.");
        }
    }



    @Override
    public List<Post> afficher() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";
        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Post p = new Post();
                p.setID_post(res.getInt("ID_post"));
                p.setID_User(res.getInt("ID_User"));
                p.setDate(LocalDate.parse(res.getString("Date")));
                p.setHeure(LocalTime.parse(res.getString("Heure")));
                Post.add(p);
            }
        }
        return posts;
    }

    public Post getByID(int postId) throws SQLException {
        String query = "SELECT * FROM post WHERE IDpost = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Post post = new Post();
                    post.setID_post(resultSet.getInt("IDpost"));
                    post.setID_User(resultSet.getInt("IDUser"));
                    post.setDate(LocalDate.parse(resultSet.getString("date")));
                    post.setHeure(LocalTime.parse(resultSet.getString("heure")));
                    post.setDescription(resultSet.getString("description"));
                    // Assuming you have a method to retrieve IDcom from post
                    Post post1 = new Post();
                    post1.setID_post(resultSet.getInt("IDpost"));



                    return post;
                } else {
                    return null; // post with the specified ID not found
                }
            }
        }
    }
}
