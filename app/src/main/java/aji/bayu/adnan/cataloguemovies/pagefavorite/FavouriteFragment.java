package aji.bayu.adnan.cataloguemovies.pagefavorite;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import aji.bayu.adnan.cataloguemovies.MainActivity;
import aji.bayu.adnan.cataloguemovies.R;

import static aji.bayu.adnan.cataloguemovies.DatabaseContract.CONTENT_URI;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class FavouriteFragment extends Fragment {
    //    private Toolbar toolbar;
    private MainActivity mMainActivity;

    private Cursor mList;

    private FavouriteAdapter mAdapter;

    private RecyclerView mRvNotes;

    private ProgressBar mProgressBar;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mMainActivity.setupNavigationDrawer(toolbar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mMainActivity = (MainActivity) getActivity();
    }

    private void showSnackbarMessage(){
        Snackbar.make(mRvNotes, "", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

//        toolbar = view.findViewById(R.id.toolbar);
////        toolbar.setTitle("Favorite Movie");
//        mMainActivity.setSupportActionBar(toolbar);

        mRvNotes = view.findViewById(R.id.recycler_view);
        mRvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvNotes.setHasFixedSize(true);

        mProgressBar = view.findViewById(R.id.progress_bar);

        mAdapter = new FavouriteAdapter(getContext());
        mAdapter.setListMovies(mList);
        mRvNotes.setAdapter(mAdapter);

        new LoadMovieAsync().execute();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            mProgressBar.setVisibility(View.GONE);

            mList = notes;
            mAdapter.setListMovies(mList);
            mAdapter.notifyDataSetChanged();

            if (mList.getCount() == 0){
                showSnackbarMessage();
            }
        }
    }
}
