package com.example.qt2_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

public class NewNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater save = getMenuInflater();
        save.inflate(R.menu.save_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_new)
        {
            EditText txtTitle = findViewById(R.id.title);
            EditText txtContent = findViewById(R.id.content);
            String title = txtTitle.getText().toString();
            String content = txtContent.getText().toString();
            int id  = MainActivity.infoArrayList.size();
            Info note_new = new Info(id, title, content, new Date());
            MainActivity.proInfoListViewAdapter.add(note_new);
            MainActivity.proInfoListViewAdapter.notifyDataSetChanged();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}