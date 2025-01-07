package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.guess_results.R;

public class ModuleFragment extends Fragment {


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.module_screen, container, false);

        addModuleButton = view.findViewById(R.id.addModuleButton);
        addModuleButton.setOnClickListener(v -> navigateToDataFragment());

        return view;
    }


        private void navigateToDataFragment() {
        DataFragment dataFragment = new DataFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, dataFragment);
        transaction.addToBackStack(null); // Allows back navigation
        transaction.commit();
    }
}
