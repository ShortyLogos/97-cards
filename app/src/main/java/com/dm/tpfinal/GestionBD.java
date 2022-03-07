package com.dm.tpfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestionBD extends SQLiteOpenHelper {

    // Instance unique de la classe Singleton
    private static GestionBD instance;
    private SQLiteDatabase database;

    // Constructeur particulier d'un Singleton
    public static GestionBD getInstance(Context contexte) {
        if (instance == null) {
            instance = new GestionBD(contexte.getApplicationContext());
        }
        return instance;
    }

    private GestionBD(Context context) {super(context, "db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE joueur (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "meilleur_score INTEGER," +
                "nb_partie INTEGER," +
                "nb_victoire INTEGER)");

        ContentValues cv = new ContentValues();
        cv.put("meilleur_score", 0);
        cv.put("nb_partie", 0);
        cv.put("nb_victoire", 0);

        db.insert("joueur", null, cv);
    }

    // Méthode pour récupérer meilleur score
    public int recupererScore() {
        int meilleurScore = 0;

        Cursor cursor = database.rawQuery("SELECT score FROM joueur", null);
        cursor.moveToNext();

        meilleurScore = cursor.getInt(0);
        return  meilleurScore;
    }

    // Méthode pour récupérer nombre de parties

    // Méthode pour récupérer nombre de victoires

    // Méthode pour remplacer meilleur score
    public void changerScore(int nouveauScore) {
        String[] tab = {String.valueOf(nouveauScore)};
        Cursor cursor = database.rawQuery("UPDATE joueur SET meilleur_score =?", tab);
        cursor.moveToNext();
    }

    // Méthode pour ajouter une partie au nombre de parties jouées

    // Méthode pour ajouter une victoire dans la base de données

    public void connecterBD() { database = this.getWritableDatabase(); }

    public void deconnecterBD() { database.close(); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE joueur");
    }
}