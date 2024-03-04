package entities;

public class EquipeMembre {
    private Equipe equipe ;
    private Utilisateur membre ;

    public EquipeMembre(Equipe equipe, Utilisateur membre) {
        this.equipe = equipe;
        this.membre = membre;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Utilisateur getMembre() {
        return membre;
    }

    public void setMembre(Utilisateur membre) {
        this.membre = membre;
    }


}
