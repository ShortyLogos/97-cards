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

        Cursor cursor = database.rawQuery("SELECT meilleur_score FROM joueur", null);
        cursor.moveToNext();

        meilleurScore = cursor.getInt(0);
        cursor.close();
        return  meilleurScore;
    }

    // Méthode pour récupérer nombre de parties
    public int recupererNbPartie() {
        int nb = 0;

        Cursor cursor = database.rawQuery("SELECT nb_partie FROM joueur", null);
        cursor.moveToNext();

        nb = cursor.getInt(0);
        cursor.close();
        return  nb;
    }

    // Méthode pour récupérer nombre de victoires
    public int recupererNbVictoire() {
        int nb = 0;

        Cursor cursor = database.rawQuery("SELECT nb_victoire FROM joueur", null);
        cursor.moveToNext();

        nb = cursor.getInt(0);
        cursor.close();
        return  nb;
    }

    public boolean changerScore(int nouveauScore) {
        ContentValues cv = new ContentValues();
        cv.put("meilleur_score", nouveauScore);
        database.update("joueur", cv, "_id = 1", null);

        return true;
    }

    // Méthode pour ajouter une partie au nombre de parties jouées
    public boolean nouvellePartie() {
        int p = recupererNbPartie();
        p++;

        ContentValues cv = new ContentValues();
        cv.put("nb_partie", p);
        database.update("joueur", cv, "_id = 1", null);

        return true;
    }

    // Méthode pour ajouter une victoire dans la base de données
    public boolean nouvelleVictoire() {
        int v = recupererNbVictoire();
        v++;

        ContentValues cv = new ContentValues();
        cv.put("nb_victoire", v);
        database.update("joueur", cv, "_id = 1", null);

        return true;
    }

    public void connecterBD() { database = this.getWritableDatabase(); }

    public void deconnecterBD() { database.close(); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE joueur");
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}