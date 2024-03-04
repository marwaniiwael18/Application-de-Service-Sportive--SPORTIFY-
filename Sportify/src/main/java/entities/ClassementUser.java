package entities;

public class ClassementUser {

    int id ;
    Utilisateur user ;
    int points , rank ,nbre_de_match, win , loss , draw ;

    public ClassementUser() {
    }

    public ClassementUser(int id, Utilisateur user, int points, int rank, int nbre_de_match, int win, int loss, int draw) {
        this.id = id;
        this.user = user;
        this.points = points;
        this.rank = rank;
        this.nbre_de_match = nbre_de_match;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    public ClassementUser(Utilisateur user, int points, int rank, int nbre_de_match, int win, int loss, int draw) {
        this.user = user;
        this.points = points;
        this.rank = rank;
        this.nbre_de_match = nbre_de_match;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getNbre_de_match() {
        return nbre_de_match;
    }

    public void setNbre_de_match(int nbre_de_match) {
        this.nbre_de_match = nbre_de_match;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
