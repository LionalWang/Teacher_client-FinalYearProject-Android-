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

    public Lecture(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
