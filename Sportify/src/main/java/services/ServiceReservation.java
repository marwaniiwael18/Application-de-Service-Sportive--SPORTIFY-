package services;
import entities.Utilisateur;
import entities.Reservation;
import utils.DB;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ServiceReservation implements IService<Reservation> {
    private Connection con = DB.getInstance().getConnection();

    public ServiceReservation() {
    }

    @Override
    public void ajouter(Reservation reservation) {
        try {
            String query = "INSERT INTO Reservation (id, ID_Terrain, Date_Heure, Duree) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stm.setInt(1, reservation.getId());
            stm.setInt(2, reservation.getID_Terrain());
            // Convert LocalDateTime to Timestamp
            stm.setTimestamp(3, Timestamp.valueOf(reservation.getDate_Heure()));
            stm.setString(4, reservation.getDuree());

            stm.executeUpdate();
            System.out.println("Reservation ajoutée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ajouter(Reservation reservation, String path) throws SQLException {

    }

    @Override
    public void modifier(Reservation reservation) throws SQLException {

    }
@Override
    public void supprimer(int ID_reservation) throws SQLException {
        String sql = "delete from reservation where ID_reservation = ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, ID_reservation);
        preparedStatement.executeUpdate();
        }


    public void modifierReservation(Reservation reservation) {
        try {
            String query = "UPDATE Reservation SET id=?, ID_Terrain=?, Date_Heure=?, Duree=? WHERE ID_reservation=?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, reservation.getId());
            pstmt.setInt(2, reservation.getID_Terrain());
            // Convert LocalDateTime to Timestamp
            pstmt.setTimestamp(3, Timestamp.valueOf(reservation.getDate_Heure()));
            pstmt.setString(4, reservation.getDuree());
            pstmt.setInt(5, reservation.getID_reservation());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La réservation a été correctement modifiée.");
            } else {
                System.out.println("Aucune modification n'a été effectuée. Vérifiez l'ID de la réservation.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de la réservation : " + ex.getMessage());
        }
    }

    // Other methods...

    @Override
    public Set<Reservation> afficher() {
        Set<Reservation> reservations = new HashSet<>();

        try {
            Statement stm = con.createStatement();
            String query = "SELECT * FROM Reservation";

            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                int idReservation = rs.getInt("ID_reservation");
                int id = rs.getInt("id");
                int idTerrain = rs.getInt("ID_Terrain");
                // Retrieve the Timestamp from the ResultSet and convert it to LocalDateTime
                LocalDateTime dateHeure = rs.getTimestamp("Date_Heure").toLocalDateTime();
                String duree = rs.getString("Duree");

                Reservation reservation = new Reservation(idReservation, id, idTerrain, dateHeure, duree);
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reservations;
    }




    }

