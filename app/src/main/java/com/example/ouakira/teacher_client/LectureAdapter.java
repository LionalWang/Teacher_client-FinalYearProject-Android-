package com.example.ouakira.teacher_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ouakira on 16/3/24.
 */
public class LectureAdapter extends ArrayAdapter<Lecture>{
    private int resourceId;
    public LectureAdapter (Context context, int textViewResourceId, List<Lecture> objects){
        super (context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Lecture lecture = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView lectureName = (TextView) view.findViewById(R.id.lecture_name);
        TextView lectureTime = (TextView) view.findViewById(R.id.lecture_time);
        lectureName.setText(lecture.getName());
        lectureTime.setText(new SimpleDateFormat().format(lecture.getDate()));
        return view;
    }
}
