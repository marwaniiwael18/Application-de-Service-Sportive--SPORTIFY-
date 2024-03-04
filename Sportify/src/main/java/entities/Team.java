package entities;

import java.util.List;

public class Team {
    public String nomEquipe;
    public List<String> membres;

    public Team(String nomEquipe, List<String> membres) {
        this.nomEquipe = nomEquipe;
        this.membres = membres;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public List<String> getMembres() {
        return membres;
    }

    public void setMembres(List<String> membres) {
        this.membres = membres;
    }

    @Override
    public String toString() {
        return "Team{" +
                "nomEquipe='" + nomEquipe + '\'' +
                ", membres=" + membres +
                '}';
    }
}

