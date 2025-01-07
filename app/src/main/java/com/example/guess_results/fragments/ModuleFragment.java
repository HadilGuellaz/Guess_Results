package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.guess_results.DBHelper;
import com.example.guess_results.Module;
import com.example.guess_results.ModuleAdapter;
import com.example.guess_results.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ModuleFragment extends Fragment {

    private View view;
    private MaterialCardView addModuleButton;
    private ListView moduleListView;

    DBHelper dbHelper;

    List<Module> modules;
    private ImageView editModule;

    ModuleAdapter adapter;



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
        editModule = view.findViewById(R.id.editModuleButton);

        dbHelper = new DBHelper(getActivity());
        modules = dbHelper.getAllModules();

    }

    private void loadModuleNames() {
        if (modules.isEmpty()) {
            Toast.makeText(getActivity(), "Aucun module trouvé!", Toast.LENGTH_SHORT).show();
        } else {
            // Use the ModuleAdapter to display module names
            adapter = new ModuleAdapter(getActivity(), modules, this);
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

    public void deleteModule(int position) {
        Module moduleToDelete = modules.get(position);

        // Delete the module from the database
        DBHelper dbHelper = new DBHelper(getActivity());
        boolean isDeleted = dbHelper.deleteModule(moduleToDelete.getId());

        if (isDeleted) {
            Toast.makeText(getActivity(), "Module supprimé avec succès!", Toast.LENGTH_SHORT).show();
            // Remove the module from the list
            modules.remove(position);
            adapter.notifyDataSetChanged();  // Notify the adapter to update the list
        } else {
            Toast.makeText(getActivity(), "Erreur lors de la suppression du module!", Toast.LENGTH_SHORT).show();
        }
    }

}
