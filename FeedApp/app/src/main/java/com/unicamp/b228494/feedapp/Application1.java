package com.unicamp.b228494.feedapp;

import java.util.Date;

public class Application1 {
    private String name;
    private String artist;
    private String releaseDate;

    public Application1() {}

    public Application1(String name, String artist, String releaseDate) {
        this.name = name;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate.substring(8, 10) + '/' +
                releaseDate.substring(5, 7) + '/' +
                releaseDate.substring(0, 4);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n" +
                "Artist: " + this.getArtist() + "\n" +
                "Release Date: " + this.getReleaseDate();
    }
}
