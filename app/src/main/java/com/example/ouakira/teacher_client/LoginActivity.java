package com.example.ouakira.teacher_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    boolean isUser = false;
    int tid;
    String teachername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText teachernameEditText = (EditText) findViewById(R.id.teachername);
                EditText passwordEditText = (EditText) findViewById(R.id.password);

                teachername = teachernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

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
                            int status = object.getInt("status");
                            tid = object.getInt("tid");
                            if (status == 1) {
                                isUser = true;
                            } else {
                               isUser = false;
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
                        if (isUser) {
                            Intent intent = new Intent(LoginActivity.this, LectureActivity.class);
                            intent.putExtra("tid", tid);
                            intent.putExtra("teachername", teachername);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "The user doesn't exist or password is wrong", Toast.LENGTH_SHORT );
                            toast.show();
                        }
                    }
                }.execute("http://10.10.240.27:5000/login/teacher/"+teachername+"/"+password);
            }
        });
    }
}
