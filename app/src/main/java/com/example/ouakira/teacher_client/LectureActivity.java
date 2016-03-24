package com.example.ouakira.teacher_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LectureActivity extends AppCompatActivity {

    private List<Lecture> lectureList = new ArrayList<Lecture>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        initLecture();
        LectureAdapter adapter = new LectureAdapter(LectureActivity.this,
                R.layout.lecture_item, lectureList);
        ListView listView = (ListView) findViewById(R.id.lecture_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lecture lecture = lectureList.get(position);
                Intent intent = new Intent(LectureActivity.this, DetailsActivity.class);
                intent.putExtra("lectureName", lecture.getName());
                startActivity(intent);
            }
        });
    }

    private void initLecture(){
        Lecture math = new Lecture("math", new Date(2012,5,4,15,30));
        lectureList.add(math);
        Lecture english = new Lecture("English", new Date(2012,5,4,15,30));
        lectureList.add(english);
        Lecture sport = new Lecture("Sport", new Date(2012,5,4,15,30));
        lectureList.add(sport);
        Lecture art = new Lecture("Art", new Date(2012,5,4,15,30));
        lectureList.add(art);
    }
}
