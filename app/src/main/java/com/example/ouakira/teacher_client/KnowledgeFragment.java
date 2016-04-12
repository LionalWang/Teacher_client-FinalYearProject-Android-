package com.example.ouakira.teacher_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ouakira on 16/3/24.
 */
public class KnowledgeFragment extends Fragment{

    private List<Knowledge> knowledgeList = new ArrayList<Knowledge>();
    View view;
    int lid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_knowledge, container, false);

        return view;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        lid = intent.getIntExtra("lid", 0);

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

                        Log.e("d", knowledge.getString("text"));

                        Knowledge newknowledge = new Knowledge(knowledge.getString("text"), knowledge.getInt("yes_count"), knowledge.getInt("no_count"), knowledge.getInt("id"));
                        knowledgeList.add(newknowledge);
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
                KnowledgeAdapter knowledgeAdapter = new KnowledgeAdapter(getActivity(),
                        R.layout.knowledge_item, knowledgeList);
                ListView kListView = (ListView) view.findViewById(R.id.knowledge_list) ;
                kListView.setAdapter(knowledgeAdapter);
                knowledgeAdapter.notifyDataSetChanged();
                kListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Knowledge knowledge = knowledgeList.get(position);
                        Intent intent = new Intent(getActivity(), CollectActivity.class);
                        intent.putExtra("knowledgeText", knowledge.getName());
                        intent.putExtra("kid", knowledge.getId());
                        startActivity(intent);
                    }
                });
            }
        }.execute("http://10.10.240.27:5000/api/knowledge/" + lid);
    }
}
