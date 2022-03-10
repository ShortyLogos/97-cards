package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class WelcomeActivity extends AppCompatActivity {

    GestionBD dbInstance;
    Button btnJouer;
    TextView meilleurScore;
    TextView nbPartie;
    TextView nbVictoire;
    TextView ratioPV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbInstance = GestionBD.getInstance(this);

        btnJouer = findViewById(R.id.btnJouer);
        meilleurScore = findViewById(R.id.meilleurScore);
        nbPartie = findViewById(R.id.nbPartie);
        nbVictoire = findViewById(R.id.nbVictoire);
        ratioPV = findViewById(R.id.ratioPV);

        Ecouteur ec = new Ecouteur();

        btnJouer.setOnClickListener(ec);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbInstance.connecterBD();

        int score = dbInstance.recupererScore();
        int partie = dbInstance.recupererNbPartie();
        int victoire = dbInstance.recupererNbVictoire();

        String ratio = "0%";
        if (partie > 0) {
            double pv = (victoire * 100.0)/partie;  // Calcul du ratio partie/victoire en %
            DecimalFormat df = new DecimalFormat("0.00");
            ratio = df.format(pv) + "%";
        }

        meilleurScore.setText(String.valueOf(score));
        nbPartie.setText(String.valueOf(partie));
        nbVictoire.setText(String.valueOf(victoire));
        ratioPV.setText(ratio);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dbInstance.deconnecterBD();
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
//            finish();
            startActivity(i);
        }
    }
}