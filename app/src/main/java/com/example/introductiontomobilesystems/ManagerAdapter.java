package com.example.introductiontomobilesystems;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.SwitchCompat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerVH> {

    private List<Habit> data;
    private HabitsStorage storage;
    private Context context;
    private final String[] frequencies = {"Daily", "Weekly", "Bi-Weekly", "Monthly"};

    public ManagerAdapter(Context ctx, List<Habit> data) {
        this.context = ctx;
        this.data = data;
        this.storage = new HabitsStorage(ctx);
    }

    @NonNull
    @Override
    public ManagerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit_manager, parent, false);
        return new ManagerVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerVH holder, int position) {
        Habit h = data.get(position);

        holder.etName.removeTextChangedListener(holder.textWatcher);
        holder.swActive.setOnCheckedChangeListener(null);
        holder.swNotifs.setOnCheckedChangeListener(null);
        holder.spinnerFreq.setOnItemSelectedListener(null);

        holder.etName.setText(h.name);
        holder.swActive.setChecked(h.active);
        holder.swNotifs.setChecked(h.notifs);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerFreq.setAdapter(adapter);

        int spinnerPosition = Arrays.asList(frequencies).indexOf(h.freq);
        if(spinnerPosition >= 0) {
            holder.spinnerFreq.setSelection(spinnerPosition);
        }

        holder.textWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                h.name = s.toString();
                storage.save(data);
            }
        };
        holder.etName.addTextChangedListener(holder.textWatcher);

        holder.swActive.setOnCheckedChangeListener((v, isChecked) -> {
            h.active = isChecked;
            storage.save(data);
        });

        holder.swNotifs.setOnCheckedChangeListener((v, isChecked) -> {
            h.notifs = isChecked;
            storage.save(data);
        });

        holder.spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = frequencies[pos];
                if (!selected.equals(h.freq)) {
                    h.freq = selected;
                    storage.save(data);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class ManagerVH extends RecyclerView.ViewHolder {
        EditText etName;
        Spinner spinnerFreq;
        SwitchCompat swActive, swNotifs;
        TextWatcher textWatcher;

        public ManagerVH(@NonNull View itemView) {
            super(itemView);
            etName = itemView.findViewById(R.id.etEditName);
            spinnerFreq = itemView.findViewById(R.id.spinnerEditFreq);
            swActive = itemView.findViewById(R.id.switchActive);
            swNotifs = itemView.findViewById(R.id.switchNotifs);
        }
    }
}