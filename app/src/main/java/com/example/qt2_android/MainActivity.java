package com.example.qt2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Info> infoArrayList;
    InfoListViewAdapter proInfoListViewAdapter;
    ListView listViewinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoArrayList = new ArrayList<>();
        infoArrayList.add(new Info(1, "hello", "dang lam bai", new Date()));
        infoArrayList.add(new Info(2, "da xong", "hay coi lai", new Date()));

        proInfoListViewAdapter = new InfoListViewAdapter(infoArrayList);
        listViewinfo = (findViewById(R.id.listinfo));
        listViewinfo.setAdapter((ListAdapter) proInfoListViewAdapter);


        listViewinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Info item = (Info) ((ListAdapter) proInfoListViewAdapter).getItem(i);
                Toast.makeText(MainActivity.this, item.title, Toast.LENGTH_LONG).show();
            }
        });
    }
}
