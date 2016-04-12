package com.example.ouakira.teacher_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CollectActivity extends AppCompatActivity {

    int kid;
    String kname;
    TextView yes_count;
    TextView no_count;
    int yes;
    int no;
    Button collect_finish;
    Boolean is_send = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        Intent intent = getIntent();
        kid = intent.getIntExtra("kid", 0);
        kname = intent.getStringExtra("knowledgeText");

        TextView knameText = (TextView) findViewById(R.id.knowledgeName);
        knameText.setText(kname);

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
                        JSONObject knowledge = array.getJSONObject(i);

                        Log.e("d", knowledge.getString("yes_count"));

                        yes = knowledge.getInt("yes_count");
                        no = knowledge.getInt("no_count");
                        is_send = knowledge.getBoolean("is_send");
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
                yes_count = (TextView) findViewById(R.id.yes_count);
                yes_count.setText(""+yes);
                no_count = (TextView) findViewById(R.id.no_count);
                no_count.setText(""+no);
                collect_finish = (Button) findViewById(R.id.collect_finish);
                if (is_send) {
                    collect_finish.setText("Finish");
                } else {
                    collect_finish.setText("Send");
                }

                collect_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                    JSONObject object = array.getJSONObject(0);
                                    yes = object.getInt("yes_count");
                                    no = object.getInt("no_count");
                                    is_send = object.getBoolean("is_send");
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
                                if (is_send) {
                                    collect_finish.setText("Finish");
                                    yes_count.setText(""+yes);
                                    no_count.setText(""+no);
                                    Toast toast = Toast.makeText(getApplicationContext(), "The knowledge is sent", Toast.LENGTH_SHORT );
                                    toast.show();
                                } else {
                                    collect_finish.setText("Send");
                                    yes_count.setText(""+yes);
                                    no_count.setText(""+no);
                                    Toast toast = Toast.makeText(getApplicationContext(), "The message is collected", Toast.LENGTH_SHORT );
                                    toast.show();
                                }
                            }
                        }.execute("http://10.10.240.27:5000/api/knowledge/update/"+kid);
                    }
                });
            }
        }.execute("http://10.10.240.27:5000/api/kdetail/" + kid);
    }


    @Override
    protected void onDestroy() {
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
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("http://10.10.240.27:5000/api/knowledge/end/" + kid);
        super.onDestroy();
    }
}
