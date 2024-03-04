package entities;

import java.time.LocalDate;
import java.time.LocalTime;
public class Post {
    int ID_post;
    int ID_User;
    LocalDate Date;
    LocalTime Heure;
    String Description;

    public Post(int ID_post, int ID_User, LocalDate date, LocalTime heure, String description) {
        this.ID_post = ID_post;
        this.ID_User = ID_User;
        Date = date;
        Heure = heure;
        Description = description;
    }

    public Post(int ID_User, LocalDate date, LocalTime heure, String description) {
        this.ID_User = ID_User;
        Date = date;
        Heure = heure;
        Description = description;
    }

    public Post() {
    }

    public static void add(Post p) {

    }

    public int getID_User() {
        return ID_User;
    }

    public int getID_post() {
        return ID_post;
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

    public void setID_post(int ID_post) {
        this.ID_post = ID_post;
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
        return  "post{" +
                "ID_post=" + ID_post+
                ", ID_User='" + ID_User+ '\'' +
                ", Date='" + Date+ '\'' +
                ", Heure='" + Heure+
                ", Description='" + Description+ '\'' +
                '}';
    }


    public void modifier(Post postToUpdate) {
    }


}
