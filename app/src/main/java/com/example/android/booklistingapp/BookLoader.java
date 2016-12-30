package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by duong on 11/16/2016.
 */

public class BookLoader extends AsyncTaskLoader {
    private String mUrl;
    public BookLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        BookUtils bookUtils = new BookUtils(mUrl);
        ArrayList<Book>  result = null;
        try {
            result = bookUtils.readFromJason();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
