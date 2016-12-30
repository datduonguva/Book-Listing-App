package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {
    private BookAdapter adapter = null;
    private String requestString = "";
    private int counter = 1;
    LoaderManager.LoaderCallbacks mCallBack = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit_text);
        ImageView imageView = (ImageView) findViewById(R.id.go_button);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter = null;
                counter += 1;
                String getEditText = editText.getText().toString();
                requestString = "https://www.googleapis.com/books/v1/volumes?q=" + modifyKey(getEditText) + "&maxResults=10";

                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
                if (isConnected) {
                    ListView listView = (ListView) findViewById(R.id.list_view);
                    adapter = new BookAdapter(getApplicationContext(), R.layout.list_item, new ArrayList<Book>());
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(counter, null, mCallBack);
                    listView.setAdapter(adapter);
                    TextView emptyTextView = (TextView) findViewById(R.id.empty_text);
                    listView.setEmptyView(emptyTextView);

                } else {
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.GONE);
                    TextView emptyTextView = (TextView) findViewById(R.id.empty_text);
                    emptyTextView.setText("There is no internet connection");

                }
            }
        });
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, requestString);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        if (adapter != null) adapter.clear();
        if (data != null && !data.isEmpty()) adapter.addAll(data);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_text);

        if (data == null) emptyTextView.setText("There is no result returned");
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

    public String modifyKey(String rawString) {
        String[] spitString = rawString.split(" ");
        String result = "";
        for (int i = 0; i < spitString.length; i++) {
            result += spitString[i];
            if (i < spitString.length) result += "+";
        }
        return result;
    }
}
