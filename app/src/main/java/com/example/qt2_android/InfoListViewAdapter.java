package com.example.qt2_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoListViewAdapter extends BaseAdapter {
    ArrayList<Info> infoArrayList;

    InfoListViewAdapter(ArrayList<Info> infoArrayList){
        this.infoArrayList = infoArrayList;
    }
    @Override
    public int getCount() {
        return infoArrayList.size();
    }

    @Override
    public Info getItem(int i) {
        return infoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return infoArrayList.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Info item = getItem(i);
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_info, viewGroup, false);
        }
        TextView title = view.findViewById(R.id.title);
        title.setText(item.title);
        TextView content = view.findViewById(R.id.content);
        content.setText(item.content);
        TextView date = view.findViewById(R.id.date);
        date.setText(item.getDateFormat());
        return view;
    }
}
