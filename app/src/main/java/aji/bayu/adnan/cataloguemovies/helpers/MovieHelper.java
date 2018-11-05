package aji.bayu.adnan.cataloguemovies.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.TABEL;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class MovieHelper {
    private static String DATABASE_TABLE = TABEL;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public MovieHelper(Context mContext){
        this.mContext = mContext;
    }

    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public long insertProvider(ContentValues contentValues){
        return mDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return mDatabase.query(DATABASE_TABLE,null,_ID+"=?",new String[]{id},null,null,null,null);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return mDatabase.update(DATABASE_TABLE, contentValues,_ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return mDatabase.delete(DATABASE_TABLE,_ID+"=?",new String[]{id});
    }

    public Cursor queryProvider(){
        return mDatabase.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }
}
