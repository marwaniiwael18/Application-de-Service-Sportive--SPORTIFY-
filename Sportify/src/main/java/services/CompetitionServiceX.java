package services;

import entities.Competition;
import entities.Equipe;
import entities.Terrain;
import utils.DB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompetitionServiceX implements IServiceX<Competition> {

    private Connection con ;

    public CompetitionServiceX() {
        this.con = DB.getInstance().getConnection();
    }


    @Override
    public void ajouter(Competition competition) throws SQLException {
        String req = "INSERT INTO competition (Nom, Description, Type, Heure,Date,terrain_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setString(1, competition.getNom());
        pstmt.setString(2, competition.getDescription());
        pstmt.setString(3, competition.getType());
        pstmt.setTime(4, competition.getHeure());
        pstmt.setDate(5, competition.getDate());
        pstmt.setInt(6, competition.getTerrain().getID_Terrain());





        pstmt.executeUpdate();
        pstmt.close();
    }

    @Override
    public void modifier(Competition competition) throws SQLException {
        String req = "update competition set nom=? , Description=? , Type=? , Heure=? , Date=? , terrain_id=?  where ID_Competition=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1,competition.getNom());
        pre.setString(2,competition.getDescription());
        pre.setString(3,competition.getType());
        pre.setTime(4,competition.getHeure());
        pre.setDate(5,competition.getDate());
        pre.setInt(6, competition.getTerrain().getID_Terrain());
        pre.setInt(7,competition.getID_competiton());


        pre.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req="DELETE FROM competition WHERE ID_Competition=?" ;
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,id) ;
        pre.executeUpdate() ;
    }

    @Override
    public void modifiert(int id, Competition competition) throws SQLException {

    }


    @Override
    public Competition authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Competition> afficher() throws SQLException {
        List<Competition> competitions = new ArrayList<>();

        String req ="SELECT c.*, t.*, e1.*, e2.* " +
                "FROM Competition c " +
                "LEFT JOIN Terrain t ON c.terrain_id = t.ID_Terrain " +
                "LEFT JOIN Equipe e1 ON c.equipe1_id = e1.IDEquipe " +
                "LEFT JOIN Equipe e2 ON c.equipe2_id = e2.IDEquipe";
        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Competition c = new Competition();
                c.setID_competiton(res.getInt(1));
                c.setNom(res.getString("nom"));
                c.setDescription(res.getString("Description"));
                c.setType(res.getString("Type"));
                c.setHeure(res.getTime("Heure"));
                c.setHeure(res.getTime("Heure"));
                try {
                    Date date = res.getDate("Date");
                    if (date != null && !date.toLocalDate().equals(LocalDate.of(0000, 1, 1))) {
                        c.setDate(date);
                    } else {
                        // Set a default date or handle the zero date case
                        c.setDate(null); // Or set to a default date
                    }
                } catch (SQLException e) {
                    // Handle SQLException
                    e.printStackTrace();
                }

                if (res.getObject("terrain_id") != null) {
                    Terrain terrain = new Terrain();
                    terrain.setID_Terrain(res.getInt("ID_Terrain"));
                    terrain.setNomTerrain(res.getString("NomTerrain"));
                    terrain.setType_surface(res.getString("Type_surface"));
                    terrain.setLocalisation(res.getString("Localisation"));
                    terrain.setPrix(res.getDouble("Prix"));
                    terrain.setID_Proprietaire(res.getInt("ID_Propriétaire"));
                    c.setTerrain(terrain);
                } else {
                    Terrain defaultTerrain = new Terrain();
                    // Set default values or leave them as null
                    c.setTerrain(defaultTerrain); // Set the terrain to null if it's not associated
                }
                Equipe equipe1 = new Equipe();
                equipe1.setID(res.getInt("e1.IDEquipe"));
                equipe1.setNom(res.getString("e1.Nom"));
                equipe1.setRandom(res.getBoolean("e1.isRandom"));
                // Set other attributes for equipe1

                // Set equipe2 attributes
                Equipe equipe2 = new Equipe();
                equipe2.setID(res.getInt("e2.IDEquipe"));
                equipe2.setNom(res.getString("e2.Nom"));
                equipe2.setRandom(res.getBoolean("e2.isRandom"));


                // Set other attributes for equipe2

                c.setEquipe1(equipe1);
                c.setEquipe2(equipe2);
                competitions.add(c);
            }
        }
        return competitions;
    }


   public Competition getById(int id) throws SQLException {
        Competition competition = null;
       String query = "SELECT c.*, t.ID_Terrain, t.NomTerrain, t.Type_surface, t.Localisation, t.Prix " +
               "FROM Competition c " +
               "LEFT JOIN Terrain t ON c.terrain_id = t.ID_Terrain " +
               "WHERE c.ID_Competition = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    competition = new Competition();
                    competition.setID_competiton(resultSet.getInt("ID_Competition"));
                    competition.setNom(resultSet.getString("nom"));
                    competition.setDescription(resultSet.getString("Description"));
                    competition.setType(resultSet.getString("Type"));
                    competition.setHeure(resultSet.getTime("Heure"));
                    try {
                        Date date = resultSet.getDate("Date");
                        if (date != null && !date.toLocalDate().equals(LocalDate.of(0000, 1, 1))) {
                            competition.setDate(date);
                        } else {
                            // Set a default date or handle the zero date case
                            competition.setDate(null); // Or set to a default date
                        }
                    } catch (SQLException e) {
                        // Handle SQLException
                        e.printStackTrace();
                    }

                        Terrain terrain = new Terrain();
                        terrain.setID_Terrain(resultSet.getInt("ID_Terrain"));
                        terrain.setNomTerrain(resultSet.getString("NomTerrain"));
                        terrain.setType_surface(resultSet.getString("Type_surface"));
                        terrain.setLocalisation(resultSet.getString("Localisation"));
                        terrain.setPrix(resultSet.getDouble("Prix"));
                        competition.setTerrain(terrain);


                    if (resultSet.getObject("terrain_id") != null) {

                        ServiceEquipeX EquipeService = new ServiceEquipeX();
                        Equipe equipe1 = EquipeService.getById(resultSet.getInt("equipe1_id"));
                        Equipe equipe2 = EquipeService.getById(resultSet.getInt("equipe2_id"));
                        competition.setEquipe1(equipe1);
                        competition.setEquipe2(equipe2);

                    }

                    // Set other attributes for equipe2



                    else {
                        Equipe defaultEquipe1 = new Equipe();
                        Equipe defaultEquipe2 = new Equipe();
                        // Set default values or leave them as null
                        competition.setEquipe1(defaultEquipe1); // Set the terrain to null if it's not associated
                        competition.setEquipe2(defaultEquipe2); // Set the terrain to null if it's not associated
                    }



                    // Populate other attributes as needed
                }
            }
        }
        return competition;
    }

}
