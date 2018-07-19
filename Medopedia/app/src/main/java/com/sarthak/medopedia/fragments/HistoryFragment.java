package com.sarthak.medopedia.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.adapters.HistoryAdapter;

import java.util.Objects;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, null);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        showHistory();

        return rootView;
    }

    private void showHistory() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt("history_array_size", 0);
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = sharedPreferences.getString("history_array_" + i, null);
        }
        recyclerView.setAdapter(new HistoryAdapter(array, getActivity()));
    }
}

