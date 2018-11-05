package aji.bayu.adnan.cataloguemovies.pagesearch;

import org.json.JSONObject;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class MovieItems {
    private String mJudulFilm;
    private String mDeskFilm;
    private String mTglRilis;
    private String mGambarPoster;
    private String mTotalRating;
    private String mRating;

    public String getMov_title() {
        return mJudulFilm;
    }

    public void setMov_title(String mJudulFilm) {
        this.mJudulFilm = mJudulFilm;
    }

    public String getMov_description() {
        return mDeskFilm;
    }

    public void setMov_description(String mDeskFilm) {
        this.mDeskFilm = mDeskFilm;
    }

    public String getMov_date() {
        return mTglRilis;
    }

    public void setMov_date(String mTglRilis) {
        this.mTglRilis = mTglRilis;
    }

    public String getMov_image() {
        return mGambarPoster;
    }

    public void setMov_image(String mGambarPoster) {
        this.mGambarPoster = mGambarPoster;
    }

    public String getMov_rate_count() {
        return mTotalRating;
    }

    public void setMov_rate_count(String mTotalRating) {
        this.mTotalRating = mTotalRating;
    }

    public String getMov_rate() {
        return mRating;
    }

    public void setMov_rate(String mRating) {
        this.mRating = mRating;
    }

    public MovieItems(JSONObject object){
        try {

            String title        = object.getString("title");
            String description  = object.getString("overview");
            String release_date = object.getString("release_date");
            String image        = object.getString("poster_path");
            String rate_count   = object.getString("vote_count");
            String rate         = object.getString("vote_average");
            this.mJudulFilm          = title;
            this.mDeskFilm    = description;
            this.mTglRilis           = release_date;
            this.mGambarPoster          = image;
            this.mTotalRating     = rate_count;
            this.mRating           = rate;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
