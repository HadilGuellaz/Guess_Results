package com.example.guess_results;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "modules";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COEF = "coef";
    public static final String COLUMN_EVAL = "eval";
    public static final String COLUMN_EVAL_PERC = "eval_perc";
    public static final String COLUMN_EXAM = "exam";
    public static final String COLUMN_EXAM_PERC = "exam_perc";

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
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insertModule(String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COEF, coef);
        contentValues.put(COLUMN_EVAL, eval);
        contentValues.put(COLUMN_EVAL_PERC, evalPerc);
        contentValues.put(COLUMN_EXAM, exam);
        contentValues.put(COLUMN_EXAM_PERC, examPerc);

        long id = db.insert(TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }
    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Récupérer les valeurs des colonnes dans la base de données
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    float coef = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_COEF));
                    float eval = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_EVAL));
                    float evalPerc = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_EVAL_PERC));
                    float exam = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_EXAM));
                    float examPerc = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_EXAM_PERC));

                    // Créer un objet Module et l'ajouter à la liste
                    Module module = new Module(id, name, coef, eval, evalPerc, exam, examPerc);
                    modules.add(module);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Loguer l'erreur pour debugger si nécessaire
        } finally {
            // Fermeture des ressources dans le bloc finally pour éviter les fuites de mémoire
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return modules;
    }


    public int updateModule(int id, String name, float coef, float eval, float evalPerc, float exam, float examPerc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COEF, coef);
        contentValues.put(COLUMN_EVAL, eval);
        contentValues.put(COLUMN_EVAL_PERC, evalPerc);
        contentValues.put(COLUMN_EXAM, exam);
        contentValues.put(COLUMN_EXAM_PERC, examPerc);

        return db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    public boolean deleteModule(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(id) };
        int rowsAffected = db.delete("modules", whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }


}
