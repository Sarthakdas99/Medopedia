package com.sarthak.medopedia.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarthak.medopedia.R;
import com.sarthak.medopedia.pojos.Medicine;

import java.util.ArrayList;

public class MyMedicineAdapter extends RecyclerView.Adapter<MyMedicineAdapter.ViewHolder> {
    private ArrayList<Medicine> mlist;
    private Activity activity;

    public MyMedicineAdapter(ArrayList<Medicine> list, Activity activity) {
        this.mlist = list;
        //Collections.reverse(Arrays.asList(this.history));
        this.activity = activity;
    }

    public void setData(ArrayList<Medicine> list) {
        mlist = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_medicine_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try {
            String MedicineName = mlist.get(position).getName();
            holder.textViewMyMedicine.setText(MedicineName);
            holder.textViewNo.setText(String.valueOf(position + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMyMedicine;
        private TextView textViewNo;

        ViewHolder(final View view) {
            super(view);
            textViewMyMedicine = view.findViewById(R.id.text_view_my_medicine);
            textViewNo = view.findViewById(R.id.text_view_no);

           /* textViewMyMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {
                        Intent intent = new Intent(activity, SearchActivity.class);
                        intent.putExtra("search", history[ViewHolder.this.getAdapterPosition()]);
                        activity.startActivity(intent);
                    }
                }
            });*/
        }
    }
}