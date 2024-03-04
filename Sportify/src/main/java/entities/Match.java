package entities;

import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Match {
    int ID_Matc ;
    String nom,type,description;
    Date date;
    Time heure;
    Equipe equipe1,equipe2;

    public Arbitre getArbitre() {
        return arbitre;
    }

    public void setArbitre(Arbitre arbitre) {
        this.arbitre = arbitre;
    }

    Arbitre arbitre;



    public Match(int ID_Matc, String nom, String type, String description, Date date, Time heure, Equipe equipe1, Equipe equipe2, Arbitre arbitre) {
        this.ID_Matc = ID_Matc;
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.arbitre=arbitre;
    }
    public Match( String nom, String type, String description, Date date, Time heure, Equipe equipe1, Equipe equipe2, Arbitre arbitre) {

        this.nom = nom;
        this.type = type;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.arbitre=arbitre;
    }
    public Match(){}


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public int getID_Matc() {
        return ID_Matc;
    }

    public void setID_Matc(int ID_Matc) {
        this.ID_Matc = ID_Matc;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Equipe equipe2) {
        this.equipe2 = equipe2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", equipe1=" + equipe1.getNom() +
                ", equipe2=" + equipe2.getNom() +
                ", arbitre=" + arbitre.toString1() +
                '}';
    }

    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
