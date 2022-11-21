package com.example.qt2_android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Info {
    int id;
    String title;
    String content;
    Date datetime;

    public Info(int id, String title, String content, Date datetime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.datetime = new Date();
    }

    public String getDateFormat() {
        String pattern = "E MMM d, HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(datetime);
    }

}
