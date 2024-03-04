package services;

import entities.Arbitre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArbitreA implements IServiceA<Arbitre> {
    String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";

    private Connection con;
    public ServiceArbitreA(){con = utils.DB.getInstance().getConnection();}


    @Override
    public void ajouter(Arbitre arbitre) throws SQLException {
        String query = "INSERT INTO arbitre (age, nom, prenom, email, phone) VALUES (?, ?, ?, ?, ?)";


             PreparedStatement statement = con.prepareStatement(query) ;
            statement.setInt(1, arbitre.getAge());
            statement.setString(2, arbitre.getNom());
            statement.setString(3, arbitre.getPrenom());
            statement.setString(4, arbitre.getEmail());
            statement.setString(5, arbitre.getPhone());;

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Arbitre inséré avec succès !");
            }


    }

    @Override
    public void modifier(Arbitre arbitre) throws SQLException {
        String query = "UPDATE arbitre SET Age=?, Nom=?, Prenom=?, Email=?, Phone=? WHERE id_arbitre=?";
             PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, arbitre.getAge());
            statement.setString(2, arbitre.getNom());
            statement.setString(3, arbitre.getPrenom());
            statement.setString(4, arbitre.getEmail());
            statement.setString(5, arbitre.getPhone());
            statement.setInt(6, arbitre.getId_arbitre());
            System.out.println(query);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Arbitre mis à jour avec succès !");
            }

    }

    @Override
    public void supprimer(Arbitre arbitre) throws SQLException {
        String query = "DELETE FROM arbitre WHERE id_arbitre=?";
        PreparedStatement pre = con.prepareStatement(query);
        pre.setInt(1, arbitre.getId_arbitre());
        pre.executeUpdate();

    }

    @Override
    public List<Arbitre> afficher() throws SQLException {
        return null;
    }
    public List<Arbitre> getAllArbitres() throws SQLException {
        List<Arbitre> arbitres = new ArrayList<>();
        String query = "SELECT * FROM arbitre";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_arbitre");
                int age = resultSet.getInt("age");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");

                String phone = resultSet.getString("phone");


                Arbitre arbitre = new Arbitre(id,age,nom,prenom,email,phone);
                arbitres.add(arbitre);
            }
        }
        return arbitres;
    }

    public Arbitre getArbitreById(int id) throws SQLException {
        String query = "SELECT * FROM Arbitre WHERE id_arbitre = ?";
        Arbitre arbitre = null;

        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    arbitre = new Arbitre();
                    arbitre.setAge(resultSet.getInt("age"));
                    arbitre.setNom(resultSet.getString("nom"));
                    arbitre.setPrenom(resultSet.getString("prenom"));
                    arbitre.setEmail(resultSet.getString("email"));
                    arbitre.setPhone(resultSet.getString("phone"));
                }
            }
        }

        return arbitre;
    }
}

