package com.example.guess_results.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.guess_results.R;

public class HomeFragment extends Fragment {


    View view;
    CardView carrerCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_home_screen, container, false);

        carrerCard = view.findViewById(R.id.career_card);

        carrerCard.setOnClickListener(v -> navigateToModuleFragment());

        return view;
    }

    private void navigateToModuleFragment() {
        ModuleFragment moduleFragment = new ModuleFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, moduleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
