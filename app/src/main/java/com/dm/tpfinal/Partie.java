package com.dm.tpfinal;

import java.util.Vector;

public class Partie {
    private Vector<Pile> piles;
    private int chronometre;
    private int score;
    private int sequence;
    private Carte carteJouee;
    private Carte carteSurPile;
    private Pile dernierePile;

    // Coder dans cette classe les conditions de fin de partie
    // et celle qui permet de vérifier si la partie est dans un cul de sac ou non

    public Partie(Vector<Pile> piles) {
        this.piles = piles;
        chronometre = 0;
        score = 0;
        sequence = 0;
    }

    public int getScore() {
        return score;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public Carte getCarteJouee() {
        return carteJouee;
    }

    public Carte getCarteSurPile() {
        return carteSurPile;
    }

    public Pile getDernierePile() {
        return dernierePile;
    }

    public void setDernierePile(Pile dernierePile) {
        this.dernierePile = dernierePile;
    }

    public void ajoutSequence() {
        sequence += 1;
    }

    public void ajoutScore(int score) {
        this.score += score;
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

    public boolean isRetourPossible(Carte carteJouee, Carte carteSurPile, Pile dernierePile, int indexCartePile) {
        if (sequence >= 2) {
            sequence = 0;
            return false;
        }
        else {
            this.carteJouee = carteJouee;
            this.carteSurPile = carteSurPile;
            this.dernierePile = dernierePile;
            return true;
        }
    }
}
