package entities;

public class Arbitre {
    int id_arbitre;
    int age;
    String nom,prenom,email,phone;

    public Arbitre(int id_arbitre, String nom) {
        this.id_arbitre = id_arbitre;
        this.nom = nom;
    }

    public Arbitre(int id_arbitre, int age, String nom, String prenom, String email, String phone) {
        this.id_arbitre = id_arbitre;
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
    }
    public Arbitre(int age, String nom, String prenom, String email, String phone) {

        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
    }
    public Arbitre(){}

    public int getId_arbitre() {
        return id_arbitre;
    }

    public void setId_arbitre(int id_arbitre) {
        this.id_arbitre = id_arbitre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Age: " + age + ", Nom: " + nom + ", Prénom: " + prenom + ", Email: " + email + ", Phone: " + phone;

    }
    public String toString1() {
        return " Nom: " + nom + " Prénom: " + prenom;

    }


}
