package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.guess_results.DBHelper;
import com.example.guess_results.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DataFragment extends Fragment {

    private TextInputEditText name, coef, eval, evalPerc, exam, examPerc;
    private MaterialCardView saveButton;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_data, container, false);

        init();

        saveButton.setOnClickListener(v -> {
            String validationMessage = validateFields();

            if (validationMessage == null) {
                String nameValue = name.getText().toString();
                float coefValue = Float.parseFloat(coef.getText().toString());
                float evalValue = Float.parseFloat(eval.getText().toString());
                float evalPercValue = Float.parseFloat(evalPerc.getText().toString());
                float examValue = Float.parseFloat(exam.getText().toString());
                float examPercValue = Float.parseFloat(examPerc.getText().toString());

                DBHelper dbHelper = new DBHelper(getActivity());
                long id = dbHelper.insertModule(nameValue, coefValue, evalValue, evalPercValue, examValue, examPercValue);

                if (id != -1) {
                    Toast.makeText(getActivity(), "Données enregistrées avec succès!", Toast.LENGTH_SHORT).show();
                    getParentFragmentManager().popBackStack("ModuleFragment", 1);
                } else {
                    Toast.makeText(getActivity(), "Erreur lors de l'enregistrement des données!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), validationMessage, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void init() {
        name = view.findViewById(R.id.edit_name);
        coef = view.findViewById(R.id.edit_coef);
        eval = view.findViewById(R.id.edit_eval);
        evalPerc = view.findViewById(R.id.edit_eval_perc);
        exam = view.findViewById(R.id.edit_exam);
        examPerc = view.findViewById(R.id.edit_exam_perc);
        saveButton = view.findViewById(R.id.save);
    }

    private String validateFields() {
        if (name.getText().toString().isEmpty() ||
                coef.getText().toString().isEmpty() ||
                eval.getText().toString().isEmpty() ||
                evalPerc.getText().toString().isEmpty() ||
                exam.getText().toString().isEmpty() ||
                examPerc.getText().toString().isEmpty()) {
            return "Tous les champs doivent être remplis!";
        }

        try {
            float evalNote = Float.parseFloat(eval.getText().toString());
            float examNote = Float.parseFloat(exam.getText().toString());
            if (evalNote < 0 || evalNote > 20) {
                return "La note de l'évaluation doit être entre 0 et 20!";
            }
            if (examNote < 0 || examNote > 20) {
                return "La note de l'examen doit être entre 0 et 20!";
            }
        } catch (NumberFormatException e) {
            return "Veuillez entrer des nombres valides pour les notes!";
        }

        try {
            float evalPercValue = Float.parseFloat(evalPerc.getText().toString());
            float examPercValue = Float.parseFloat(examPerc.getText().toString());
            if (evalPercValue < 0 || examPercValue < 0) {
                return "Les pourcentages ne peuvent pas être négatifs!";
            }
            if (evalPercValue + examPercValue != 100) {
                return "La somme des pourcentages doit être égale à 100%!";
            }
        } catch (NumberFormatException e) {
            return "Veuillez entrer des pourcentages valides!";
        }

        try {
            float coefValue = Float.parseFloat(coef.getText().toString());
            if (coefValue <= 0) {
                return "Le coefficient doit être supérieur à 0!";
            }
        } catch (NumberFormatException e) {
            return "Veuillez entrer un coefficient valide!";
        }

        return null;
    }
}
