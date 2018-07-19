package com.sarthak.medopedia.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Medicine> medicines;
    private Context context;
    private DBHelper dbHelper = null;

    public SearchAdapter(ArrayList<Medicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_layout, parent, false);
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

            if (position % 2 == 0) {
                holder.itemView.setAlpha(0.9f);
            }
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

    private void initDBHelperIfNeeded() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName, textViewform, textViewStandardUnits, textViewPackagingForm, textViewPrice, textViewSize, textViewManufacturer;
        private ExpandableLayout expandableLayout;
        private Button buttonAddMedicine, buttonAlternative;


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
            buttonAddMedicine = view.findViewById(R.id.button_add_med);
            buttonAlternative = view.findViewById(R.id.button_alternative);

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
            buttonAlternative.setOnClickListener(this);
            buttonAddMedicine.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Medicine medicine = medicines.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.button_add_med:
                    String medicine_name = medicine.getName();
                    initDBHelperIfNeeded();
                    dbHelper.addMedicine(medicine_name);
                    break;
                case R.id.button_alternative:
                    Intent intent = new Intent(context, AlternateMedicineActivity.class);
                    intent.putExtra("medicine_id", medicine.getMedicineId());
                    intent.putExtra("medicine_name", medicine.getName());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}