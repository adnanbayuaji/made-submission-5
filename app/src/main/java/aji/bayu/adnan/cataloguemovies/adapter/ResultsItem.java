/*
 * Created by Jihad044.
 * Copyright (c) 2018. All rights reserved.
 * Last modified 11/11/17 3:14 PM.
 */

package aji.bayu.adnan.cataloguemovies.adapter;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.getColumnInt;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.getColumnString;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.JUDUL;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.DESKRIPSI;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.GAMBAR;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.RILIS;

public class ResultsItem {

    @SerializedName("overview")
    private String overview;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("id")
    private int id;

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ResultsItem() {
    }

    public ResultsItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, JUDUL);
        this.posterPath = getColumnString(cursor, GAMBAR);
        this.releaseDate = getColumnString(cursor, RILIS);
        this.overview = getColumnString(cursor, DESKRIPSI);
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "overview = '" + overview + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}