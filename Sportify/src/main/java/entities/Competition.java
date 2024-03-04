package entities;

import javafx.scene.control.DatePicker;

import java.sql.Time;
import java.sql.Date;

public class Competition  {
    int ID_competiton ;


    String Nom;
    String Description ;

    String Type ;

    Time Heure ;
    Date Date ;

    private Equipe equipe1;
    private Equipe equipe2;
    private Terrain terrain;


    public Competition() {
    }

    public Competition(int ID_competiton, String nom, String description, String type, Time heure, Date date , Terrain terrain) {
        this.ID_competiton = ID_competiton;
        Nom = nom;
        Description = description;
        Type = type;
        Heure = heure;
        Date = date;
        this.terrain=terrain ;
    }

    public Competition(String nom, String description, String type, Time heure) {
        Nom = nom;
        Description = description;
        Type = type;
        Heure = heure;
    }

    public Competition(String nom, String description, String type, Time heure, java.sql.Date date,Terrain terrain) {
        Nom = nom;
        Description = description;
        Type = type;
        Heure = heure;
        Date = date;
        this.terrain=terrain ;
    }


    public int getID_competiton() {
        return ID_competiton;
    }

    public void setID_competiton(int ID_competiton) {
        this.ID_competiton = ID_competiton;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Time getHeure() {
        return Heure;
    }

    public void setHeure(Time heure) {
        Heure = heure;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
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

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "ID_competiton=" + ID_competiton +
                ", Nom='" + Nom + '\'' +
                ", Description='" + Description + '\'' +
                ", Type='" + Type + '\'' +
                ", Heure=" + Heure +
                ", Date=" + Date +
                '}';
    }
}
