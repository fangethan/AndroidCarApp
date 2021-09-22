package com.example.carappweek5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    CarDatabase db;
    CarDAO dbOperations;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = CarDatabase.getDatabase(getContext());
        dbOperations = db.carDAO();
        return true;
    }

    // Uri: maps to the table name
    // projection: list of columns that should be included in each row
    // selection: is a string that represents the where clause
    // Selection Arguments: an array of strings represents values that should be embedded in the selection statement
    // sort order: a string that indicates whether to sort the data in ascending or descending order.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor = dbOperations.getAllCarsCursor();
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleteCount;
        deleteCount = db.getOpenHelper().getWritableDatabase().delete("cars",selection,selectionArgs);

//        deleteCount = dbOperations.deleteYearCar("year");

        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}