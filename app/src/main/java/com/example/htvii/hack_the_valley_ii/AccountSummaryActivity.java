package com.example.htvii.hack_the_valley_ii;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.htvii.hack_the_valley_ii.models.Record;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AccountSummaryActivity extends BaseActivity {
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_summary);
        setView();
    }

    @Override
    public void setView(){
        super.setView();
        TextView balance = (TextView) findViewById(R.id.balance);
        balance.setText("$" + String.format("%.2f", getBalance()));
        TextView accountName = (TextView) findViewById(R.id.accountname);
        accountName.setText(getAccountName());
        List<Record> recordlist = getRecord(new Date(2018,1,24));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getRecordStr(recordlist));
        /*ListView listView = (ListView) findViewById(R.id.recordListView);
        listView.setAdapter(adapter);*/

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.recordlinearlayout);
        for (int i = 0; i < adapter.getCount(); i++) {
            View record = adapter.getView(i, null, null);
            View div = (View) getLayoutInflater().inflate(R.layout.divtemplate,null);
            linearLayout.addView(record);
            linearLayout.addView(div);

            record.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    TextView text = (TextView) view;
                    navigate((String) text.getText());
                }
            });
        }
    }

    //TODO: implement logic
    private void navigate(String record){

    }

    //TODO: get balance from database
    public float getBalance(){
        return accountData.balance;
    }

    //TODO: get account name from database
    public String getAccountName(){
        return accountData.accountName;
    }

    //TODO: get records from database
    public List<Record> getRecord (Date date){
        List <Record> records = new ArrayList<Record>();
        for (Record record : accountData.records) {
            if (record.datecreated.getYear() == date.getYear() && record.datecreated.getMonth() == date.getMonth() && record.datecreated.getDay() == date.getDay()){
                records.add(record);
            }
        }
        return records;
    }

    public List<String> getRecordStr (List<Record> records){
        List<String> recordStrs = new ArrayList<>();
        for (Record record: records){
            recordStrs.add(record.name);
        }
        return recordStrs;
    }
}
