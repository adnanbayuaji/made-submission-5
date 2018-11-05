package aji.bayu.adnan.cataloguemovies.pagenowplaying;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import aji.bayu.adnan.cataloguemovies.BuildConfig;
import aji.bayu.adnan.cataloguemovies.R;
import aji.bayu.adnan.cataloguemovies.adapter.Adapter;
import aji.bayu.adnan.cataloguemovies.adapter.MovieItems;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaySekarangFragment extends Fragment {

    private View mView;
    private List<MovieItems> mLists;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRv;
    private static final String API_URL = BuildConfig.MOVIE_URL+"/now_playing?api_key="+BuildConfig.MOVIE_API_KEY+"&language=en-US";

    public void jsonData(String response)
    {
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("results");
            for (int i = 0; i < array.length(); i++){

                MovieItems movies = new MovieItems();
                JSONObject data = array.getJSONObject(i);
                movies.setJudul(data.getString("title"));
                movies.setDeskripsi(data.getString("overview"));
                movies.setTanggal(data.getString("release_date"));
                movies.setGambar(data.getString("poster_path"));
                movies.setJumlahPeringkat(data.getString("vote_count"));
                movies.setPeringkat(data.getString("vote_average"));
                mLists.add(movies);

            }

            mAdapter = new Adapter(mLists, getActivity());
            mRv.setAdapter(mAdapter);

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    public PlaySekarangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_play_sekarang, container,false);

        mRv = (RecyclerView)mView.findViewById(R.id.rv_category);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLists = new ArrayList<>();

        loadData();

        return mView;
    }

    private void loadData() {
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                jsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadData();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
