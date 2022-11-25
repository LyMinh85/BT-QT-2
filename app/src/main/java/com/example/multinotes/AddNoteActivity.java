package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multinotes.R;
import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {
    TextView titleTextView;
    EditText titleInput, descriptionInput;
    MaterialButton saveBtn;
    String title, description, dodID;
    Boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = findViewById(R.id.titleinput);
        descriptionInput = findViewById(R.id.descriptioninput);
        saveBtn = findViewById(R.id.savebtn);
        titleTextView = findViewById(R.id.titletextview);

        title = getIntent().getStringExtra("tittle");
        description = getIntent().getStringExtra("description");
        dodID = getIntent().getStringExtra("dodID");

        if (dodID!=null && !dodID.isEmpty()){
            isEditMode = true;
        }

        titleInput.setText(title);
        descriptionInput.setText(description);

        if (isEditMode){
            titleTextView.setText("Edit note");
            saveBtn.setText("Save changes");
        }

        saveBtn.setOnClickListener((v) -> saveNote());
    }

    void saveNote() {
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        long createdTime = System.currentTimeMillis();

        if (title == null || title.isEmpty()){
            titleInput.setError("Title is required!");
            return;
        }

        realm.beginTransaction();
        Note note = realm.createObject(Note.class);
        note.setTitle(title);
        note.setDescription(description);
        note.setCreatedTime(createdTime);

        if (isEditMode){
            realm.insertOrUpdate(note);
            realm.commitTransaction();
        }
        else{
            realm.commitTransaction();
        }

        Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
        finish();
    }
}