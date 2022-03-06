package com.dm.tpfinal;

import java.util.Vector;

public class Main {
    private Vector<Carte> main;
    private int nbCartes;
    private int limite;
    private int seuilPige;

    public Main(int limite) {
        nbCartes = 0;
        seuilPige = limite - 2;
        main = new Vector<Carte>(limite);
        this.limite = limite;
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

    public int getLimite() { return limite; }

    public int getSeuilPige() {
        return seuilPige;
    }

    public void ajouterCarte(Carte c) {
        main.add(c);
        nbCartes = main.size();
    }

    public void retirerCarte(Carte c) {
        main.remove(c);
        nbCartes = main.size();
    }

}
