package com.example.ouakira.teacher_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LectureActivity extends AppCompatActivity {

    private List<Lecture> lectureList = new ArrayList<Lecture>();
    String lecturename;
    int lid;
    Date time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        Intent intent = getIntent();
        String teachername = intent.getStringExtra("teachername");
        int tid = intent.getIntExtra("tid", 0);

        new AsyncTask<String, Void, Void>(){
            @Override
            protected Void doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    URLConnection connection = url.openConnection();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is,"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                    br.close();
                    isr.close();
                    JSONObject root = new JSONObject(builder.toString());
                    JSONArray array = root.getJSONArray("result");
                    for (int i=0;i<array.length();i++) {
                        JSONObject lecture = array.getJSONObject(i);

                        System.out.print("name________"+lecture.getString("lecturename"));
                        Log.e("d",lecture.getString("lecturename"));

//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd Hh:mm:ss");
//                        Date date = sdf.parse(lecture.getString("time"));
                        Lecture newLecture = new Lecture(lecture.getString("lecturename"), new Date(2012,5,4,15,30));
                        lectureList.add(newLecture);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                LectureAdapter adapter = new LectureAdapter(LectureActivity.this,
                        R.layout.lecture_item, lectureList);
                ListView listView = (ListView) findViewById(R.id.lecture_list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
        }.execute("http://10.10.240.27:5000/lecture/" + tid);
    }
}
