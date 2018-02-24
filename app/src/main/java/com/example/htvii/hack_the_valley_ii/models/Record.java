package com.example.htvii.hack_the_valley_ii.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Susan C on 2018-02-24.
 */

public class Record implements Serializable {
    public Record(String name, float transaction, Date datecreated){
        this.name = name;
        this.transaction = transaction;
        this.year = datecreated.getYear();
        this.month = datecreated.getMonth();
        this.day = datecreated.getDay();
    }
    public String name;
    public float transaction;
    public int year;
    public int month;
    public int day;
}
