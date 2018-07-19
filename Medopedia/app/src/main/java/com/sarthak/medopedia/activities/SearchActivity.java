package com.sarthak.medopedia.activities;

import android.content.SharedPreferences;
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
import com.sarthak.medopedia.adapters.SearchAdapter;
import com.sarthak.medopedia.pojos.Medicine;
import com.sarthak.medopedia.utils.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    SearchAdapter searchAdapter;
    ArrayList<Medicine> list = new ArrayList<>();
    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = getIntent().getStringExtra("search");

        addRecordToHistory(search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(search);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchAdapter = new SearchAdapter(list, this);
        recyclerView.setAdapter(searchAdapter);

        getMedicines();
    }

    private void getMedicines() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.host_url) + getString(R.string.search_medicine_url) + search;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.v("response", response);
                            try {
                                list = Parser.parseMedicines(response);
                                searchAdapter.setData(list);
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

    private void addRecordToHistory(String str) {
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int size = sharedPreferences.getInt("history_array_size", 0);
        ArrayList<String> strings = new ArrayList<>();
        int pos = -1;
        for (int i = 0; i < size; i++) {
            strings.add(sharedPreferences.getString("history_array_" + i, null));
            if (strings.get(i).equals(str)) {
                pos = i;
            }
        }

        if (pos == -1) {
            editor.putString("history_array_" + size, str);
            editor.putInt("history_array_size", size + 1);
            editor.apply();
        } else {
            strings.remove(pos);
            for (int i = 0; i < strings.size(); i++) {
                editor.putString("history_array_" + i, strings.get(i));
                editor.putInt("history_array_size", size);
            }
            editor.putString("history_array_" + (size - 1), str);
            editor.apply();
        }
    }


}
