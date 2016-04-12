package com.example.ouakira.teacher_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ouakira on 16/3/24.
 */
public class KnowledgeAdapter extends ArrayAdapter<Knowledge> {
    private int resourceId;
    public KnowledgeAdapter(Context context, int textViewResourceId, List<Knowledge> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Knowledge knowledge = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView knowledgeName = (TextView) view.findViewById(R.id.knowledge_name);
        knowledgeName.setText(knowledge.getName());
        return view;
    }
}
