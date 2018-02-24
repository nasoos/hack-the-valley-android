package com.example.htvii.hack_the_valley_ii;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.htvii.hack_the_valley_ii.models.AccountData;
import com.example.htvii.hack_the_valley_ii.models.Record;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public AccountData accountData;

    private Intent intent;
    private Menu nav_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAccountData();
    }

    //TODO: Load account data from database instead of the temp data
    private void loadAccountData() {
        accountData = new AccountData();
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            accountData.accountName = (String) bundle.getSerializable("accountName");
            int count = (int) bundle.getSerializable("count");
            accountData.records = new ArrayList<Record>();
            for (int i = 0; i < count; i++) {
                accountData.records.add((Record) bundle.getSerializable("record" + Integer.toString(i)));
            }
        }
        else {
            accountData = new AccountData();
            accountData.accountName = "Youth Account";
            accountData.records = new ArrayList<Record>();
            accountData.records.add(new Record("Chicken", -9.54f, getToday()));
            accountData.records.add(new Record("Cabbage", -19.20f, getToday()));
            accountData.records.add(new Record("Eggs", -3.54f, getToday()));
            accountData.records.add(new Record("Carrots", -8.54f, new Date(2018, 1, 22)));
            accountData.records.add(new Record("Pizza", -87.54f, new Date(2018, 1, 24)));
        }
    }

    public void navigate(Intent intent){
        Bundle bundle = new Bundle();
        bundle.putSerializable("accountName", accountData.accountName);
        bundle.putSerializable("count", accountData.records.size());
        for (int i = 0; i < accountData.records.size(); i ++){
            bundle.putSerializable("record"+Integer.toString(i), accountData.records.get(i));
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       /* ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_menu = navigationView.getMenu();
        nav_menu.add(0, Menu.FIRST, Menu.FIRST, "Camera").setIcon(R.drawable.ic_menu_camera);
        nav_menu.add(1, Menu.FIRST + 1, Menu.FIRST, "Gallery").setIcon(R.drawable.ic_menu_gallery);
        nav_menu.add(2, Menu.FIRST + 2, Menu.FIRST, "Settings").setIcon(R.drawable.ic_menu_manage);
        nav_menu.add(3, Menu.FIRST + 3, Menu.FIRST, "Send").setIcon(R.drawable.ic_menu_send);
        nav_menu.add(4, Menu.FIRST + 4, Menu.FIRST, "Sign Out").setIcon(R.drawable.ic_menu_share);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == nav_menu.getItem(0).getItemId()) {

        } else if (id == nav_menu.getItem(1).getItemId()) {

        } else if (id == nav_menu.getItem(2).getItemId()) {

        } else if (id == nav_menu.getItem(3).getItemId()) {

        }else if (id == nav_menu.getItem(4).getItemId()) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Date getToday(){
        return Calendar.getInstance().getTime();
    }
}