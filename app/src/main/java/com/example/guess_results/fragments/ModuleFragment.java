package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.guess_results.R;

public class ModuleFragment extends Fragment {

    private View view;
    private Button addModuleButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.module_screen, container, false);

        // Initialize the button
        addModuleButton = view.findViewById(R.id.addModuleButton);
        addModuleButton.setOnClickListener(v -> navigateToDataFragment());

        return view;
    }

    private void navigateToDataFragment() {
        DataFragment dataFragment = new DataFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, dataFragment);
        transaction.addToBackStack("ModuleFragment"); // Add to back stack with a tag
        transaction.commit();
    }
}
