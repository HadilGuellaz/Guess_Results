package com.example.guess_results;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TableLayout modulesTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Référence au TableLayout dans le fichier XML
        modulesTable = findViewById(R.id.modules_table);

        // Charger les modules depuis la base de données
        loadModules();
    }

    private void loadModules() {
        DBHelper dbHelper = new DBHelper(this);
        List<Module> modules = dbHelper.getAllModules(); // Récupérer tous les modules de la base

        for (Module module : modules) {
            TableRow newRow = new TableRow(this);

            // Création des TextViews pour chaque colonne
            TextView nameText = new TextView(this);
            nameText.setText(module.getName());
            nameText.setPadding(8, 8, 8, 8);

            TextView coefText = new TextView(this);
            coefText.setText(String.valueOf(module.getCoef()));
            coefText.setPadding(8, 8, 8, 8);

            TextView evalText = new TextView(this);
            evalText.setText(String.valueOf(module.getEval()));
            evalText.setPadding(8, 8, 8, 8);

            TextView examText = new TextView(this);
            examText.setText(String.valueOf(module.getExam()));
            examText.setPadding(8, 8, 8, 8);

            // Ajouter les TextViews dans la rangée
            newRow.addView(nameText);
            newRow.addView(coefText);
            newRow.addView(evalText);
            newRow.addView(examText);

            // Ajouter la rangée dans le tableau
            modulesTable.addView(newRow);
        }
    }
}
