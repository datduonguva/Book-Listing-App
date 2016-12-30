package com.example.android.booklistingapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by duong on 11/16/2016.
 */

public class BookUtils {

    private String urlString;
    public BookUtils(String a){
        urlString = a;
    }

    public URL urlCreate() throws MalformedURLException{
        URL myUrl = null;
        try{
            myUrl = new URL(urlString);
        } catch (MalformedURLException e){

        }
        return myUrl;
    }

    public String httpRequest() throws IOException {
        URL url = urlCreate();
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonrespon = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonrespon = readFromStream(inputStream);
            }
        } catch (IOException e){
            throw e;
        } finally {
            if(urlConnection != null) urlConnection.disconnect();
            if(inputStream != null) inputStream.close();
        }
        return  jsonrespon;
    }

    public String readFromStream(InputStream inputStream){
        StringBuilder output = new StringBuilder();
        try{
            if(inputStream!= null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line!= null){
                    output.append(line);
                    line = reader.readLine();
                }
            }
        } catch (IOException e){
        } finally {

        }
        return  output.toString();

    }



    public  ArrayList<Book> readFromJason() throws JSONException, IOException {
        String jsonRespond = httpRequest();

        JSONObject jsonObject = new JSONObject(jsonRespond);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        ArrayList<Book> result = new ArrayList<Book>();
        for(int i=0; i< itemsArray.length(); i++){
            JSONObject itemI=  itemsArray.getJSONObject(i);
            JSONObject volumeInfor = itemI.getJSONObject("volumeInfo");
            String title = volumeInfor.getString("title");
            ArrayList<String> authors = new ArrayList<String>();
            if(volumeInfor.has("authors")) {
                JSONArray authorJsonArray = volumeInfor.getJSONArray("authors");
                for(int j =0; j< authorJsonArray.length(); j++){
                    authors.add(authorJsonArray.get(j).toString());
                }
            }
            String link = "";
            String date = "";
            if(volumeInfor.has("publishedDate")) {
                 date = volumeInfor.getString("publishedDate");
            }
            if(itemI.has("accessinfor")) {
                JSONObject accessInfor = itemI.getJSONObject("accessInfo");
                link = accessInfor.getString("webReaderLink");
            }
            result.add(new Book(title, authors, date, link));
        }
        return  result;

    }
}
