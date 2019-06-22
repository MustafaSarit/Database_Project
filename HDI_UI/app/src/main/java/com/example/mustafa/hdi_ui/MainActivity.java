package com.example.mustafa.hdi_ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        Queries queries = new Queries();
        List<Map<String, String>> myData = queries.mainPage();

        String[] fromWhere = {"Country", "HDI Rank", "HDI"};
        int[] toWhere = {R.id.Country, R.id.Rank, R.id.HDI};
        adapter = new SimpleAdapter(MainActivity.this, myData, R.layout.listtemp, fromWhere, toWhere);
        lv.setAdapter(adapter);
        lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                String country = o.toString().replaceFirst(".*Country=(.*?)", "");
                country = country.replaceFirst(",.*","");
                Intent intent = new Intent(MainActivity.this, SecondPage.class);
                intent.putExtra("country", country);
                startActivity(intent);
            }
        });
    }
}
