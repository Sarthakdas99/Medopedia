package com.sarthak.medopedia.fragments;

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
import com.sarthak.medopedia.adapters.MyMedicineAdapter;
import com.sarthak.medopedia.db.DBHelper;
import com.sarthak.medopedia.pojos.Medicine;

import java.util.ArrayList;

public class MyMedicinesFragment extends Fragment /*implements View.OnClickListener*/ {

    MyMedicineAdapter myMedicineAdapter;
    //FloatingActionButton floatingActionButton;
    DBHelper dbHelper;
    ArrayList<Medicine> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_medicine, null);

        dbHelper = new DBHelper(getActivity());

        //floatingActionButton=rootView.findViewById(R.id.fab_add);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        myMedicineAdapter = new MyMedicineAdapter(list, getActivity());
        recyclerView.setAdapter(myMedicineAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        showMyMedicine();

        return rootView;
    }

    /*@Override
    public void onClick(View v) {
        new MaterialDialog.Builder(getActivity())
                .title("Add medicine")
                .content("Enter your medicine")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Medicine name", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String myMedicineName=input.toString();
                        dbHelper.addMedicine(myMedicineName);
                        showMyMedicine();


                    }
                }).show();
    }*/

    private void showMyMedicine() {
        dbHelper = new DBHelper(getActivity());
        list = dbHelper.showAllMedicine();
        myMedicineAdapter.setData(list);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "Yo", Toast.LENGTH_SHORT).show();
        showMyMedicine();
    }
}

