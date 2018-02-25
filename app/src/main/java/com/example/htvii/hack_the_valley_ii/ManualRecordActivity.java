package com.example.htvii.hack_the_valley_ii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.htvii.hack_the_valley_ii.models.Record;

import org.w3c.dom.Text;

import java.util.Date;

public class ManualRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_record);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText recordName = (EditText) findViewById(R.id.recordName);
                EditText expense = (EditText) findViewById(R.id.expense);
                Date date = getToday();
                if (!recordName.getText().toString().matches("") && !expense.getText().toString().matches("")) {
                    accountData.records.add(new Record(recordName.getText().toString(), -Math.abs(Float.parseFloat(expense.getText().toString())), date));
                    returnToHome();
                }
            }
        });
    }

    private void returnToHome(){
        navigate(new Intent(this, AccountSummaryActivity.class));
        finish();
    }


}
