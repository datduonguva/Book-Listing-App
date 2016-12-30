package com.example.android.booklistingapp;

import java.util.ArrayList;

/**
 * Created by duong on 11/16/2016.
 */

public class Book {
    private String name;
    private ArrayList<String> authors;
    private String year;
    private String link;

    public Book(String name, ArrayList<String> authors, String year, String link){
        this.name = name;
        this.authors = authors;
        this.year = year;
        this.link = link;
    }
    public String getName(){ return name; }
    public String getAuthors(){
        String result  = "Authors: ";
        for(int i=0 ; i < authors.size(); i++) {
            result += authors.get(i);
            if(i == authors.size()-1) continue;
            result += ", ";
        }
        result += ".";
        return  result;
    }
    public String getYear(){return year;}
    public String getLink(){return link;}
}
