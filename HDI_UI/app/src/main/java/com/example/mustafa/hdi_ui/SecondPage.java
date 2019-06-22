package com.example.mustafa.hdi_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SecondPage extends AppCompatActivity {
    ListView lvc;
    SimpleAdapter adapter;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        Intent intent = getIntent();
        String country = intent.getStringExtra("country");
        tv = findViewById(R.id.countryn);
        tv.setText(country);

        lvc = findViewById(R.id.lvc);
        Queries queries = new Queries();
        List<Map<String, String>> myData = queries.seconPage(country);

        String[] fromWhere = {"StatTypeName", "Value"};
        int[] toWhere = {R.id.stat, R.id.value};
        adapter = new SimpleAdapter(SecondPage.this, myData, R.layout.cltemp, fromWhere, toWhere);
        lvc.setAdapter(adapter);
    }
}
