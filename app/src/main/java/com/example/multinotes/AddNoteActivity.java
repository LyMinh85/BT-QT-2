package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {
    TextView titleTextView;
    EditText titleInput, descriptionInput;
    MaterialButton saveBtn;
    Boolean isEditMode;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = findViewById(R.id.titleinput);
        descriptionInput = findViewById(R.id.descriptioninput);
        saveBtn = findViewById(R.id.savebtn);
        titleTextView = findViewById(R.id.titletextview);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        String id = getIntent().getStringExtra("id");

        // Nếu note tồn tại
        if (id != null){
            note = realm.where(Note.class).equalTo("id", id).findFirst();
            isEditMode = true;
        } else {
            note = new Note();
            note.setTitle("");
            note.setDescription("");
            isEditMode = false;
        }

        titleInput.setText(note.title);
        descriptionInput.setText(note.description);

        if (isEditMode){
            titleTextView.setText("Edit note");
            saveBtn.setText("Save changes");
        }

        saveBtn.setOnClickListener((view) -> saveNote(realm));
    }

    void saveNote(Realm realm) {
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        long createdTime = System.currentTimeMillis();

        if (title.isEmpty()) {
            titleInput.setError("Title is required!");
            return;
        }

        // Update note
        if (isEditMode) {
            realm.beginTransaction();

            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);
            realm.insertOrUpdate(note);

            realm.commitTransaction();
        } else { // Create new note
            realm.beginTransaction();

            String id = UUID.randomUUID().toString();
            Note note = realm.createObject(Note.class, id);
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);

            realm.commitTransaction();
        }

        Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
        finish();
    }
}