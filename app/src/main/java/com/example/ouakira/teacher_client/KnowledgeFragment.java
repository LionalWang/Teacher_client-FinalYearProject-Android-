package com.example.ouakira.teacher_client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ouakira on 16/3/24.
 */
public class KnowledgeFragment extends Fragment{

    private List<Knowledge> knowledgeList = new ArrayList<Knowledge>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_knowledge, container, false);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initKnowledge();
        KnowledgeAdapter knowledgeAdapter = new KnowledgeAdapter(getActivity(),
                R.layout.knowledge_item, knowledgeList);
        ListView kListView = (ListView) view.findViewById(R.id.knowledge_list) ;
        kListView.setAdapter(knowledgeAdapter);
    }

    public void initKnowledge(){
        Knowledge k1 = new Knowledge("How to study Android");
        knowledgeList.add(k1);
        Knowledge k2 = new Knowledge("How to study Python");
        knowledgeList.add(k2);
        Knowledge k3 = new Knowledge("How to study Database");
        knowledgeList.add(k3);
    }
}
