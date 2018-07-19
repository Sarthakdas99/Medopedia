package com.sarthak.medopedia.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.pojos.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private ArrayList<Alarm> alarms;

    public AlarmAdapter(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try {
            Alarm currentAlarm = alarms.get(position);
            holder.textViewName.setText(currentAlarm.getName());
            String hourText = String.valueOf(currentAlarm.getHour());
            if (currentAlarm.getHour() < 10) {
                hourText = "0" + hourText;
            }
            String minText = String.valueOf(currentAlarm.getMinute());
            if (currentAlarm.getMinute() < 10) {
                minText = "0" + minText;
            }
            holder.textViewTime.setText(String.format("%s:%s", hourText, minText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewTime;

        ViewHolder(final View view) {
            super(view);
            textViewName = view.findViewById(R.id.text_view_name);
            textViewTime = view.findViewById(R.id.text_view_time);
        }
    }
}