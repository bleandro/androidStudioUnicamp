package com.unicamp.bruno.tvtracker.Database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unicamp.bruno.tvtracker.Database.DatabaseHelper;
import com.unicamp.bruno.tvtracker.app.ScreenPlay;

import java.util.ArrayList;

public class ScreenPlayDAOImpl implements ScreenPlayDAO {
    private DatabaseHelper databaseHelper;

    public ScreenPlayDAOImpl(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public ArrayList<ScreenPlay> getAll() {
        SQLiteDatabase db =databaseHelper. getWritableDatabase();
        ArrayList<ScreenPlay> screenPlays = new ArrayList<>();

        try {
            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    ScreenPlay screenPlay = new ScreenPlay();

                    screenPlay.setImdbID(cursor.getString(cursor.getColumnIndex("imdbID")));
                    screenPlay.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                    screenPlay.setYear(cursor.getString(cursor.getColumnIndex("Year")));
                    screenPlay.setType(cursor.getString(cursor.getColumnIndex("Type")));
                    screenPlay.setPoster(cursor.getString(cursor.getColumnIndex("Poster")));

                    screenPlays.add(screenPlay);
                } while (cursor.moveToNext());
            }
        }
        finally {
            db.close();
        }

        return screenPlays;
    }

    @Override
    public ScreenPlay getByIMDbId(String IMDBId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            Cursor cursor = db.query(TABLE_NAME, null, "IMDbId=?", new String[] { IMDBId }, null, null, null);

            if (cursor.moveToFirst()) {
                ScreenPlay screenPlay = new ScreenPlay();

                screenPlay.setImdbID(cursor.getString(cursor.getColumnIndex("imdbID")));
                screenPlay.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                screenPlay.setYear(cursor.getString(cursor.getColumnIndex("Year")));
                screenPlay.setType(cursor.getString(cursor.getColumnIndex("Type")));
                screenPlay.setPoster(cursor.getString(cursor.getColumnIndex("Poster")));

                return screenPlay;
            }
        }
        finally {
            db.close();
        }

        return null;
    }

    @Override
    public long saveScreenPlay(ScreenPlay screenPlay) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put("imdbID", screenPlay.getImdbID());
            contentValues.put("Title", screenPlay.getTitle());
            contentValues.put("Year", screenPlay.getYear());
            contentValues.put("Type", screenPlay.getType());
            contentValues.put("Poster", screenPlay.getPoster());

            return db.insert(TABLE_NAME, null, contentValues);
        }
        catch (Exception e){
            return -1;
        }
        finally {
            db.close();
        }
    }

    @Override
    public boolean deleteScreenPlay(ScreenPlay screenPlay) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            db.delete(TABLE_NAME, "imdbID=?", new String[] { screenPlay.getImdbID() });

            return true;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            db.close();
        }
    }
}
