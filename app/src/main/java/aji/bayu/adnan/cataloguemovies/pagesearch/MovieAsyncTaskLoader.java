package aji.bayu.adnan.cataloguemovies.pagesearch;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.android.volley.Header;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import aji.bayu.adnan.cataloguemovies.BuildConfig;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;

    private String mJudulMovie;

    public MovieAsyncTaskLoader(final Context context, String judulMovie) {
        super(context);

        onContentChanged();
        this.mJudulMovie = judulMovie;
    }

    private void onReleaseResources(ArrayList<MovieItems> data) {
    }

    public void deliverResult(ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItems_ = new ArrayList<>();
        String url = BuildConfig.MOVIE_URL_SEARCH+"?api_key=" +
                BuildConfig.MOVIE_API_KEY+"&language=en-US&query="+mJudulMovie;

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0 ; i < list.length() ; i++){
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItems_.add(movieItems);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItems_;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }
}
