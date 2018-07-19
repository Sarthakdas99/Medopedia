package com.sarthak.medopedia.pojos;

import java.util.ArrayList;

public class Alarm {

    private String name;

    public String getName() {
        return name;
    }

    public int getMinute() {
        return minute;
    }

    public int getHour() {
        return hour;
    }

    private int minute;
    private int hour;

    public Alarm(String name, int hour, int minute) {
        this.name = name;
        this.minute = minute;
        this.hour = hour;
    }
}
