package com.unicamp.bruno.tvtracker.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unicamp.bruno.tvtracker.Database.DAO.ScreenPlayDAOImpl;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "tv_tracker.db";
    private static final int VERSAO_BANCO = 1;

    private static final String CREATE_TABLE_SCREENPLAY =
            "CREATE TABLE " + ScreenPlayDAOImpl.TABLE_NAME + " (\n" +
                    "     imdbID VARCHAR(50) PRIMARY KEY,\n" +
                    "     Title VARCHAR(255),\n" +
                    "     Year VARCHAR(30),\n" +
                    "     Type VARCHAR(30),\n" +
                    "     Poster VARCHAR(255)\n" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCREENPLAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
