package com.dm.tpfinal;

import android.widget.TextView;

public class Carte {
    private int valeur;
    private TextView textView;

    public Carte(int valeur, TextView textView) {
        this.valeur = valeur;
        this.textView = textView;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
