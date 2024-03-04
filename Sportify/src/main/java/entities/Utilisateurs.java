package entities;

import java.util.Date;

public class Utilisateurs {
    int id;
    String nom,prenom,email,mot_de_passe,image,niveau_competence,adresse;
    Role role;
    Date date_de_naissance;

    Boolean verified;
    public Utilisateurs(){

    }

    public Utilisateurs(int id, String nom, String prenom, String email, String mot_de_passe, String image, String niveau_competence, String adresse, Role role, Date date_de_naissance,Boolean verified) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.image = image;
        this.niveau_competence = niveau_competence;
        this.adresse = adresse;
        this.role = role;
        this.date_de_naissance = date_de_naissance;
        this.verified=verified;
    }

    public Utilisateurs(String nom, String prenom, String email, String mot_de_passe, String image, String niveau_competence, String adresse, Role role, Date date_de_naissance,Boolean verified) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.image = image;
        this.niveau_competence = niveau_competence;
        this.adresse = adresse;
        this.role = role;
        this.date_de_naissance = date_de_naissance;
        this.verified=verified;
    }

    public Utilisateurs(String nom, String prenom, String email, String mot_de_passe, String image, String niveau_competence, String adresse, Date date_de_naissance,Boolean verified) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.image = image;
        this.niveau_competence = niveau_competence;
        this.adresse = adresse;
        this.date_de_naissance = date_de_naissance;
        this.verified=verified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNiveau_competence() {
        return niveau_competence;
    }

    public void setNiveau_competence(String niveau_competence) {
        this.niveau_competence = niveau_competence;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", image='" + image + '\'' +
                ", niveau_competence='" + niveau_competence + '\'' +
                ", adresse='" + adresse + '\'' +
                ", role=" + role +
                ", date_de_naissance=" + date_de_naissance +
                '}';
    }
}
