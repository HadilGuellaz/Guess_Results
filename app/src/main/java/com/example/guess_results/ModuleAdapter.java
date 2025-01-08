package com.example.guess_results;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.guess_results.fragments.EditDataFragment;
import com.example.guess_results.fragments.ModuleFragment;

import java.util.List;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private final Context context;
    private final List<Module> modules;
    private ModuleFragment moduleFragment;

    public ModuleAdapter(Context context, List<Module> modules, ModuleFragment moduleFragment) {
        super(context, 0, modules);
        this.context = context;
        this.modules = modules;
        this.moduleFragment = moduleFragment;  // Initialize the fragment reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_module, parent, false);
        }

        Module module = getItem(position);
        TextView nameTextView = convertView.findViewById(R.id.moduleNameTextView);
        if (module != null) {
            nameTextView.setText(module.getName());
        }

        // Handle the edit button if needed (same as before)
        ImageButton editButton = convertView.findViewById(R.id.editModuleButton);
        editButton.setOnClickListener(v -> navigateToEditDataFragment(position));

        ImageButton deleteButton = convertView.findViewById(R.id.deleteModuleButton);
        deleteButton.setOnClickListener(v -> {
            // Directly calling the deleteModule method in ModuleFragment
            if (moduleFragment != null) {
                moduleFragment.deleteModule(position);
            }
        });

        return convertView;
    }

    private void navigateToEditDataFragment(int position) {
        Module moduleToEdit = modules.get(position);
        EditDataFragment editDataFragment = new EditDataFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("modulePosition", position);
        bundle.putParcelable("module", moduleToEdit);
        editDataFragment.setArguments(bundle);

        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, editDataFragment);
        transaction.addToBackStack("ModuleFragment");
        transaction.commit();
    }
}





