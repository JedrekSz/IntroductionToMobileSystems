package com.example.introductiontomobilesystems;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

        holder.cbDone.setOnCheckedChangeListener(null);

        holder.cbDone.setChecked(h.done);

        if (h.done) {
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            h.done = isChecked;

            if (isChecked) {
                holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            storage.save(data);
        });

        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            h.done = isChecked;

            if (isChecked) {
                holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            List<Habit> fullList = storage.load();

            for (Habit original : fullList) {
                if (original.name.equals(h.name)) {
                    original.done = h.done;
                    break;
                }
            }

            storage.save(fullList);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName;
        CheckBox cbDone;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHabitName);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}