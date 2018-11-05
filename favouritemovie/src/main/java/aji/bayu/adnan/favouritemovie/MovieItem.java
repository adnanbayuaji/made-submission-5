package aji.bayu.adnan.favouritemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.DEKRIPSI;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.GAMBAR;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.JUDUL;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.RILIS;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.getColumnInt;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.getColumnString;
import static android.provider.BaseColumns._ID;

/**
 * Created by Jihad044 on 10/09/2018.
 */

public class MovieItem implements Parcelable {
    private int id;
    private String titleMovie, overView, releaseDate, imagePoster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    private void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getOverView() {
        return overView;
    }

    private void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    private void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titleMovie);
        dest.writeString(this.overView);
        dest.writeString(this.releaseDate);
        dest.writeString(this.imagePoster);
    }

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.titleMovie = in.readString();
        this.overView = in.readString();
        this.releaseDate = in.readString();
        this.imagePoster = in.readString();
    }

    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.titleMovie = getColumnString(cursor, JUDUL);
        this.overView = getColumnString(cursor, DEKRIPSI);
        this.releaseDate = getColumnString(cursor, RILIS);
        this.imagePoster = getColumnString(cursor, GAMBAR);
    }
}
