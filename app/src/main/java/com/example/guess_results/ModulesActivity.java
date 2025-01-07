package com.example.guess_results;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

public class ModulesActivity extends AppCompatActivity {

    private ListView moduleListView;
    private Button addModuleButton;
    private ModuleAdapter moduleAdapter;
    private Collection<String> moduleList;
    private DBHelper dbHelper;

    private Module module;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


            moduleListView = findViewById(R.id.moduleListView);
            addModuleButton = findViewById(R.id.addModuleButton);

            dbHelper = new DBHelper(this);

            // Fetch modules from database
            moduleList = dbHelper.getAllModules();

            moduleAdapter = new ModuleAdapter(this, moduleList);
            moduleListView.setAdapter(moduleAdapter);

            addModuleButton.setOnClickListener(v -> {
                dbHelper.insertModule(module);
                moduleList.clear();
                moduleList.addAll(dbHelper.getAllModules());
                moduleAdapter.notifyDataSetChanged();
                Toast.makeText(this, "New Module Added", Toast.LENGTH_SHORT).show();
            });
        }
    }


