package com.dm.tpfinal;

import android.content.Context;
import android.widget.TextView;

public class Carte {
    private int valeur;

    public Carte(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
