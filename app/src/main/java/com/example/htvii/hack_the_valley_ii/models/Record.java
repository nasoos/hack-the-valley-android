package com.example.htvii.hack_the_valley_ii.models;

import java.util.Date;

/**
 * Created by Susan C on 2018-02-24.
 */

public class Record {
    public Record(String name, float transaction, Date datecreated){
        this.name = name;
        this.transaction = transaction;
        this.datecreated = datecreated;
    }
    public String name;
    public float transaction;
    public Date datecreated;
}
