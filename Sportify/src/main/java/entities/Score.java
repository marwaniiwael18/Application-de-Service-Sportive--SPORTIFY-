package entities;

public class Score {
    private int idScore;
    private Competition competition;
    private Equipe winner;
    private Equipe loser;
    private int equipe1Score;
    private int equipe2Score;
    private String resultat;
    private String reclamation;

    public Score() {
    }

    public Score(Competition competition, Equipe winner, Equipe loser, int equipe1Score, int equipe2Score, String resultat, String reclamation) {
        this.competition = competition;
        this.winner = winner;
        this.loser = loser;
        this.equipe1Score = equipe1Score;
        this.equipe2Score = equipe2Score;
        this.resultat = resultat;
        this.reclamation = reclamation;
    }

    public Score(int idScore, Competition competition, Equipe winner, Equipe loser, int equipe1Score, int equipe2Score, String resultat, String reclamation) {
        this.idScore = idScore;
        this.competition = competition;
        this.winner = winner;
        this.loser = loser;
        this.equipe1Score = equipe1Score;
        this.equipe2Score = equipe2Score;
        this.resultat = resultat;
        this.reclamation = reclamation;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Equipe getWinner() {
        return winner;
    }

    public void setWinner(Equipe winner) {
        this.winner = winner;
    }

    public Equipe getLoser() {
        return loser;
    }

    public void setLoser(Equipe loser) {
        this.loser = loser;
    }

    public int getEquipe1Score() {
        return equipe1Score;
    }

    public void setEquipe1Score(int equipe1Score) {
        this.equipe1Score = equipe1Score;
    }

    public int getEquipe2Score() {
        return equipe2Score;
    }

    public void setEquipe2Score(int equipe2Score) {
        this.equipe2Score = equipe2Score;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Score{" +
                "idScore=" + idScore +
                ", competition=" + competition +
                ", winner=" + winner +
                ", loser=" + loser +
                ", equipe1Score=" + equipe1Score +
                ", equipe2Score=" + equipe2Score +
                ", resultat='" + resultat + '\'' +
                ", reclamation='" + reclamation + '\'' +
                '}';
    }
}
