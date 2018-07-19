package com.sarthak.medopedia.pojos;

public class Schedule {

    private String category;
    private String label;

    public Schedule(String category, String label) {
        this.category = category;
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
