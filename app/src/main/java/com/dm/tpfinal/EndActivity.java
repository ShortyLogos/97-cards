package com.dm.tpfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndActivity extends AppCompatActivity {

    TextView resultatAnnonce;
    TextView scoreCourant;
    Button btnRejouer;
    Button btnAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        // On récupère les données passées par MainActivity
        Bundle bundle = getIntent().getExtras();
        boolean resultat = bundle.getBoolean("victoire");
        int score = bundle.getInt("score");

        resultatAnnonce = findViewById(R.id.resultatAnnonce);
        scoreCourant = findViewById(R.id.scoreCourant);
        btnRejouer = findViewById(R.id.btnRejouer);
        btnAccueil = findViewById(R.id.btnAccueil);

        if (resultat) {
            resultatAnnonce.setText(getResources().getText(R.string.victoire));
        }
        else {
            resultatAnnonce.setText(getResources().getText(R.string.defaite));
        }

        scoreCourant.setText(String.valueOf(score));

        // Vérification d'un nouveau record établit

        Ecouteur ec = new Ecouteur();

        btnRejouer.setOnClickListener(ec);
        btnAccueil.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View source) {
            if (source == btnRejouer) {
                Intent i = new Intent(EndActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
            else if (source == btnAccueil) {
                Intent i = new Intent(EndActivity.this, WelcomeActivity.class);
                finish();
                startActivity(i);
            }
        }
    }
}