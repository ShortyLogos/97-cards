package com.dm.tpfinal;

import android.content.Context;
import android.widget.TextView;

import java.util.Collections;
import java.util.Vector;

public class Jeu {
    private int nbCartes;
    private Vector<Carte> jeu;

    public Jeu(int nbCartes, Context context) {
        this.nbCartes = nbCartes;
        jeu = new Vector<Carte>(nbCartes);

        for (int i = 1; i <= nbCartes; i++) {
            jeu.add(new Carte(i));
        }

        // On mélange le paquet de cartes lors de sa création
        Collections.shuffle(jeu);
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public Vector<Carte> getJeu() {
        return jeu;
    }

    public Carte pigerCarte(Main main, TextView t) {
        Carte c = jeu.remove(0);
        main.ajouterCarte(c);
        nbCartes--;

        // On actualise le nombre de cartes du paquet chaque fois qu'on en pige une
        // grâce au TextView passé en paramètre
        t.setText(String.valueOf(nbCartes));

        return  c;
    }



}
