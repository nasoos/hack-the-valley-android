package com.example.htvii.hack_the_valley_ii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.htvii.hack_the_valley_ii.models.Record;

import java.util.Date;

public class RecordActivity extends BaseActivity {
    private Record curRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        curRecord = getRecord(getIntent().getStringExtra("recordID"));
        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText recordName = (EditText) findViewById(R.id.recordName);
                EditText expense = (EditText) findViewById(R.id.expense);
                Date date = getToday();
                curRecord.name = recordName.getText().toString();
                curRecord.transaction = Float.parseFloat(expense.getText().toString());
                curRecord.year = date.getYear();
                curRecord.month = date.getMonth();
                curRecord.day = date.getDay();

                returnToHome();
            }
        });
    }

    private Record getRecord(String recordID){
        for (int i = 0; i < accountData.records.size(); i++){
            if (accountData.records.get(i).name == recordID){
                return accountData.records.get(i);
            }
        }
        return null;
    }

    private void returnToHome(){
        navigate(new Intent(this, AccountSummaryActivity.class));
        finish();
    }
}
