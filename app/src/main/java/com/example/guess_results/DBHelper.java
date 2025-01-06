package com.example.guess_results;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Définition du nom et de la version de la base de données
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Définir les tables et les champs de la base de données
    public static final String TABLE_NAME = "modules";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COEF = "coef";
    public static final String COLUMN_EVAL = "eval";
    public static final String COLUMN_EVAL_PERC = "eval_perc";
    public static final String COLUMN_EXAM = "exam";
    public static final String COLUMN_EXAM_PERC = "exam_perc";

    // Requête SQL pour créer la table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_COEF + " REAL, " +
                    COLUMN_EVAL + " REAL, " +
                    COLUMN_EVAL_PERC + " REAL, " +
                    COLUMN_EXAM + " REAL, " +
                    COLUMN_EXAM_PERC + " REAL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crée la table à la première exécution
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Met à jour la base de données si la version change
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Méthode pour insérer un module dans la base de données
    public long insertModule(String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        // Obtenir la base de données en mode écriture
        SQLiteDatabase db = this.getWritableDatabase();

        // Créer un objet ContentValues pour contenir les données
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COEF, coef);
        contentValues.put(COLUMN_EVAL, eval);
        contentValues.put(COLUMN_EVAL_PERC, evalPerc);
        contentValues.put(COLUMN_EXAM, exam);
        contentValues.put(COLUMN_EXAM_PERC, examPerc);

        // Insérer les données dans la base de données
        long id = db.insert(TABLE_NAME, null, contentValues);

        // Fermer la base de données après l'insertion
        db.close();

        // Retourner l'ID de l'insertion ou -1 en cas d'erreur
        return id;
    }
    public List<String> getAllModules() {
        List<String> modules = new ArrayList<>();

        // Obtenir une instance en lecture de la base de données
        SQLiteDatabase db = this.getReadableDatabase();

        // Exécuter la requête pour récupérer tous les enregistrements
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // Vérifier si des données sont présentes
        if (cursor.moveToFirst()) {
            do {
                // Récupérer le nom du module à partir de la colonne "name"
                String moduleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                // Ajouter le nom du module à la liste
                modules.add(moduleName);
            } while (cursor.moveToNext());
        }

        // Fermer le curseur et la base de données
        cursor.close();
        db.close();

        // Retourner la liste des noms des modules
        return modules;
    }


}
