package entities;

public class ClassementEquipe {
    int id ;
    Equipe equipe ;
    int points , rank ,nbre_de_match, win , loss , draw ;

    public ClassementEquipe(int id, Equipe equipe, int points, int rank, int win, int loss, int draw) {
        this.id = id;
        this.equipe = equipe;
        this.points = points;
        this.rank = rank;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }


    public ClassementEquipe(Equipe equipe, int points, int rank, int win, int loss, int draw) {
        this.equipe = equipe;
        this.points = points;
        this.rank = rank;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    public ClassementEquipe(Equipe equipe, int points, int rank, int nbre_de_match, int win, int loss, int draw) {
        this.equipe = equipe;
        this.points = points;
        this.rank = rank;
        this.nbre_de_match = nbre_de_match;
        this.win = win;
        this.loss = loss;
        this.draw = draw;
    }

    public int getNbre_de_match() {
        return nbre_de_match;
    }

    public void setNbre_de_match(int nbre_de_match) {
        this.nbre_de_match = nbre_de_match;
    }

    public ClassementEquipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
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
