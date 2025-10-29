package com.example.introductiontomobilesystems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.VH> {
    List<Habit> data;
    HabitsStorage storage;

    public HabitAdapter(Context ctx, List<Habit> data) {
        this.data = data;
        this.storage = new HabitsStorage(ctx);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Habit h = data.get(position);
        holder.tvName.setText(h.name);
        holder.tvFreq.setText(h.freq);
        holder.sw.setChecked(h.active);
        holder.sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            h.active = isChecked;
            storage.save(data);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvFreq;
        Switch sw;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHabitName);
            tvFreq = itemView.findViewById(R.id.tvFreq);
            sw = itemView.findViewById(R.id.switchActive);
        }
    }
}