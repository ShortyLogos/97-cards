package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView nbCartes;
    LinearLayout rangeeMain1;
    LinearLayout rangeeMain2;
    LinearLayout pileAscendante1;
    LinearLayout pileAscendante2;
    LinearLayout pileDescendante1;
    LinearLayout pileDescendante2;
    TextView carteAscendante1;
    TextView carteAscendante2;
    TextView carteDescendante1;
    TextView carteDescendante2;

    Partie partie;
    Jeu jeu;
    Main main;
    Pile pile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Zone d'informations sur la partie en cours
        nbCartes = findViewById(R.id.nbCartes);

        // Zone des piles
        pileAscendante1 = findViewById(R.id.pileAscendante1);
        pileAscendante2 = findViewById(R.id.pileAscendante2);
        pileDescendante1 = findViewById(R.id.pileDescendante1);
        pileDescendante2 = findViewById(R.id.pileDescendante2);

        // On récupère les cartes initiales de la pile pour les gérer éventuellement
        carteAscendante1 = findViewById(R.id.carteAscendante1);
        carteAscendante2 = findViewById(R.id.carteAscendante2);
        carteDescendante1 = findViewById(R.id.carteDescendante1);
        carteDescendante2 = findViewById(R.id.carteDescendante2);

        // Zone de la main
        rangeeMain1 = findViewById(R.id.rangeeMain1);
        rangeeMain2 = findViewById(R.id.rangeeMain2);

        // Création du paquet de carte
        jeu = new Jeu(97, this);

        // Création de la main
        main = new Main(8);

        // Distribution des 8 cartes aléatoires de départ
        while (main.getNbCartes() < main.getLimite()) {

            Carte c = jeu.pigerCarte(main, nbCartes);
            CarteView tvCarte = new CarteView(this, c);

            if (rangeeMain1.getChildCount() < (main.getLimite()/2)) {
                rangeeMain1.addView(tvCarte);
            }
            else {
                rangeeMain2.addView(tvCarte);
            }
        }
    }

    // Classe interne qui permet de designer graphiquement une carte comme sous-classe d'un TextView
    private class CarteView extends androidx.appcompat.widget.AppCompatTextView {
        private Carte c;

        public CarteView(Context context, Carte c) {
            super(context);

            // Caractéristiques essentielles
            this.setTextColor(Color.BLACK);
            this.setTextSize(26);
            this.setTypeface(null, Typeface.BOLD);
            this.setBackgroundResource(R.drawable.card_background);
            int largeur = Utils.conversionDpPx(MainActivity.this, 75);
            int hauteur = Utils.conversionDpPx(MainActivity.this, 115);
            this.setWidth(largeur);
            this.setHeight(hauteur);

            // Marges
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int marge = Utils.conversionDpPx(MainActivity.this, 10);
            params.setMargins(marge, 0, marge, 0);
            this.setLayoutParams(params);

            // Padding
            int padTB = Utils.conversionDpPx(MainActivity.this, 10);
            int padLR = Utils.conversionDpPx(MainActivity.this, 12);
            this.setPadding(padLR, padTB, padLR, padTB);

            // Affichage de la valeur de la carte
            this.c = c;
            this.setText(String.valueOf(c.getValeur()));
        }
    }
}