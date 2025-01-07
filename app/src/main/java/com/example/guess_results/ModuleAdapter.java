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

import java.util.List;

public class ModuleAdapter extends ArrayAdapter<Module> {

    private final Context context;
    private final List<Module> modules;

    public ModuleAdapter(Context context, List<Module> modules) {
        super(context, 0, modules);
        this.context = context;
        this.modules = modules;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_module, parent, false);
        }

        // Get the module object at this position
        Module module = getItem(position);

        // Find the TextView where module name will be displayed
        TextView nameTextView = convertView.findViewById(R.id.moduleNameTextView);

        // Set the name of the module in the TextView
        if (module != null) {
            nameTextView.setText(module.getName());
        }

        // Handle the edit button if needed (same as before)
        ImageButton editButton = convertView.findViewById(R.id.editModuleButton);
        editButton.setOnClickListener(v -> {
            navigateToEditDataFragment(position);
        });

        return convertView;
    }

    private void navigateToEditDataFragment(int position) {
        // Navigate to edit fragment with the position
        Module moduleToEdit = modules.get(position);

        EditDataFragment editDataFragment = new EditDataFragment();


        Bundle bundle = new Bundle();
        bundle.putInt("modulePosition", position);
        bundle.putParcelable("module", moduleToEdit);
        editDataFragment.setArguments(bundle);

        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, editDataFragment);
        transaction.addToBackStack("ModuleFragment"); // Optionally add to back stack
        transaction.commit();
    }
}


