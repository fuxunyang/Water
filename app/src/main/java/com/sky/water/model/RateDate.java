package com.sky.water.model;

import java.io.Serializable;

public class RateDate implements Serializable {

    private float soil10;
    private float soil20;
    private float soil40;
    private String date;
    private String type;

    public float getSoil10() {
        return soil10;
    }

    public void setSoil10(float soil10) {
        this.soil10 = soil10;
    }

    public float getSoil20() {
        return soil20;
    }

    public void setSoil20(float soil20) {
        this.soil20 = soil20;
    }

    public float getSoil40() {
        return soil40;
    }

    public void setSoil40(float soil40) {
        this.soil40 = soil40;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
