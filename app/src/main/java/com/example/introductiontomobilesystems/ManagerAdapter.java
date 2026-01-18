package com.example.introductiontomobilesystems;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.VH> {

    List<Habit> data;
    HabitsStorage storage;
    Context context;
    // Callback to tell Activity to update the "KenMore" image
    Runnable onDeleteCallback;

    private final String[] frequencies = {"Daily", "Weekly", "Bi-Weekly", "Monthly"};

    public ManagerAdapter(Context ctx, List<Habit> data, Runnable onDeleteCallback) {
        this.data = data;
        this.context = ctx;
        this.storage = new HabitsStorage(ctx);
        this.onDeleteCallback = onDeleteCallback;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit_manager, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Habit h = data.get(position);

        // --- NAME EDITING ---
        holder.etName.setText(h.name);
        holder.etName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                h.name = s.toString();
                storage.save(data);
            }
        });

        // --- SPINNER SETUP ---
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(h.freq);
        holder.spinner.setSelection(spinnerPosition);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                h.freq = frequencies[position];
                storage.save(data);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // --- SWITCHES ---
        holder.switchActive.setOnCheckedChangeListener(null); // Prevent listener firing during recycle
        holder.switchActive.setChecked(h.active);
        holder.switchActive.setOnCheckedChangeListener((v, isChecked) -> {
            h.active = isChecked;
            storage.save(data);
        });

        holder.switchNotifs.setOnCheckedChangeListener(null);
        holder.switchNotifs.setChecked(h.notifs);
        holder.switchNotifs.setOnCheckedChangeListener((v, isChecked) -> {
            h.notifs = isChecked;
            storage.save(data);
        });

        // --- DELETE BUTTON LOGIC ---
        // Reset button state whenever the view is rebound
        holder.btnDelete.setText("delete");

        holder.btnDelete.setOnClickListener(v -> {
            if (holder.btnDelete.getText().toString().equals("delete")) {
                // First Click: Ask Confirmation
                holder.btnDelete.setText("sure?");

                // Auto-reset back to "delete" after 3 seconds
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Check if view is still valid/attached to window
                    if (holder.btnDelete != null) {
                        holder.btnDelete.setText("delete");
                    }
                }, 3000);
            } else {
                // Second Click: Execute Delete
                int actualPos = holder.getAdapterPosition();
                if (actualPos != RecyclerView.NO_POSITION) {
                    data.remove(actualPos);
                    notifyItemRemoved(actualPos);
                    notifyItemRangeChanged(actualPos, data.size());
                    storage.save(data);

                    // Trigger Activity to check image visibility
                    if (onDeleteCallback != null) {
                        onDeleteCallback.run();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        EditText etName;
        Spinner spinner;
        SwitchCompat switchActive, switchNotifs;
        Button btnDelete;

        public VH(@NonNull View itemView) {
            super(itemView);
            etName = itemView.findViewById(R.id.etEditName);
            spinner = itemView.findViewById(R.id.spinnerEditFreq);
            switchActive = itemView.findViewById(R.id.switchActive);
            switchNotifs = itemView.findViewById(R.id.switchNotifs);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}