package com.example.ouakira.teacher_client;

import android.widget.Button;

/**
 * Created by ouakira on 16/3/24.
 */
public class Knowledge {
    private String name;
    private int yes_count;
    private int no_count;
    private int id;

    public int getId() {
        return id;
    }

    public int getYes_count() {
        return yes_count;
    }

    public int getNo_count() {
        return no_count;
    }

    public Knowledge(String name, int yes_count, int no_count, int id){
        this.name = name;
        this.yes_count = yes_count;
        this.no_count = no_count;
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
