package com.cms.keep.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes_Table")
public class Notes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String subTitle;
    private String body;
    private String date;
    private String priority ;
    public Notes(){

    }
    public Notes(String title, String subTitle, String body, String date, String priority) {
        this.title = title;
        this.subTitle = subTitle;
        this.body = body;
        this.date = date;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
