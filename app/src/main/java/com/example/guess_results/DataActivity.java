package com.example.guess_results;

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

        // Initialiser les vues avec les ID des éléments XML
        init();

        // Ajouter un écouteur sur le bouton pour vérifier les champs
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Valider les champs
                String validationMessage = validateFields();

                if (validationMessage == null) {
                    // Si tous les champs sont valides

                    // Récupérer les données des champs
                    String nameValue = name.getText().toString();
                    float coefValue = Float.parseFloat(coef.getText().toString());
                    float evalValue = Float.parseFloat(eval.getText().toString());
                    float evalPercValue = Float.parseFloat(evalPerc.getText().toString());
                    float examValue = Float.parseFloat(exam.getText().toString());
                    float examPercValue = Float.parseFloat(examPerc.getText().toString());

                    // Créer une instance de DBHelper pour insérer les données dans la base de données
                    DBHelper dbHelper = new DBHelper(DataActivity.this);
                    long id = dbHelper.insertModule(nameValue, coefValue, evalValue, evalPercValue, examValue, examPercValue);

                    // Vérifier si l'insertion a réussi
                    if (id != -1) {
                        // Afficher un message de succès
                        Toast.makeText(DataActivity.this, "Données enregistrées avec succès!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Afficher un message d'erreur
                        Toast.makeText(DataActivity.this, "Erreur lors de l'enregistrement des données!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Si la validation échoue, afficher le message d'erreur
                    Toast.makeText(DataActivity.this, validationMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init() {
        // Initialisation des EditText avec leurs IDs
        name = findViewById(R.id.name);
        coef = findViewById(R.id.coef);
        eval = findViewById(R.id.eval);
        evalPerc = findViewById(R.id.eval_perc);
        exam = findViewById(R.id.exam);
        examPerc = findViewById(R.id.exam_perc);

        // Initialisation du bouton
        saveButton = findViewById(R.id.save);
    }

    // Fonction de validation des champs avec des messages d'erreur détaillés
    private String validateFields() {
        // Vérifier que tous les champs sont remplis
        if (name.getText().toString().isEmpty() ||
                coef.getText().toString().isEmpty() ||
                eval.getText().toString().isEmpty() ||
                evalPerc.getText().toString().isEmpty() ||
                exam.getText().toString().isEmpty() ||
                examPerc.getText().toString().isEmpty()) {
            return "Tous les champs doivent être remplis!";
        }

        // Vérifier que les notes sont entre 0 et 20
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

        // Vérifier que les pourcentages totalisent 100%
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

        // Vérifier que le coefficient est supérieur à 0
        try {
            float coefValue = Float.parseFloat(coef.getText().toString());
            if (coefValue <= 0) {
                return "Le coefficient doit être supérieur à 0!";
            }
        } catch (NumberFormatException e) {
            return "Veuillez entrer un coefficient valide!";
        }

        // Si toutes les validations passent
        return null;
    }
}
