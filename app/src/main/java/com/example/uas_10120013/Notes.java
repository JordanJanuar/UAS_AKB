package com.example.uas_10120013;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

// NIM : 10120013
// NAMA : Jordan Januar Ilhami G
// KELAS : IF 1
public class Notes implements Serializable {
    @Exclude
    private String key;
    private String title;
    private String note;
    private String date;
    private String category;

    public Notes(){}

    public Notes(String title, String note, String date, String category) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
