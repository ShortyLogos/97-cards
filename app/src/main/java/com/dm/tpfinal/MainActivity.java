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
import android.os.Handler;
import android.os.Looper;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Variables globales de nature graphique
    Button btnReprendre;
    Button btnNouveau;
    TextView nbCartes;
    TextView score;
    Chronometer chrono;
    LinearLayout zonePile;
    LinearLayout pileAscendante1;
    LinearLayout pileAscendante2;
    LinearLayout pileDescendante1;
    LinearLayout pileDescendante2;
    LinearLayout zoneMain;
    LinearLayout rangeeMain1;
    LinearLayout rangeeMain2;
    CarteView cvJouee;
    CarteView cvSurPile;
    int indexCartePile;
    LinearLayout dernierePile;

    // Variables globales de nature logique
    Partie partie;
    Jeu jeu;
    Main main;
    Pile pileAsc1;
    Pile pileAsc2;
    Pile pileDes1;
    Pile pileDes2;
    Pile pileActive;
    boolean retourPossible;

    // En variable globale pour certaines méthodes de l'Activité
    Ecouteur ec;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReprendre = findViewById(R.id.btnReprendre);
        btnNouveau = findViewById(R.id.btnNouveau);

        // Zone d'informations sur la partie en cours
        nbCartes = findViewById(R.id.nbCartes);
        chrono = (Chronometer)findViewById(R.id.chrono);
        score = findViewById(R.id.score);

        // On récupère les LinearLayout qui contiennent les différentes piles
        zonePile = findViewById(R.id.zonePile);
        pileAscendante1 = findViewById(R.id.pileAscendante1);
        pileAscendante2 = findViewById(R.id.pileAscendante2);
        pileDescendante1 = findViewById(R.id.pileDescendante1);
        pileDescendante2 = findViewById(R.id.pileDescendante2);

        // Zones de la main
        zoneMain = findViewById(R.id.zoneMain);
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
        // afin de pouvoir vérifier après chaque coup si la partie est dans un cul-de-sac
        Vector<Pile> piles = new Vector<Pile>();
        piles.add(pileAsc1);
        piles.add(pileAsc2);
        piles.add(pileDes1);
        piles.add(pileDes2);

        // Création de la main
        main = new Main(8);

        // Distribution des 8 cartes aléatoires de départ
        refaireMain();

        // Affichage du nombre de cartes restantes du Jeu
        nbCartes.setText(String.valueOf(jeu.getNbCartes()));

        // Création de l'écouteur
        ec = new Ecouteur();

        // Inscription des sources à l'écouteur
        // Main
        for (int i = 0; i < zoneMain.getChildCount(); i++) {
            LinearLayout rangeeMain = (LinearLayout)zoneMain.getChildAt(i);
            for (int j = 0; j < rangeeMain.getChildCount(); j++) {
                CarteView cv = (CarteView)rangeeMain.getChildAt(j);
                cv.setOnTouchListener(ec);
            }
        }
        // Piles
        for (int i = 0; i < zonePile.getChildCount(); i++) {
            LinearLayout rangeePile = (LinearLayout)zonePile.getChildAt(i);
            for (int j = 0; j < rangeePile.getChildCount(); j++) {
                LinearLayout pile = (LinearLayout)rangeePile.getChildAt(j);
                pile.setOnDragListener(ec);
            }
        }

        btnReprendre.setOnClickListener(ec);
        btnNouveau.setOnClickListener(ec);

        // Création de l'objet Partie
        partie = new Partie(piles);
        chrono.start();
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener, View.OnClickListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onDrag(View source, DragEvent dragEvent) {
            CarteView carte = (CarteView) dragEvent.getLocalState(); // la carte

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    source.setBackgroundColor(Color.parseColor("#7729856B"));
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    source.setBackgroundColor(Color.TRANSPARENT);
                    break;

                case DragEvent.ACTION_DROP:
                    source.setBackgroundColor(Color.TRANSPARENT);
                    boolean legal = false;
                    dernierePile = (LinearLayout)source;

                    // Vérification de la légalité du coup
                    // Nécessaire de passer par une série de if, puisqu'on ne peut pas définir
                    // une sous-classe de LinearLayout auquel on ajouterait une variable d'instance Pile
                    if (dernierePile.equals(pileAscendante1))
                        pileActive = pileAsc1;
                    else if (dernierePile.equals(pileAscendante2))
                        pileActive = pileAsc2;
                    else if (dernierePile.equals(pileDescendante1))
                        pileActive = pileDes1;
                    else if (dernierePile.equals(pileDescendante2))
                        pileActive = pileDes2;

                    legal = pileActive.isPossible(carte.getCarte());

                    // Si le coup est permis, on remplace la carte active de la pile
                    if (legal) {
                        partie.ajoutSequence(); // On compte le nombre de coups consécutifs pour gérer le bouton Retour
                        cvSurPile = new CarteView(MainActivity.this, pileActive.getCarteActive());  // On garde en mémoire le CarteView sur la pile dans le cas d'un retour possible
                        cvJouee = carte;

                        pileActive.setCarteActive(carte.getCarte()); // On remplace le CarteView sur la pile par celui de la carte jouée
                        main.retirerCarte(carte.getCarte());
                        LinearLayout p = (LinearLayout)carte.getParent(); // Récupère le parent conteneur d'origine
                        p.removeView(carte); // Enlève la carte du LinearLayout d'origine (ici la rangée correspondante dans la main)

                        for (int i = 0; i < dernierePile.getChildCount(); i++) {
                            if (dernierePile.getChildAt(i) instanceof TextView) {
                                View v = dernierePile.getChildAt(i);
                                indexCartePile = dernierePile.indexOfChild(v);
                            }
                        }

                        remplacerCarte(dernierePile, carte, indexCartePile);
                        carte.setOnTouchListener(null);

                        retourPossible = partie.isRetourPossible(carte.getCarte(), pileActive.getCarteActive(), pileActive, indexCartePile);
                        if (retourPossible)
                            btnReprendre.setTextColor(Color.parseColor("#062A34"));
                        else {
                            btnReprendre.setTextColor(getResources().getColor(R.color.reprendreInactif));
                        }

                        partie.ajoutScore(10);
                        score.setText(String.valueOf(partie.getScore()));

                        if (main.getNbCartes() == main.getSeuilPige()) {
                            refaireMain();
                        }

                        break;
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    carte.setVisibility(View.VISIBLE);

                default:
                    break;
            }

            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onTouch(View carteView, MotionEvent event) {

            View.DragShadowBuilder dragshadow = new View.DragShadowBuilder(carteView);
            carteView.startDragAndDrop(null, dragshadow, carteView, View.DRAG_FLAG_OPAQUE);

            carteView.setVisibility(View.INVISIBLE);

            return true;
        }

        @Override
        public void onClick(View source) {
            if (source == btnNouveau) {
                finish();
                startActivity(getIntent());
            }
            else if (source == btnReprendre) {
                if (retourPossible) {
                    btnReprendre.setTextColor(getResources().getColor(R.color.reprendreInactif));
                    retourArriere();
                }
            }
        }
    }

    // Classe interne qui permet de designer graphiquement une carte comme sous-classe d'un TextView
    private class CarteView extends androidx.appcompat.widget.AppCompatTextView {
        private Carte carte;

        public CarteView(Context context, Carte carte) {
            super(context);

            // Caractéristiques essentielles
            this.setTextColor(Color.parseColor("#062A34"));
            this.setTextSize(22);
            this.setTypeface(null, Typeface.BOLD);
            int largeur = Utils.conversionDpPx(MainActivity.this, 70);
            int hauteur = Utils.conversionDpPx(MainActivity.this, 105);
            this.setWidth(largeur);
            this.setHeight(hauteur);

            // Couleur de la carte déterminée par sa valeur
            if (carte.getValeur() == 0 || carte.getValeur() == 98)
                this.setBackgroundResource(R.drawable.card_background_pile);
            else if (carte.getValeur() < 10)
                this.setBackgroundResource(R.drawable.card_background1);
            else if (carte.getValeur() < 20)
                this.setBackgroundResource(R.drawable.card_background10);
            else if (carte.getValeur() < 30)
                this.setBackgroundResource(R.drawable.card_background20);
            else if (carte.getValeur() < 40)
                this.setBackgroundResource(R.drawable.card_background30);
            else if (carte.getValeur() < 50)
                this.setBackgroundResource(R.drawable.card_background40);
            else if (carte.getValeur() < 60)
                this.setBackgroundResource(R.drawable.card_background50);
            else if (carte.getValeur() < 70)
                this.setBackgroundResource(R.drawable.card_background60);
            else if (carte.getValeur() < 80)
                this.setBackgroundResource(R.drawable.card_background70);
            else if (carte.getValeur() < 90)
                this.setBackgroundResource(R.drawable.card_background80);
            else if (carte.getValeur() < 100)
                this.setBackgroundResource(R.drawable.card_background90);

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

    @SuppressLint("ClickableViewAccessibility")
    public CarteView insererCarteMain(Carte c) {

        main.ajouterCarte(c);
        CarteView carteView = new CarteView(this, c);
        carteView.setOnTouchListener(ec);

        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        if (rangeeMain1.getChildCount() < (main.getLimite()/2)) {
            rangeeMain1.addView(carteView);
        }
        else {
            rangeeMain2.addView(carteView);
        }
        carteView.startAnimation(fadeIn);

        return carteView;
    }

    public void refaireMain() {

        while (main.getNbCartes() < main.getLimite()) {

            if (jeu.getNbCartes() > 0) {
                Carte c = jeu.pigerCarte();
                insererCarteMain(c);
            }
        }

        nbCartes.setText(String.valueOf(jeu.getNbCartes()));
    }

    public void remplacerCarte(LinearLayout pile, CarteView carte, int indexView) {
        pile.removeViewAt(indexView);
        pile.addView(carte, indexView);
    }

    public void retourArriere() {
        Carte carteJouee = partie.getCarteJouee();

        remplacerCarte(dernierePile, cvSurPile, indexCartePile);
        pileActive.setCarteActive(cvSurPile.getCarte());
        CarteView carteView = insererCarteMain(carteJouee);

        partie.setSequence(0);
        retourPossible = false;
    }

}


//pileActive.setCarteActive(carte.getCarte());
//        main.retirerCarte(carte.getCarte());
//        LinearLayout p = (LinearLayout)carte.getParent(); // Récupère le parent conteneur d'origine
//        p.removeView(carte); // Enlève la carte du LinearLayout d'origine
//
//        for (int i = 0; i < pile.getChildCount(); i++) {
//        if (pile.getChildAt(i) instanceof TextView) {
//        View v = pile.getChildAt(i);
//        indexView = pile.indexOfChild(v);
//        }
//        }
//
//        remplacerCarte(pile, carte, indexView);

