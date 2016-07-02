package com.example.android.booksfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
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
        authorsTextView.setText(currentBook.getAuthorsString());
        publishedDateTextView.setText(currentBook.getPublishedDate());

        return listItemView;
    }
}
