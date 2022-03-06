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
        // Plusieurs fois pour augmenter l'étendue de la randomisation
        Collections.shuffle(jeu);
        Collections.shuffle(jeu);
        Collections.shuffle(jeu);
    }

    public int getNbCartes() {
        return nbCartes;
    }

    public Vector<Carte> getJeu() {
        return jeu;
    }

    public Carte pigerCarte() {
        Carte c = jeu.remove(0);
        nbCartes = jeu.size();

        return  c;
    }



}
