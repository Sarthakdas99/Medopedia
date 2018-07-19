package com.sarthak.medopedia.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sarthak.medopedia.R;
import com.sarthak.medopedia.adapters.AlarmAdapter;
import com.sarthak.medopedia.broadcast.AlarmReceiver;
import com.sarthak.medopedia.db.AlarmDBHelper;
import com.sarthak.medopedia.db.DBHelper;
import com.sarthak.medopedia.pojos.Alarm;
import com.sarthak.medopedia.pojos.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getActivity());
                ArrayList<Medicine> list = dbHelper.showAllMedicine();
                final String[] medicines = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Medicine medicine = list.get(i);
                    medicines[i] = medicine.getName();
                }
                new MaterialDialog.Builder(Objects.requireNonNull(getActivity()))
                        .title("Select the medicine for which you want to set the alarm")
                        .items(medicines)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);
                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                        setAlarm(medicines[which], selectedHour, selectedMinute);
                                        setData();
                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();
                            }
                        })
                        .show();
            }
        });

        setData();

        return rootView;
    }

    private void setAlarm(String name, int hour, int min) {
        AlarmDBHelper alarmDBHelper = new AlarmDBHelper(getActivity());
        alarmDBHelper.addAlarm(name, hour, min);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        Toast.makeText(getActivity(), "Reminder set", Toast.LENGTH_LONG).show();
    }

    private void setData() {
        AlarmDBHelper alarmDBHelper = new AlarmDBHelper(getActivity());
        ArrayList<Alarm> alarms = alarmDBHelper.getAlarms();
        AlarmAdapter alarmAdapter = new AlarmAdapter(alarms);
        recyclerView.setAdapter(alarmAdapter);
    }
}

