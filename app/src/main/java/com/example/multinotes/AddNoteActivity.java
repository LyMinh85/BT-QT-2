package com.example.multinotes;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddNoteActivity extends AppCompatActivity {
    TextView titleTextView, titlePickTime;
    EditText titleInput, descriptionInput;
    MaterialButton saveBtn, btnPickTime;
    Boolean isEditMode;
    Note note;
    Date datePicker;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titlePickTime = findViewById(R.id.text_pick_time);
        titleInput = findViewById(R.id.titleinput);
        descriptionInput = findViewById(R.id.descriptioninput);
        saveBtn = findViewById(R.id.savebtn);
        titleTextView = findViewById(R.id.titletextview);
        btnPickTime = findViewById(R.id.button_pick_time);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        createNotificationChannel();

        String id = getIntent().getStringExtra("id");
        position = getIntent().getIntExtra("position", -1);

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
        btnPickTime.setOnClickListener((view -> pickTime(realm)));
    }

    void saveNote(Realm realm) {
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        long createdTime = System.currentTimeMillis();

        if (title.isEmpty()) {
            titleInput.setError("Title is required!");
            return;
        }

        if (position == -1) {
            position = (int) realm.where(Note.class).count();
        }

        realm.beginTransaction();
        // Update note
        if (isEditMode) {
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);
            note.setReminder(datePicker);
            realm.insertOrUpdate(note);
        } else { // Create new note
            String id = UUID.randomUUID().toString();
            note = realm.createObject(Note.class, id);
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);
            note.setReminder(datePicker);
        }
        realm.commitTransaction();

        // Đặt thời gian hiển thị thông báo
        if (note.getReminder() != null) {
            Intent intent = new Intent(AddNoteActivity.this, ReminderBroadcast.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("description", note.getDescription());
            intent.putExtra("position", position);
            Log.d("IDK", String.valueOf(position));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNoteActivity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(note.getReminder());
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        Toast.makeText(getApplicationContext(),"Note saved",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void pickTime(Realm realm) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
            titlePickTime.setText(selectedHour + ":" + selectedMinute);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);
            calendar.set(Calendar.SECOND, 0);
            datePicker = calendar.getTime();
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ReminderBroadcast.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}