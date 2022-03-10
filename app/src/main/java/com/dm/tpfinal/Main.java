package com.dm.tpfinal;

import java.util.ArrayList;
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

    public ArrayList<Carte> refaireMain(Jeu jeu) {
        ArrayList<Carte> cartes = new ArrayList<>();

        while (getNbCartes() < getLimite()) {

            if (jeu.getNbCartes() > 0) {
                Carte c = jeu.pigerCarte();
                ajouterCarte(c);    // On ajoute la carte à la main
                cartes.add(c);      // On l'ajoute au ArrayList retourné à la vue pour le pendant graphique
            }
            else {
                break;
            }
        }

        return cartes;
    }

}
