package services;

import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceCategorie implements IServiceE<Categorie> {
    private Connection con;


    public ServiceCategorie() {
        con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (nom, description, image) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, categorie.getNom());
            pre.setString(2, categorie.getDescription());
            pre.setString(3, categorie.getImage());
            pre.executeUpdate();
        }
    }

    @Override
    public void modifier(Categorie categorie) throws SQLException {
        String req = "UPDATE categorie SET nom=?, description=?, image=? WHERE IDCateg=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, categorie.getNom());
            pre.setString(2, categorie.getDescription());
            pre.setString(3, categorie.getImage());
            pre.setInt(4, categorie.getID_Categ());
            pre.executeUpdate();
        }
    }

    /*@Override
    public void supprimer(Categorie categorie) throws SQLException {
        String req = "DELETE FROM categorie WHERE IDCateg = ?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, categorie.getID_Categ());
            pre.executeUpdate();
        }
    }*/
    @Override
    public void supprimer(Categorie categorie) throws SQLException {
        String checkEquipeQuery = "SELECT COUNT(*) FROM equipe WHERE IDCateg = ?";
        try (PreparedStatement checkEquipeStatement = con.prepareStatement(checkEquipeQuery)) {
            checkEquipeStatement.setInt(1, categorie.getID_Categ());
            try (ResultSet resultSet = checkEquipeStatement.executeQuery()) {
                if (resultSet.next()) {
                    int equipeCount = resultSet.getInt(1);
                    if (equipeCount > 0) {
                        throw new SQLException("Cannot delete the category as it is referenced by equipe records.");
                    }
                }
            }
        }


        String deleteCategoryQuery = "DELETE FROM categorie WHERE IDCateg = ?";
        try (PreparedStatement deleteCategoryStatement = con.prepareStatement(deleteCategoryQuery)) {
            deleteCategoryStatement.setInt(1, categorie.getID_Categ());
            deleteCategoryStatement.executeUpdate();
            System.out.println("Category deleted successfully.");
        }
    }



    @Override
    public ObservableList<Categorie> afficher() throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        String req = "SELECT * FROM categorie";
        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Categorie c = new Categorie();
                c.setIDCateg(res.getInt("IDCateg"));
                c.setNom(res.getString("Nom"));
                c.setDescription(res.getString("Description"));
                c.setImage(res.getString("Image"));
                categories.add(c);
            }
        }
        return FXCollections.observableArrayList(categories);
    }
    public Categorie getCategorieById(int id) throws SQLException {
        Categorie categorie = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sportify", "root", null);
            String query = "SELECT * FROM Categorie WHERE IDCateg = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                categorie = new Categorie(
                        resultSet.getInt("IDCateg"),
                        resultSet.getString("Nom"),
                        resultSet.getString("Description"),
                        resultSet.getString("Image")
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

        return categorie;
    }
    public List<String> afficherImages() throws SQLException {
        List<String> imagePaths = new ArrayList<>();
        String query = "SELECT Image FROM categorie";
        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String imagePath = resultSet.getString("Image");
                imagePaths.add(imagePath);
            }
        }
        return imagePaths;
    }


}
