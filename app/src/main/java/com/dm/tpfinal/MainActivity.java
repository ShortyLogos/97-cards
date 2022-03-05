package com.dm.tpfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    TextView nbCartes;
    LinearLayout zonePile;
    LinearLayout rangeeMain1;
    LinearLayout rangeeMain2;
    LinearLayout LLpileAsc1;
    LinearLayout LLpileAsc2;
    LinearLayout LLpileDes1;
    LinearLayout LLpileDes2;
    TextView carteAscendante1;
    TextView carteAscendante2;
    TextView carteDescendante1;
    TextView carteDescendante2;

    Partie partie;
    Jeu jeu;
    Main main;

    Pile pileAsc1;
    Pile pileAsc2;
    Pile pileDes1;
    Pile pileDes2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Zone d'informations sur la partie en cours
        nbCartes = findViewById(R.id.nbCartes);

        // On récupère les LinearLayout qui contiennent les différentes piles
        LLpileAsc1 = findViewById(R.id.pileAscendante1);
        LLpileAsc2 = findViewById(R.id.pileAscendante2);
        LLpileDes1 = findViewById(R.id.pileDescendante1);
        LLpileDes2 = findViewById(R.id.pileDescendante2);

        // On récupère les TextView des cartes initiales des piles pour les gérer éventuellement
        carteAscendante1 = findViewById(R.id.carteAscendante1);
        carteAscendante2 = findViewById(R.id.carteAscendante2);
        carteDescendante1 = findViewById(R.id.carteDescendante1);
        carteDescendante2 = findViewById(R.id.carteDescendante2);

        // Zones de la main
        rangeeMain1 = findViewById(R.id.rangeeMain1);
        rangeeMain2 = findViewById(R.id.rangeeMain2);

        // Création du paquet de cartes
        jeu = new Jeu(97, this);

        // Création des piles avec leur carte initiale respective
        pileAsc1 = new Pile(true, new Carte(0));
        pileAsc2 = new Pile(true, new Carte(0));
        pileDes1 = new Pile(false, new Carte(98));
        pileDes2 = new Pile(false, new Carte(98));

        // Création d'un vecteur de piles qu'on ajoutera à l'objet Partie
        Vector<Pile> piles = new Vector<Pile>();
        piles.add(pileAsc1);
        piles.add(pileAsc2);
        piles.add(pileDes1);
        piles.add(pileDes2);

        // Création de la main
        main = new Main(8);

        // Distribution des 8 cartes aléatoires de départ
        while (main.getNbCartes() < main.getLimite()) {

            Carte c = jeu.pigerCarte();
            main.ajouterCarte(c);
            CarteView tvCarte = new CarteView(this, c);

            if (rangeeMain1.getChildCount() < (main.getLimite()/2)) {
                rangeeMain1.addView(tvCarte);
            }
            else {
                rangeeMain2.addView(tvCarte);
            }
        }

        // Affichage du nombre de cartes restantes du Jeu
        nbCartes.setText(String.valueOf(jeu.getNbCartes()));

        // Création de l'écouteur
        Ecouteur ec = new Ecouteur();

        // Inscription des sources à l'écouteur
        // Rangée main 1
        for (int carte = 0; carte < rangeeMain1.getChildCount(); carte++) {
            CarteView cv = (CarteView)rangeeMain1.getChildAt(carte);
            cv.setOnTouchListener(ec);
        }
        // Rangée main 2
        for (int carte = 0; carte < rangeeMain2.getChildCount(); carte++) {
            CarteView cv = (CarteView)rangeeMain2.getChildAt(carte);
            cv.setOnTouchListener(ec);
        }
        // Piles
        LLpileAsc1.setOnDragListener(ec);
        LLpileAsc2.setOnDragListener(ec);
        LLpileDes1.setOnDragListener(ec);
        LLpileDes2.setOnDragListener(ec);

        // Création de l'objet Partie
        partie = new Partie(piles);
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {

        @Override
        public boolean onDrag(View source, DragEvent dragEvent) {
            CarteView carte = (CarteView) dragEvent.getLocalState(); // la carte

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackgroundColor(Color.BLUE);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackgroundColor(Color.TRANSPARENT);
                    break;

                case DragEvent.ACTION_DROP:
                    source.setBackgroundColor(Color.TRANSPARENT);
                    LinearLayout p = (LinearLayout)carte.getParent(); // Récupère le parent conteneur d'origine
                    p.removeView(carte); // Enlève la carte du LinearLayout d'origine
                    LinearLayout pileActive = (LinearLayout)source;
                    pileActive.addView(carte);
                    break;

                 case DragEvent.ACTION_DRAG_ENDED:
                     carte.setVisibility(View.VISIBLE);

                default:
                    break;
            }

            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(v);
            v.startDragAndDrop(null, dragshadow, v, View.DRAG_FLAG_OPAQUE);

            v.setVisibility(View.INVISIBLE);

            return true;
        }
    }

    // Classe interne qui permet de designer graphiquement une carte comme sous-classe d'un TextView
    private class CarteView extends androidx.appcompat.widget.AppCompatTextView {
        private Carte carte;

        public CarteView(Context context, Carte carte) {
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
            this.carte = carte;
            this.setText(String.valueOf(carte.getValeur()));
        }

        public Carte getCarte() {
            return carte;
        }
    }
}