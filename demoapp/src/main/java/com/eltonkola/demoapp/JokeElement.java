package com.eltonkola.demoapp;

import com.eltonkola.prefdatalibrary.BaseType;

import java.util.Date;

/**
 * Created by elton on 5/7/15.
 */

public class JokeElement extends BaseType{

    private String title;
    private String description;
    private Date creation_date;

    public JokeElement(){
        creation_date =  new Date(System.currentTimeMillis());
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
