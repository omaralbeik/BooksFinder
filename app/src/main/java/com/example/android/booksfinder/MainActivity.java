package com.example.android.booksfinder;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "HttpExample";
    EditText searchEditText;
    ListView listView;
    ArrayList<Book> books = new ArrayList<>();
    String query;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        adapter = new BookAdapter(getApplicationContext(), books);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setCursorVisible(true);
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean success = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // get textView's text with leading and trailing whitespace trimmed
                    String query = v.getText().toString().trim();

                    if (query.isEmpty()) {
                        showToast("Please enter a search term and try again!");
                        return false;

                    } else {
                        if (isConnected()) {
                            hideKeyboard(searchEditText);
                            BooksFetcher fetcher = new BooksFetcher(adapter);
                            fetcher.execute(query);
                            searchEditText.setCursorVisible(false);
                            success = true;
                        } else {
                            showToast("Not Connected, Please connect and try again!");
                        }
                    }

                }
                return success;
            }
        });

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // save query from searchEditText after encoding
//                query = Uri.encode(searchEditText.getText().toString());
//
//                hideKeyboard(view);
//                searchEditText.setCursorVisible(false);
//            }
//        });
    }




    // Helper Methods

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        searchEditText.clearFocus();
    }

    public boolean isConnected() {
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


}

