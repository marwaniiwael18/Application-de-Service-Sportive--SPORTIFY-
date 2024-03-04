package services;

import entities.Terrain;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTerrainX implements IServiceX<Terrain> {

    private Connection con ;

    public ServiceTerrainX() {
        this.con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Terrain terrain) throws SQLException {

    }

    @Override
    public void modifier(Terrain terrain) throws SQLException {

    }

    @Override
    public void supprimer(int t) throws SQLException {

    }

    @Override
    public void modifiert(int id, Terrain terrain) throws SQLException {

    }

    @Override
    public Terrain authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<Terrain> afficher() throws SQLException {
        return null;
    }

    public List<Terrain> afficherNom() throws SQLException {
        List<Terrain> terrains = new ArrayList<>() ;
        String query = "SELECT ID_Terrain , NomTerrain FROM Terrain ";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Terrain terrain = new Terrain();
                    terrain.setID_Terrain(resultSet.getInt("ID_Terrain"));
                    terrain.setNomTerrain(resultSet.getString("NomTerrain"));

                    terrains.add(terrain) ;
                }
            }
        }
        return terrains;

    }
}
