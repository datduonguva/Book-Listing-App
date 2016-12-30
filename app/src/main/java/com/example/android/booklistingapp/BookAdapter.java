package com.example.android.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by duong on 11/16/2016.
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, int resourceID, ArrayList<Book> object){
        super(context, resourceID, object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = (Book) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }
        if(book!= null){
            TextView bookName = (TextView) convertView.findViewById(R.id.book_name);
            TextView year = (TextView) convertView.findViewById(R.id.year);
            TextView author = (TextView) convertView.findViewById(R.id.authors);

            bookName.setText(book.getName());
            year.setText(book.getYear().toString());
            author.setText(book.getAuthors());
        }


        return convertView;
    }
}
