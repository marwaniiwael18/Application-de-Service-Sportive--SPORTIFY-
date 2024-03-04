package services;

import entities.ClassementEquipe;
import entities.Equipe;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceClassementEquipeX implements IServiceX<ClassementEquipe> {

    private Connection con ;

    public ServiceClassementEquipeX() {
            this.con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(ClassementEquipe classementEquipe) throws SQLException {
        String req = "INSERT INTO classementEquipe (equipe_id, points, rank, nbre_de_match,win,draw,loss) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setInt(1, classementEquipe.getEquipe().getID());
        pstmt.setInt(2, classementEquipe.getPoints());
        pstmt.setInt(3, classementEquipe.getRank());
        pstmt.setInt(4, classementEquipe.getNbre_de_match());
        pstmt.setInt(5, classementEquipe.getWin());
        pstmt.setInt(6, classementEquipe.getDraw());
        pstmt.setInt(7, classementEquipe.getLoss());

        pstmt.executeUpdate();
        pstmt.close();
    }


   /* @Override
    public void modifier(ClassementEquipe classementEquipe) throws SQLException {
        String req = "update competition set equipe_id=? , points=? , rank=? , nbre_de_match=? , win=? , draw=? , loss=?  where id=?";
        PreparedStatement pstmt = con.prepareStatement(req);

        pstmt.setInt(1, classementEquipe.getEquipe().getID());
        pstmt.setInt(2, classementEquipe.getPoints());
        pstmt.setInt(3, classementEquipe.getRank());
        pstmt.setInt(4, classementEquipe.getNbre_de_match());
        pstmt.setInt(5, classementEquipe.getWin());
        pstmt.setInt(6, classementEquipe.getDraw());
        pstmt.setInt(7, classementEquipe.getLoss());
        pstmt.setInt(8, classementEquipe.getId());
        pstmt.executeUpdate();
    }*/
    @Override
    public void modifier(ClassementEquipe classementEquipe) throws SQLException {
        String req = "UPDATE classementequipe SET points=?, rank=?, nbre_de_match=?, win=?, draw=?, loss=? WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, classementEquipe.getPoints());
            pre.setInt(2, classementEquipe.getRank());
            pre.setInt(3, classementEquipe.getNbre_de_match());
            pre.setInt(4, classementEquipe.getWin());
            pre.setInt(5, classementEquipe.getDraw());
            pre.setInt(6, classementEquipe.getLoss());
            pre.setInt(7, classementEquipe.getId());
            pre.executeUpdate();
        }
    }
    @Override
    public void supprimer(int t) throws SQLException {
    }

    @Override
    public void modifiert(int id, ClassementEquipe classementEquipe) throws SQLException {

    }

    @Override
    public ClassementEquipe authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<ClassementEquipe> afficher() throws SQLException {
        List<ClassementEquipe> tables = new ArrayList<>();

        String req = "SELECT c.*, e.* " +
                "FROM classementequipe c " +
                "LEFT JOIN Equipe e ON c.equipe_id = e.IDEquipe ORDER BY c.rank ASC";

        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                ClassementEquipe c = new ClassementEquipe();
                c.setId(res.getInt(1));
                c.setPoints(res.getInt("points"));
                c.setRank(res.getInt("rank"));
                c.setNbre_de_match(res.getInt("nbre_de_match"));
                c.setWin(res.getInt("win"));
                c.setDraw(res.getInt("draw"));
                c.setLoss(res.getInt("loss"));

                if (res.getObject("equipe_id") != null) {
                    // Instead of parsing to int, set the team name directly
                    Equipe equipe = new Equipe();
                    equipe.setID(res.getInt("e.IDEquipe"));
                    equipe.setNom(res.getString("e.Nom"));
                    equipe.setRandom(res.getBoolean("e.isRandom"));
                    c.setEquipe(equipe);
                } else {
                    Equipe defaultEquipe = new Equipe();
                    c.setEquipe(defaultEquipe); // Set the equipe to null if it's not associated
                }

                tables.add(c);
            }
        }
        return tables;
    }


    public List<ClassementEquipe> getAllOrderedByPoints() throws SQLException {
        List<ClassementEquipe> tables = new ArrayList<>();

        String req = "SELECT * FROM classementequipe ORDER BY points DESC";

        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            int rank = 1;
            while (res.next()) {
                ClassementEquipe c = new ClassementEquipe();
                c.setId(res.getInt("id"));
                c.setPoints(res.getInt("points"));
                c.setRank(rank++);
                c.setNbre_de_match(res.getInt("nbre_de_match"));
                c.setWin(res.getInt("win"));
                c.setDraw(res.getInt("draw"));
                c.setLoss(res.getInt("loss"));

                Equipe equipe = new Equipe();
                equipe.setID(res.getInt("equipe_id")); // Assuming you store equipe_id in the classementequipe table
                c.setEquipe(equipe);

                tables.add(c);
            }
        }
        return tables;
    }




    public ClassementEquipe getClassementByEquipe(Equipe equipe) {
        ClassementEquipe tableequipe = null;
        String query = "SELECT * FROM score WHERE equipe_id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, equipe.getID());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    tableequipe = new ClassementEquipe();
                    tableequipe.setId(resultSet.getInt("idScore"));
                    tableequipe.setEquipe(equipe);
                    tableequipe.setPoints(resultSet.getInt("points"));
                    tableequipe.setRank(resultSet.getInt("rank"));
                    tableequipe.setNbre_de_match(resultSet.getInt("nbre_de_match"));
                    tableequipe.setWin(resultSet.getInt("win"));
                    tableequipe.setDraw(resultSet.getInt("draw"));
                    tableequipe.setLoss(resultSet.getInt("loss"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableequipe;
    }

}
