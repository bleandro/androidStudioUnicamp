package com.unicamp.bruno.tvtracker.Database.DAO;

import com.unicamp.bruno.tvtracker.app.ScreenPlay;

import java.util.ArrayList;

public interface ScreenPlayDAO {
    public static final String TABLE_NAME = "ScreenPlay";

    public ArrayList<ScreenPlay> getAll();
    public ScreenPlay getByIMDbId(String IMDBId);
    public long saveScreenPlay(ScreenPlay screenPlay);
    public boolean deleteScreenPlay(ScreenPlay screenPlay);
}
