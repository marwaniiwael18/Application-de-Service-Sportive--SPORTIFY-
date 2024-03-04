package services;

import entities.ClassementUser;
import entities.Utilisateur;
import utils.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceClassementUserX implements IServiceX<ClassementUser> {

    private Connection con ;

    public ServiceClassementUserX() {
        this.con = DB.getInstance().getConnection();
    }

    @Override
    public void ajouter(ClassementUser classementUser) throws SQLException {
        String req = "INSERT INTO classementuser (user_id, points, rank, nbre_de_match,win,draw,loss) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(req);
        pstmt.setInt(1, classementUser.getUser().getId());
        pstmt.setInt(2, classementUser.getPoints());
        pstmt.setInt(3, classementUser.getRank());
        pstmt.setInt(4, classementUser.getNbre_de_match());
        pstmt.setInt(5, classementUser.getWin());
        pstmt.setInt(6, classementUser.getDraw());
        pstmt.setInt(7, classementUser.getLoss());

        pstmt.executeUpdate();
        pstmt.close();

    }

    @Override
    public void modifier(ClassementUser classementUser) throws SQLException {
        String req = "UPDATE classementuser SET points=?, rank=?, nbre_de_match=?, win=?, draw=?, loss=? WHERE id=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setInt(1, classementUser.getPoints());
            pre.setInt(2, classementUser.getRank());
            pre.setInt(3, classementUser.getNbre_de_match());
            pre.setInt(4, classementUser.getWin());
            pre.setInt(5, classementUser.getDraw());
            pre.setInt(6, classementUser.getLoss());
            pre.setInt(7, classementUser.getId());
            pre.executeUpdate();
        }

    }

    @Override
    public void supprimer(int t) throws SQLException {

    }

    @Override
    public void modifiert(int id, ClassementUser classementUser) throws SQLException {

    }

    @Override
    public ClassementUser authentifier(String email, String password) throws SQLException {
        return null;
    }

    @Override
    public List<ClassementUser> afficher() throws SQLException {
        List<ClassementUser> tables = new ArrayList<>();
        String req = "SELECT c.*, u.* " +
                "FROM classementuser c " +
                "LEFT JOIN utilisateur u ON c.user_id = u.id ORDER BY c.rank ASC";

        try (PreparedStatement pre = con.prepareStatement(req);
             ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                ClassementUser c = new ClassementUser();
                c.setId(res.getInt(1));
                c.setPoints(res.getInt("points"));
                c.setRank(res.getInt("rank"));
                c.setNbre_de_match(res.getInt("nbre_de_match"));
                c.setWin(res.getInt("win"));
                c.setDraw(res.getInt("draw"));
                c.setLoss(res.getInt("loss"));

                if (res.getObject("user_id") != null) {
                    // Instead of parsing to int, set the team name directly
                    Utilisateur user = new Utilisateur();
                    user.setId(res.getInt("u.id"));
                    user.setNom(res.getString("u.Nom"));

                    c.setUser(user);
                } else {
                    Utilisateur defaultuser = new Utilisateur();
                    c.setUser(defaultuser); // Set the equipe to null if it's not associated
                }
                tables.add(c);
            }
        }
        return tables; }


  /*  public ClassementEquipe getClassementByUser(Utilisateur user) {
        ClassementEquipe tableequipe = null;
        String query = "SELECT * FROM score WHERE user_id = ?";
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
    }*/

}
