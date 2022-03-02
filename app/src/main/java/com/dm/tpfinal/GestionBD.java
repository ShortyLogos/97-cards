package com.dm.tpfinal;

import android.content.Context;
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
        db.execSQL("CREATE TABLE score (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER)");
    }

    // À coder : méthode pour ajouter score et une autre pour récupérer meilleurs scores

    public void connecterBD() { database = this.getWritableDatabase(); }

    public void deconnecterBD() { database.close(); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE score");
    }
}
