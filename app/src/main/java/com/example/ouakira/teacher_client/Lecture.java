package com.example.ouakira.teacher_client;
import java.util.*;
import java.sql.*;
import java.util.Date;

/**
 * Created by ouakira on 16/3/24.
 */
public class Lecture {
    private String name;
    private Date date;
    private int id;

    public int getId() {
        return id;
    }

    public Lecture(String name, Date date, int id) {
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
