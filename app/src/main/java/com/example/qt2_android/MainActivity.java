package com.example.qt2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<info>infoArrayList;
    infoListViewAdapter proInfoListViewAdapter;
    ListView listViewinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoArrayList = new ArrayList<>();
        infoArrayList.add(new info(1,"hello", "dang lam bai"));
        infoArrayList.add(new info(2,"da xong","hay coi lai"));

        proInfoListViewAdapter = new infoListViewAdapter(infoArrayList);
        listViewinfo = (findViewById(R.id.listinfo));
        listViewinfo.setAdapter((ListAdapter) proInfoListViewAdapter);

        listViewinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                info item = (info) ((ListAdapter) proInfoListViewAdapter).getItem(i);
                Toast.makeText(MainActivity.this, item.title, Toast.LENGTH_LONG).show();
            }
        });
    }
}

    class info {
        int id;
        String title;
        String content;

         public info(int id, String title, String content){
             this.id = id;
            this.title = title;
            this.content = content;
    }
}

    class infoListViewAdapter extends BaseAdapter {
             ArrayList<info> infoArrayList;
         infoListViewAdapter(ArrayList<info> infoArrayList){
             this.infoArrayList = infoArrayList;
         }
        @Override
        public int getCount() {
            return infoArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return infoArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return infoArrayList.get(i).id;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewinfo;
            if (view == null){
                viewinfo = View.inflate(view.getContext(), R.layout.list_info, null);
            }else viewinfo = view;
            return viewinfo;
        }
}