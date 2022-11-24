package com.example.qt2_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public static ArrayList<Info> infoArrayList;
    public static InfoListViewAdapter proInfoListViewAdapter;
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


        listViewinfo.setOnItemClickListener((adapterView, view, i, l) -> {
            Info item = (Info) ((ListAdapter) proInfoListViewAdapter).getItem(i);
            Toast.makeText(MainActivity.this, item.title, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_note) {
            Intent opennew = new Intent(getApplicationContext(), NewNote.class);
            startActivity(opennew);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater add = getMenuInflater();
        add.inflate(R.menu.add_new, menu);
        return true;
    }
}
