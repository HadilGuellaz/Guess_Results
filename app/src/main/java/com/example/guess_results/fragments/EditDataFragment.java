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
import com.example.guess_results.Module;
import com.example.guess_results.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class EditDataFragment extends Fragment {

    private TextInputEditText name, coef, eval, evalPerc, exam, examPerc;
    private MaterialCardView saveButton;
    private View view;
    private Module moduleToEdit; // Module object to be edited

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_data, container, false);

        init();

        if (getArguments() != null) {
            moduleToEdit = getArguments().getParcelable("module");

            if (moduleToEdit != null) {
                // Prepopulate the EditText fields with the values of the selected Module
                name.setText(moduleToEdit.getName());
                coef.setText(String.valueOf(moduleToEdit.getCoef()));
                eval.setText(String.valueOf(moduleToEdit.getEval()));
                evalPerc.setText(String.valueOf(moduleToEdit.getEvalPerc()));
                exam.setText(String.valueOf(moduleToEdit.getExam()));
                examPerc.setText(String.valueOf(moduleToEdit.getExamPerc()));
            }
        }

        saveButton.setOnClickListener(v -> {
            String validationMessage = validateFields();

            if (validationMessage == null) {
                // Gather updated values from EditText
                String nameValue = name.getText().toString();
                float coefValue = Float.parseFloat(coef.getText().toString());
                float evalValue = Float.parseFloat(eval.getText().toString());
                float evalPercValue = Float.parseFloat(evalPerc.getText().toString());
                float examValue = Float.parseFloat(exam.getText().toString());
                float examPercValue = Float.parseFloat(examPerc.getText().toString());

                // Update the Module in the database
                DBHelper dbHelper = new DBHelper(getActivity());
                int rowsUpdated = dbHelper.updateModule(moduleToEdit.getId(), nameValue, coefValue, evalValue, evalPercValue, examValue, examPercValue);

                if (rowsUpdated > 0) {
                    Toast.makeText(getActivity(), "Données mises à jour avec succès!", Toast.LENGTH_SHORT).show();
                    // Return specifically to the ModuleFragment
                    getParentFragmentManager().popBackStack("ModuleFragment", 1);

                } else {
                    Toast.makeText(getActivity(), "Erreur lors de la mise à jour des données!", Toast.LENGTH_SHORT).show();
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
        // Check if any field is empty
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

        // Validate coefficient
        try {
            float coefValue = Float.parseFloat(coef.getText().toString());
            if (coefValue <= 0) {
                return "Le coefficient doit être supérieur à 0!";
            }
        } catch (NumberFormatException e) {
            return "Veuillez entrer un coefficient valide!";
        }

        return null; // All validations passed
    }
}
