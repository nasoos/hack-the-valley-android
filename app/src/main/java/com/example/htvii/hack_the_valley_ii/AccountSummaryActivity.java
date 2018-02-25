package com.example.htvii.hack_the_valley_ii;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.htvii.hack_the_valley_ii.models.Record;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AccountSummaryActivity extends BaseActivity {
    private static final int IMAGE_GALLERY_RESULT = 20;
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
        if (accountData != null && accountData.records != null) {
            TextView balance = (TextView) findViewById(R.id.balance);
            balance.setText("$" + String.format("%.2f", getBalance()));
            if (getBalance() < 0){
                balance.setText("- $" + String.format("%.2f", -getBalance()));
            }
            TextView accountName = (TextView) findViewById(R.id.accountname);
            accountName.setText(getAccountName());
            List<Record> recordlist = getRecord(getToday());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getRecordStr(recordlist));

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.recordlinearlayout);
            for (int i = 0; i < adapter.getCount(); i++) {
                View record = adapter.getView(i, null, null);
                View div = (View) getLayoutInflater().inflate(R.layout.divtemplate, null);
                linearLayout.addView(record);
                linearLayout.addView(div);

                record.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView text = (TextView) view;
                        navigateToRecord((String) text.getText());
                    }
                });
            }
            ImageButton manualButton = findViewById(R.id.manualrecord);
            manualButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                onManualButtonClick();
                }
            });

            ImageButton cameraButton = findViewById(R.id.camerarecord);
            cameraButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onCameraButtonClick();
                }
            });

            ImageButton galleryButton = findViewById(R.id.galleryrecord);
            galleryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String pictureDirectoryPath = pictureDirectory.getPath();

                    Uri data = Uri.parse(pictureDirectoryPath);
                    photoPickerIntent.setDataAndType(data, "image/*");
                    startActivityForResult(photoPickerIntent, IMAGE_GALLERY_RESULT);
                }
            });
        }
    }

    //TODO: do something with the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_GALLERY_RESULT){
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    String request = to64(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText( this , "Unable to open Image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void navigateToRecord(String record){
        Intent recordIntent = new Intent(this, RecordActivity.class);
        recordIntent.putExtra("recordID", record);
        navigate(recordIntent);
    }

    public float getBalance(){
        float balance = 0;
        if (accountData.records != null) {
            for (int i = 0; i < accountData.records.size(); i++) {
                balance += accountData.records.get(i).transaction;
            }
        }
        return balance;
    }

    public String getAccountName(){
        return accountData.accountName;
    }

    public List<Record> getRecord (Date date){
        List <Record> records = new ArrayList<Record>();
        if (accountData.records != null) {
            for (Record record : accountData.records) {
                if (record.year == date.getYear() && record.month == date.getMonth() && record.day == date.getDay()) {
                    records.add(record);
                }
            }
        }
        return records;
    }

    public List<String> getRecordStr (List<Record> records){
        List<String> recordStrs = new ArrayList<>();
        if (records != null) {
            for (Record record : records) {
                recordStrs.add(record.name + " -$" + String.format("%.2f", -record.transaction));
            }
        }
        return recordStrs;
    }

    private void onManualButtonClick(){
        navigate(new Intent(this, ManualRecordActivity.class));
    }

    private void onCameraButtonClick(){
        navigate(new Intent(this, MakePhotoActivity.class));
    }

    public String to64 (Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
