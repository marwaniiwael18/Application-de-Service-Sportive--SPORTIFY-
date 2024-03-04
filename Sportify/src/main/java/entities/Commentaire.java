package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Commentaire {
    int ID_commentaire;
    int ID_User;
    LocalDate Date;
    LocalTime Heure;
    String Description;

    public Commentaire(int ID_commentaire, int ID_User, LocalDate date, LocalTime heure, String description) {
        this.ID_commentaire = ID_commentaire;
        this.ID_User = ID_User;
        Date = date;
        Heure = heure;
        Description = description;
    }

    public Commentaire(int ID_User, LocalDate date, LocalTime heure, String description) {
        this.ID_User = ID_User;
        Date = date;
        Heure = heure;
        Description = description;
    }

    public Commentaire() {
    }

    public static void add(Commentaire c) {
    }

    public int getID_commentaire() {
        return ID_commentaire;
    }

    public int getID_User() {
        return ID_User;
    }

    public LocalDate getDate() {
        return Date;
    }

    public LocalTime getHeure() {
        return Heure;
    }

    public String getDescription() {
        return Description;
    }

    public void setID_commentaire(int ID_commentaire) {
        this.ID_commentaire = ID_commentaire;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public void setHeure(LocalTime heure) {
        Heure = heure;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return  "Commentaire{" +
                "ID_commentaire=" + ID_commentaire+
                ", ID_User='" + ID_User+ '\'' +
                ", Date='" + Date+ '\'' +
                ", Heure='" + Heure+
                ", Description='" + Description+ '\'' +
                '}';
    }
}
