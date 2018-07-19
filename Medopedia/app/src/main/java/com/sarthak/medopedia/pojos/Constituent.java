package com.sarthak.medopedia.pojos;

public class Constituent {

    private String name;
    private String strength;

    public Constituent(String name, String strength) {
        this.name = name;
        this.strength = strength;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }
}
