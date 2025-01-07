package com.example.guess_results;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    private EditText name, coef, eval, evalPerc, exam, examPerc;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);

        init();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validationMessage = validateFields();

                if (validationMessage == null) {

                    String nameValue = name.getText().toString();
                    float coefValue = Float.parseFloat(coef.getText().toString());
                    float evalValue = Float.parseFloat(eval.getText().toString());
                    float evalPercValue = Float.parseFloat(evalPerc.getText().toString());
                    float examValue = Float.parseFloat(exam.getText().toString());
                    float examPercValue = Float.parseFloat(examPerc.getText().toString());


                    DBHelper dbHelper = new DBHelper(DataActivity.this);
                    long id = dbHelper.insertModule(nameValue, coefValue, evalValue, evalPercValue, examValue, examPercValue);


                    if (id != -1) {

                        Toast.makeText(DataActivity.this, "Données enregistrées avec succès!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DataActivity.this, ResultsActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(DataActivity.this, "Erreur lors de l'enregistrement des données!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(DataActivity.this, validationMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {

        name = findViewById(R.id.name);
        coef = findViewById(R.id.coef);
        eval = findViewById(R.id.eval);
        evalPerc = findViewById(R.id.eval_perc);
        exam = findViewById(R.id.exam);
        examPerc = findViewById(R.id.exam_perc);


        saveButton = findViewById(R.id.save);
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
