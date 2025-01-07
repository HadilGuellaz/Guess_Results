package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.guess_results.DBHelper;
import com.example.guess_results.Module;
import com.example.guess_results.R;

import java.util.List;

public class ResultsFragment extends Fragment {

    private TableLayout modulesTable;
    private TextView averageText;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_results, container, false);
        modulesTable = view.findViewById(R.id.results_table);
        averageText = view.findViewById(R.id.average);
        loadModules();
        return view;
    }


    

    private void loadModules() {
        DBHelper dbHelper = new DBHelper(getActivity());
        List<Module> modules = dbHelper.getAllModules();
        float totalMoy = 0;
        float totalCoef = 0;

        for (Module module : modules) {
            TableRow newRow = new TableRow(getActivity());

            // Création des TextViews pour chaque colonne
            TextView nameText = new TextView(getActivity());
            nameText.setText(module.getName());
            nameText.setPadding(8, 8, 8, 8);

            TextView coefText = new TextView(getActivity());
            coefText.setText(String.valueOf(module.getCoef()));
            coefText.setPadding(8, 8, 8, 8);

            TextView evalText = new TextView(getActivity());
            evalText.setText(String.valueOf(module.getEval()));
            evalText.setPadding(8, 8, 8, 8);

            TextView examText = new TextView(getActivity());
            examText.setText(String.valueOf(module.getExam()));
            examText.setPadding(8, 8, 8, 8);

           //calculer la moyenne total
            float moy = ((module.getEval() * module.getEvalPerc()) + (module.getExam() * module.getExamPerc())) / 100;
            totalMoy += moy * module.getCoef();
            totalCoef += module.getCoef();

            // Créer un TextView pour la moyenne et l'afficher
            TextView moyText = new TextView(getActivity());
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
