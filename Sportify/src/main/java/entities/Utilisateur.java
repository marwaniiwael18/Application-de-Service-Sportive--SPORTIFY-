package entities;

import java.util.Date;

public class Utilisateur {
    private int id;
    private String nom, prenom, mot_de_passe, email, image, adresse, niveau_competence;
    private Date date_de_naissance;
    private Role role;
    private boolean verified;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String mot_de_passe, String email, String image, String adresse, String niveau_competence, Date date_de_naissance, Role role, boolean verified) {
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.image = image;
        this.adresse = adresse;
        this.niveau_competence = niveau_competence;
        this.date_de_naissance = date_de_naissance;
        this.role = role;
        this.verified = verified;
    }

    public Utilisateur(int id, String nom, String prenom, String mot_de_passe, String email, String image, String adresse, String niveau_competence, Date date_de_naissance, Role role, boolean verified) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mot_de_passe = mot_de_passe;
        this.email = email;
        this.image = image;
        this.adresse = adresse;
        this.niveau_competence = niveau_competence;
        this.date_de_naissance = date_de_naissance;
        this.role = role;
        this.verified = verified;
    }

    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String image, String niveauCompetence) {

    }
    public void setID_User(int ID_User) {
        this.id = id;
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

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNiveau_competence() {
        return niveau_competence;
    }

    public void setNiveau_competence(String niveau_competence) {
        this.niveau_competence = niveau_competence;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", adresse='" + adresse + '\'' +
                ", niveau_competence='" + niveau_competence + '\'' +
                ", date_de_naissance=" + date_de_naissance +
                ", role=" + role +
                ", verified=" + verified +
                '}';
    }
}