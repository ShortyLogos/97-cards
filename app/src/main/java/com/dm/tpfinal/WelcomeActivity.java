package com.dm.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnJouer = findViewById(R.id.btnJouer);

        Ecouteur ec = new Ecouteur();

        btnJouer.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}