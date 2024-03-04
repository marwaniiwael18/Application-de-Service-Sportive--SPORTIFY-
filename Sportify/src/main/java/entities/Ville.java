package entities;

public enum Ville {
    TUNIS("Tunis"),
    ARIANA("Ariana"),
    BEN_AROUS("Ben Arous"),
    MANOUBA("Manouba"),
    NABEUL("Nabeul"),
    ZAGHOUAN("Zaghouan"),
    BIZERTE("Bizerte"),
    BEJA("Béja"),
    JENDOUBA("Jendouba"),
    KEF("Le Kef"),
    SILIANA("Siliana"),
    KAIROUAN("Kairouan"),
    KASSERINE("Kasserine"),
    SIDI_BOUZID("Sidi Bouzid"),
    SOUSSE("Sousse"),
    MONASTIR("Monastir"),
    MAHDIA("Mahdia"),
    SFAX("Sfax"),
    GAFSA("Gafsa"),
    TOZEUR("Tozeur"),
    KEBOURGA("Kebili"),
    TATAOUINE("Tataouine"),
    MEDNINE("Médenine"),
    GABES("Gabès");
    private final String nomVille;

    Ville(String nomVille) {
        this.nomVille = nomVille;
    }

    @Override
    public String toString() {
        return nomVille;
    }
}
