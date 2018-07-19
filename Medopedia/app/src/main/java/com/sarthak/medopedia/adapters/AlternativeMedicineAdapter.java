package com.sarthak.medopedia.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.activities.AlternateMedicineActivity;
import com.sarthak.medopedia.db.DBHelper;
import com.sarthak.medopedia.pojos.Medicine;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class AlternativeMedicineAdapter extends RecyclerView.Adapter<AlternativeMedicineAdapter.ViewHolder> {
    private ArrayList<Medicine> medicines;

    public AlternativeMedicineAdapter(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alternative_medicine_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try {
            final Medicine currentMedicine = medicines.get(position);

            holder.textViewName.setText(currentMedicine.getName());
            holder.textViewform.setText("Form: " + currentMedicine.getForm());
            holder.textViewStandardUnits.setText("Standard units: " + String.valueOf(currentMedicine.getStandardUnits()));
            holder.textViewPackagingForm.setText("Package form: " + currentMedicine.getPackageForm());
            holder.textViewPrice.setText("Price: " + currentMedicine.getPrice());
            holder.textViewSize.setText("Size: " + currentMedicine.getSize());
            holder.textViewManufacturer.setText("Manufacturer: " + currentMedicine.getManufacturer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setData(ArrayList<Medicine> list) {
        medicines = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewform, textViewStandardUnits, textViewPackagingForm, textViewPrice, textViewSize, textViewManufacturer;
        private ExpandableLayout expandableLayout;

        ViewHolder(final View view) {
            super(view);
            expandableLayout = view.findViewById(R.id.expandable_layout);
            textViewName = view.findViewById(R.id.text_view_medicine_name);
            textViewform = view.findViewById(R.id.text_view_medicine_form);
            textViewStandardUnits = view.findViewById(R.id.text_view_medicine_standard_units);
            textViewPackagingForm = view.findViewById(R.id.text_view_medicine_package_form);
            textViewPrice = view.findViewById(R.id.text_view_medicine_price);
            textViewSize = view.findViewById(R.id.text_view_medicine_size);
            textViewManufacturer = view.findViewById(R.id.text_view_medicine_manufacturer);

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout != null) {
                        if (expandableLayout.isExpanded()) {
                            expandableLayout.collapse();
                        } else {
                            expandableLayout.expand();
                        }
                    }
                }
            });
            // itemView.setOnClickListener(this);
        }
    }
}