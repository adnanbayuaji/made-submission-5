package aji.bayu.adnan.cataloguemovies.pagefavorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.DESKRIPSI;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.GAMBAR;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.JUDUL;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.RILIS;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.getColumnInt;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.getColumnString;
import static android.provider.BaseColumns._ID;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class MovieItem implements Parcelable {
    private int id;
    //    private double popularity;
    private String mTitleMovie;
    private String mOverView;
    private String mReleaseDate;
    private String mImagePoster;

    public MovieItem(JSONObject jsonObject){

        try {
            String mTitleMovie = jsonObject.getString("title");
            String mOverView = jsonObject.getString("overview");
            String mReleaseDate = jsonObject.getString("release_date");
//            double popularity = jsonObject.getDouble("popularity");
            String mImagePoster = jsonObject.getString("poster_path");
            String imageBackdrop = jsonObject.getString("backdrop_path");
            String finalImg = "http://image.tmdb.org/t/p/w185/"+mImagePoster;
//            String finalBackdrop = "http://image.tmdb.org/t/p/w780/"+imageBackdrop;
            String finalDate = parseDate(mReleaseDate);
            setTitleMovie(mTitleMovie);
            setOverView(mOverView);
            setReleaseDate(finalDate);
//            setPopularity(popularity);
            setImagePoster(finalImg);
//            setImageBackdrop(finalBackdrop);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.mTitleMovie = getColumnString(cursor, JUDUL);
        this.mOverView = getColumnString(cursor, DESKRIPSI);
        this.mReleaseDate = getColumnString(cursor, RILIS);
        this.mImagePoster = getColumnString(cursor, GAMBAR);
    }

    private String parseDate(String tgl){
        String inputDate = "yyyy-MM-dd";
        String outputDate = "EEEE, MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputDate, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputDate, Locale.US);
        Date date;
        String string = null;

        try {
            date = inputFormat.parse(tgl);
            string = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return mTitleMovie;
    }

    private void setTitleMovie(String mTitleMovie) {
        this.mTitleMovie = mTitleMovie;
    }

    public String getOverView() {
        return mOverView;
    }

    private void setOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    private void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

//    public double getPopularity() {
//        return popularity;
//    }

//    private void setPopularity(double popularity) {
//        this.popularity = popularity;
//    }

    public String getImagePoster() {
        return mImagePoster;
    }

    private void setImagePoster(String mImagePoster) {
        this.mImagePoster = mImagePoster;
    }

//    public String getImageBackdrop() {
//        return imageBackdrop;
//    }

//    private void setImageBackdrop(String imageBackdrop) {
//        this.imageBackdrop = imageBackdrop;
//    }



    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    private MovieItem(Parcel in) {
        this.id = in.readInt();
        this.mTitleMovie = in.readString();
        this.mOverView = in.readString();
        this.mReleaseDate = in.readString();
        this.mImagePoster = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mTitleMovie);
        dest.writeString(this.mOverView);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mImagePoster);
    }
}
