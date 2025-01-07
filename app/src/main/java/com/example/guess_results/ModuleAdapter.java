package com.example.guess_results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

public class ModuleAdapter extends ArrayAdapter<Module> {

    public ModuleAdapter(Context context, Collection<String> modules) {
        super(context, 0, modules.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item_module, parent, false);
        }

        Module module = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.moduleNameTextView);
        ImageButton editButton = convertView.findViewById(R.id.editModuleButton);

        nameTextView.setText(module.getName());
        editButton.setOnClickListener(v ->
                Toast.makeText(getContext(), "Edit " + module.getName(), Toast.LENGTH_SHORT).show()
        );

        return convertView;
    }
}
