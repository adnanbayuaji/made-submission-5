package aji.bayu.adnan.cataloguemovies.pagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import aji.bayu.adnan.cataloguemovies.R;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    ListView mListView;
    EditText mEditJudul;
    ImageView mImgPoster;
    Button mBtnCari;
    MovieAdapter mAdapter;
    private View mView;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false);

        mEditJudul   = (EditText)mView.findViewById(R.id.et_judul);
        String mJudulMovie = mEditJudul.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, mJudulMovie);

        mImgPoster   = (ImageView)mView.findViewById(R.id.imgMoviePict);
        mBtnCari     = (Button)mView.findViewById(R.id.btn_cari);
        mBtnCari.setOnClickListener(movieListener);

        getLoaderManager().initLoader(0, bundle, SearchFragment.this);
        mAdapter     = new MovieAdapter(getActivity());
        mAdapter.notifyDataSetChanged();
        mListView    = (ListView)mView.findViewById(R.id.lvMovie);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View mView,
                                    final int position, long id) {
                MovieItems item = (MovieItems)parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_TITLE, item.getMov_title());
                intent.putExtra(DetailMovieActivity.EXTRA_DESKRIPSI, item.getMov_description());
                intent.putExtra(DetailMovieActivity.EXTRA_RILIS, item.getMov_date());
                intent.putExtra(DetailMovieActivity.EXTRA_POSTER_JPG, item.getMov_image());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE_COUNT, item.getMov_rate_count());
                intent.putExtra(DetailMovieActivity.EXTRA_RATE, item.getMov_rate());
                startActivity(intent);
            }
        });
        return mView;
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        mAdapter.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View mView) {
            String mJudulMovie = mEditJudul.getText().toString();
            if(TextUtils.isEmpty(mJudulMovie)){
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, mJudulMovie);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
        }
    };

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        mAdapter.setData(movieItems);
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String temp = "";
        if (bundle != null){
            temp = bundle.getString(EXTRAS_MOVIE);
        }
        return new MovieAsyncTaskLoader(getActivity(), temp);
    }
}
