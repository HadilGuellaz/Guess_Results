package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.guess_results.R;

public class ResultsFragment extends Fragment {


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_results, container, false);

        return view;


        modulesTable = view.findViewById(R.id.modules_table);
        averageText = view.findViewById(R.id.average);
        loadModules();
    }


    

    private void loadModules() {
        DBHelper dbHelper = new DBHelper(this);
        List<Module> modules = dbHelper.getAllModules();
        float totalMoy = 0;
        float totalCoef = 0;

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

           //calculer la moyenne total
            float moy = ((module.getEval() * module.getEvalPerc()) + (module.getExam() * module.getExamPerc())) / 100;
            totalMoy += moy * module.getCoef();
            totalCoef += module.getCoef();

            // Créer un TextView pour la moyenne et l'afficher
            TextView moyText = new TextView(this);
            moyText.setText(String.format("%.2f", moy));
            moyText.setPadding(8, 8, 8, 8);

            // ajouter les element au tableau
            newRow.addView(nameText);
            newRow.addView(coefText);
            newRow.addView(evalText);
            newRow.addView(examText);
            newRow.addView(moyText);
            modulesTable.addView(newRow);
        }

        // Calculer la moyenne globale
        if (totalCoef != 0) {
            float average = totalMoy / totalCoef;
            averageText.setText(String.format("%.2f", average));
        } else {
            averageText.setText("0.00");
        }
    }
}
