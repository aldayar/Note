package com.geeks.notes;

import android.widget.EditText;

import java.io.Serializable;

public class Note implements Serializable {

    private String image;
    private String title;
    private String desc;
    private String date;

    public Note(String image, String title, String desc, String date) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }




    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
