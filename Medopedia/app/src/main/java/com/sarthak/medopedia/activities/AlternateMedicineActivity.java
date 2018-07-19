package com.sarthak.medopedia.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sarthak.medopedia.R;
import com.sarthak.medopedia.adapters.AlternativeMedicineAdapter;
import com.sarthak.medopedia.pojos.Medicine;
import com.sarthak.medopedia.utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlternateMedicineActivity extends AppCompatActivity {
    private String med_id;
    private AlternativeMedicineAdapter alternativeMedicineAdapter;
    private ArrayList<Medicine> list = new ArrayList<>();
    private int page = 1;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_medicine);

        med_id = getIntent().getStringExtra("medicine_id");

        queue = Volley.newRequestQueue(this);

        String medicineName = getIntent().getStringExtra("medicine_name");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Alternate medicines for " + medicineName);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(AlternateMedicineActivity.this));
        alternativeMedicineAdapter = new AlternativeMedicineAdapter(list);
        recyclerView.setAdapter(alternativeMedicineAdapter);

        getMedicines();
    }

    private void getMedicines() {
        String url = getString(R.string.host_url) + String.format(getString(R.string.alternate_medicine_url), med_id) + "page=" + String.valueOf(page) + "&size=10";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            ArrayList<Medicine> list;
                            try {
                                list = Parser.parseAlternateMedicines(response);
                                alternativeMedicineAdapter.setData(list);
                                Log.v("response", response);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Oops! Some error occured!", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Oops! Some error occured!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("Authorization", "Bearer " + getString(R.string.access_token));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        queue.add(getRequest);
    }
}
