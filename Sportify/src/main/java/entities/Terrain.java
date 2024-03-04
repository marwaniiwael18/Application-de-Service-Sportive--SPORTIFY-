package entities;

import java.util.Objects;

public class Terrain {
    int ID_Terrain,ID_Proprietaire;
    String Nom, Type_surface, Localisation;
    Double Prix;
    String image_ter;

    public Terrain(int ID_Proprietaire, String nom, String type_surface, String localisation, Double prix, String image_ter) {
        this.ID_Proprietaire = ID_Proprietaire;
        Nom = nom;
        Type_surface = type_surface;
        Localisation = localisation;
        Prix = prix;
        this.image_ter = image_ter;
    }

    public Terrain(int ID_Terrain, int ID_Proprietaire, String nom, String type_surface, String localisation, Double prix, String image_ter) {
        this.ID_Terrain = ID_Terrain;
        this.ID_Proprietaire = ID_Proprietaire;
        Nom = nom;
        Type_surface = type_surface;
        Localisation = localisation;
        Prix = prix;
        this.image_ter = image_ter;
    }

    public Terrain() {

    }

    public int getID_Terrain() {
        return ID_Terrain;
    }

    public void setID_Terrain(int ID_Terrain) {
        this.ID_Terrain = ID_Terrain;
    }

    public int getID_Proprietaire() {
        return ID_Proprietaire;
    }

    public void setID_Proprietaire(int ID_Proprietaire) {
        this.ID_Proprietaire = ID_Proprietaire;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getType_surface() {
        return Type_surface;
    }

    public void setType_surface(String type_surface) {
        Type_surface = type_surface;
    }

    public String getLocalisation() {
        return Localisation;
    }

    public void setLocalisation(String localisation) {
        Localisation = localisation;
    }

    public Double getPrix() {
        return Prix;
    }

    public void setPrix(Double prix) {
        Prix = prix;
    }

    public String getImage_ter() {
        return image_ter;
    }

    public void setImage_ter(String image_ter) {
        this.image_ter = image_ter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terrain terrain = (Terrain) o;
        return ID_Terrain == terrain.ID_Terrain && ID_Proprietaire == terrain.ID_Proprietaire && Objects.equals(Nom, terrain.Nom) && Objects.equals(Type_surface, terrain.Type_surface) && Objects.equals(Localisation, terrain.Localisation) && Objects.equals(Prix, terrain.Prix) && Objects.equals(image_ter, terrain.image_ter);
    }



    @Override
    public String toString() {
        return "Terrain{" +
                "ID_Terrain=" + ID_Terrain +
                ", ID_Proprietaire=" + ID_Proprietaire +
                ", Nom='" + Nom + '\'' +
                ", Type_surface='" + Type_surface + '\'' +
                ", Localisation='" + Localisation + '\'' +
                ", Prix=" + Prix +
                ", image_ter='" + image_ter + '\'' +
                '}';
    }


    public void setNomTerrain(String nomTerrain) {
    }

    public String getNomTerrain() {
        return null;
    }
}
