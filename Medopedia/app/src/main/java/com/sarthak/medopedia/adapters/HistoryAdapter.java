package com.sarthak.medopedia.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.activities.SearchActivity;

import java.util.Arrays;
import java.util.Collections;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private String[] history;
    private Activity activity;

    public HistoryAdapter(String[] history, Activity activity) {
        this.history = history;
        Collections.reverse(Arrays.asList(this.history));
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try {
            String historyString = history[position];
            holder.textViewHistory.setText(historyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return history.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewHistory;

        ViewHolder(final View view) {
            super(view);
            textViewHistory = view.findViewById(R.id.text_view_history);

            textViewHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {
                        Intent intent = new Intent(activity, SearchActivity.class);
                        intent.putExtra("search", history[ViewHolder.this.getAdapterPosition()]);
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }
}