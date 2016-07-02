package com.example.android.booksfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by omar on 7/2/16.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.titleTextView);
        TextView authorsTextView = (TextView) listItemView.findViewById(R.id.authorsTextView);
        TextView publishedDateTextView = (TextView) listItemView.findViewById(R.id.publishedDateTextView);

        titleTextView.setText(currentBook.getTitle());

        if (currentBook.getAuthors() == null || currentBook.getAuthors().size() == 0) {
            authorsTextView.setVisibility(View.GONE);
        } else {
            if (currentBook.getAuthors().size() == 1) {
                authorsTextView.setText("Author: " + currentBook.getAuthorsString());
            } else {
                authorsTextView.setText("Authors: " + currentBook.getAuthorsString());
            }
        }

        if (currentBook.getPublishedDate() == null || currentBook.getPublishedDate().length() == 0) {
            publishedDateTextView.setVisibility(View.GONE);
        } else {
            publishedDateTextView.setText("Published at: " + currentBook.getPublishedDate());
        }

        return listItemView;
    }
}
