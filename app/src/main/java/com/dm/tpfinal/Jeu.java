package com.dm.tpfinal;

import java.util.Vector;

public class Jeu {
    private int nbCartes;
    private Vector<Carte> jeu;

    public Jeu(int nbCartes, Vector<Carte> jeu) {
        this.nbCartes = nbCartes;
        this.jeu = jeu;
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }

    public Vector<Carte> getJeu() {
        return jeu;
    }

    public void setJeu(Vector<Carte> jeu) {
        this.jeu = jeu;
    }
}
