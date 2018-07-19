package com.sarthak.medopedia.utils;

import com.sarthak.medopedia.pojos.Constituent;
import com.sarthak.medopedia.pojos.Medicine;
import com.sarthak.medopedia.pojos.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parser {

    public static ArrayList<Medicine> parseMedicines(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        try {
            ArrayList<Medicine> medicines = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                medicines.add(new Medicine(
                        jsonObject.getString("name"),
                        jsonObject.getString("form"),
                        jsonObject.getInt("standardUnits"),
                        jsonObject.getString("packageForm"),
                        jsonObject.getString("price"),
                        jsonObject.getString("size"),
                        jsonObject.getString("manufacturer"),
                        jsonObject.getString("id"),
                        jsonObject.getString("medicine_id"),
                        jsonObject.getString("search_score"),
                        parseConstituents(jsonObject.getJSONArray("constituents")),
                        new Schedule(jsonObject.getJSONObject("schedule").getString("category"), jsonObject.getJSONObject("schedule").getString("label"))
                ));
            }
            return medicines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Medicine> parseAlternateMedicines(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        try {
            ArrayList<Medicine> medicines = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                medicines.add(new Medicine(
                        jsonObject.getString("name"),
                        jsonObject.getString("form"),
                        jsonObject.getInt("standardUnits"),
                        jsonObject.getString("packageForm"),
                        jsonObject.getString("price"),
                        jsonObject.getString("size"),
                        jsonObject.getString("manufacturer"),
                        null,
                        jsonObject.getString("medicine_id"),
                        null,
                        parseConstituents(jsonObject.getJSONArray("constituents")),
                        new Schedule(jsonObject.getJSONObject("schedule").getString("category"), jsonObject.getJSONObject("schedule").getString("label"))
                ));
            }
            return medicines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Constituent> parseConstituents(JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }
        try {
            ArrayList<Constituent> constituents = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                constituents.add(new Constituent(
                        jsonObject.getString("name"),
                        jsonObject.getString("strength")
                ));
            }
            return constituents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
