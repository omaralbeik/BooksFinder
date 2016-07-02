package com.example.android.booksfinder;

import java.util.ArrayList;

/**
 *
 * Created by omar on 7/2/16.
 */
public class Book {
    private String title;
    private ArrayList<String> authors;
    private String publishedDate;

    /**
     * Create a new book
     * @param title book title
     * @param authors list of authors
     * @param publishedDate year the book was published
     */
    public Book(String title, String publishedDate, ArrayList<String> authors) {

        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        String text = "";

        for (int i = 0; i < authors.size(); i++) {
            text += authors.get(i);
            if (i < authors.size() - 1)
                text += ", ";
        }
        return text;
    }

}
