package com.example.android.booksfinder;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by omar on 7/2/16.
 */
public class BooksFetcher extends AsyncTask<String, Void, ArrayList<Book>> {


    private String LOG = BooksFetcher.class.getSimpleName();

    private BookAdapter bookAdapter;

    public BooksFetcher(BookAdapter adapter) {
        this.bookAdapter = adapter;
    }

    @Override
    protected ArrayList<Book> doInBackground(String... params) {

        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String jsonString;

        try {

            final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
            final String QUERY = "q";
            final String MAX = "maxResults";

            final Uri uri = Uri.parse(BASE_URL).buildUpon() .appendQueryParameter(QUERY, params[0]).appendQueryParameter(MAX, "40").build();

            URL url = new URL(uri.toString());

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            StringBuilder sb = new StringBuilder();

            if (is == null)
                return null;

            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            jsonString = sb.toString();

        } catch (IOException e) {
            Log.d(LOG, "Error: ", e);
            return null;

        } finally {
            if (conn != null)
            conn.disconnect();

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(LOG, "Error closing reader: ", e);
                }
            }
        }

        return parseJSON(jsonString);
    }

    @Override
    protected void onPostExecute(ArrayList<Book> books) {

        if (books != null && bookAdapter != null) {
            bookAdapter.clear();

            for (Book book: books) {
                bookAdapter.add(book);
            }
        }

        super.onPostExecute(books);
    }

    private ArrayList<Book> parseJSON(String jsonString) {

        ArrayList<Book> books = new ArrayList<>();

        try {

            JSONObject json = new JSONObject(jsonString);
            JSONArray items = json.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                String publishedDate = null;
                if (volumeInfo.has("publishedDate")) {
                    publishedDate = volumeInfo.getString("publishedDate");
                }

                ArrayList<String> authorsList = new ArrayList<>();
                if (volumeInfo.has("authors")) {
                    JSONArray authors = volumeInfo.getJSONArray("authors");
                    for (int j =0; j < authors.length(); j++) {
                        authorsList.add(authors.getString(j));
                    }
                }

                Book book = new Book(title, publishedDate, authorsList);

                books.add(book);
            }

        } catch (JSONException e) {
            Log.d(LOG, "Error Parsing JSON: ", e);
        }

        return books;
    }

}
