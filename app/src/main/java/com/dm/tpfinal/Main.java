package com.dm.tpfinal;

import java.util.Vector;

public class Main {
    private Vector<Carte> main;
    private int nbCartes;

    public Main(Vector<Carte> main, int nbCartes) {
        this.main = main;
        this.nbCartes = nbCartes;
    }

    public Vector<Carte> getMain() {
        return main;
    }

    public void setMain(Vector<Carte> main) {
        this.main = main;
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }
}
