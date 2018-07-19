package com.sarthak.medopedia.pojos;

import java.util.ArrayList;

public class Medicine {

    private String name;
    private String form;
    private int standardUnits;
    private String packageForm;
    private String price;
    private String size;
    private String manufacturer;
    private String id;
    private String medicineId;
    private String searchScore;
    private ArrayList<Constituent> constituents;
    private Schedule schedule;

    public Medicine(String name) {
        this.name = name;
    }

    public Medicine(String name, String form, int standardUnits, String packageForm, String price, String size, String manufacturer, String id, String medicineId, String searchScore, ArrayList<Constituent> constituents, Schedule schedule) {
        this.name = name;
        this.form = form;
        this.standardUnits = standardUnits;
        this.packageForm = packageForm;
        this.price = price;
        this.size = size;
        this.manufacturer = manufacturer;
        this.id = id;
        this.medicineId = medicineId;
        this.searchScore = searchScore;
        this.constituents = constituents;
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public int getStandardUnits() {
        return standardUnits;
    }

    public String getPackageForm() {
        return packageForm;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getId() {
        return id;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public String getSearchScore() {
        return searchScore;
    }

    public ArrayList<Constituent> getConstituents() {
        return constituents;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
