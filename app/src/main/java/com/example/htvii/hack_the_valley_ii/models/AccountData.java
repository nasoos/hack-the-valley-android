package com.example.htvii.hack_the_valley_ii.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susan C on 2018-02-24.
 */

public class AccountData {
    public AccountData(){
        records = new ArrayList<Record>();
    }
    public List<Record> records;
    public String accountName;
}
