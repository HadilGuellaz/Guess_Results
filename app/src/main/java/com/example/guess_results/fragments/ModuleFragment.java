package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.guess_results.DBHelper;
import com.example.guess_results.Module;
import com.example.guess_results.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ModuleFragment extends Fragment {

    private View view;
    private MaterialCardView addModuleButton;
    private ListView moduleListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.module_screen, container, false);

        // Initialize UI Components
        init();

        // Navigate to DataFragment.java when button is clicked
        addModuleButton.setOnClickListener(v -> navigateToDataFragment());

        // Load Module Names from the Database
        loadModuleNames();

        return view;
    }

    private void init() {
        addModuleButton = view.findViewById(R.id.addModuleButton);
        moduleListView = view.findViewById(R.id.moduleListView);
    }

    private void loadModuleNames() {
        DBHelper dbHelper = new DBHelper(getActivity());
        List<Module> modules = dbHelper.getAllModules();
        List<String> moduleNames =new ArrayList<>();

        if (modules.isEmpty()) {

            Toast.makeText(getActivity(), "Aucun module trouvé!", Toast.LENGTH_SHORT).show();
        } else {
            for (Module module : modules) {
                moduleNames.add(module.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                moduleNames
            );
            moduleListView.setAdapter(adapter);
        }
    }

    private void navigateToDataFragment() {
        DataFragment dataFragment = new DataFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, dataFragment);
        transaction.addToBackStack("ModuleFragment"); // Named backstack entry
        transaction.commit();
    }
}
