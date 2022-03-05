package com.dm.tpfinal;

import java.util.Vector;

public class Partie {
    Vector<Pile> piles;
    int chronometre;
    int score;

    // Possiblement coder dans cette classe les conditions de fin de partie
    // et celle qui permet de vérifier si la partie est dans un cul de sac ou non

    public Partie(Vector<Pile> piles) {
        this.piles = piles;
        chronometre = 0;
        score = 0;
    }

    public boolean isCoupPossible(Main main) {
        for (Pile pile : piles) {
            for (Carte carte : main.getMain()) {
                if (pile.isPossible(carte)) {
                    return true;
                }
            }
        }

        return false;
    }
}
