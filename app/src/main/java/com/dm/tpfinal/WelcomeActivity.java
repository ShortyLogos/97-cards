package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    GestionBD dbInstance;
    TextView meilleurScore;
    Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbInstance = GestionBD.getInstance(this);
        dbInstance.connecterBD();

        meilleurScore = findViewById(R.id.meilleurScore);
        btnJouer = findViewById(R.id.btnJouer);

        Ecouteur ec = new Ecouteur();

        btnJouer.setOnClickListener(ec);

        int score = dbInstance.recupererScore();
        meilleurScore.setText(String.valueOf(score));
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
            startActivity(i);
        }
    }
}