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

    Jeu jeu;
    Main main;

    TextView carte;
    ViewGroup.LayoutParams params;
    // LayoutParams utilisés pour modèle de carte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nbCartes = findViewById(R.id.nbCartes);
        rangeeMain1 = findViewById(R.id.rangeeMain1);
        rangeeMain2 = findViewById(R.id.rangeeMain2);

        // On récupère le modèle de carte de la pile pour
        // le réutiliser dans la génération de nouvelles cartes
        carte = findViewById(R.id.carteAscendante1);
        params = carte.getLayoutParams();

        // Création du paquet de carte
        jeu = new Jeu(97, this);

        // Création de la main
        main = new Main(8);

        // Distribution des 8 cartes aléatoires de départ
        while (main.getNbCartes() < main.getLimite()) {

            Carte c = jeu.pigerCarte(main, nbCartes);
            TextView tvCarte = new TextView(this);
            modeleCarte(tvCarte);
            tvCarte.setText(String.valueOf(c.getValeur()));

            if (rangeeMain1.getChildCount() < (main.getLimite()/2)) {
                rangeeMain1.addView(tvCarte);
            }
            else {
                rangeeMain2.addView(tvCarte);
            }
        }
    }

    public void modeleCarte(TextView carte) {
        carte.setTextColor(Color.BLACK);
        carte.setTextSize(26);
        carte.setTypeface(null, Typeface.BOLD);
        carte.setBackgroundResource(R.drawable.card_background);
        carte.setLayoutParams(params);
        int padTB = conversionDpPx(this, 10);
        int padLR = conversionDpPx(this, 12);
        carte.setPadding(padLR, padTB, padLR, padTB);
    }

    public int conversionDpPx(Context context, float dp) {
        float px = dp * context.getResources().getDisplayMetrics().density;
        return (int)px;
    }
}