package entities;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    int ID_Categ;
    String Nom;
    String Description;
    String Image;

    public Categorie(int ID_Categ, String nom, String description, String image) {
        this.ID_Categ = ID_Categ;
        Nom = nom;
        Description = description;
        Image = image;
    }

    public Categorie(String nom, String description, String image) {
        Nom = nom;
        Description = description;
        Image = image;
    }
    public Categorie(){

    }

    public int getID_Categ() {
        return ID_Categ;
    }

    public void setIDCateg(int IDCateg) {
        this.ID_Categ = IDCateg;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "IDCateg=" + ID_Categ +
                ", Nom='" + Nom + '\'' +
                ", Description='" + Description + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}