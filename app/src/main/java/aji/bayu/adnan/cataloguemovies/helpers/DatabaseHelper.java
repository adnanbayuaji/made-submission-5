package aji.bayu.adnan.cataloguemovies.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import aji.bayu.adnan.cataloguemovies.DatabaseContract;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABEL = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABEL,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.JUDUL,
            DatabaseContract.MovieColumns.DESKRIPSI,
            DatabaseContract.MovieColumns.RILIS,
            DatabaseContract.MovieColumns.GAMBAR
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABEL);
        onCreate(db);
    }

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
