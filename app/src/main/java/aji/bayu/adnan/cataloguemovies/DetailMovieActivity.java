package aji.bayu.adnan.cataloguemovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import aji.bayu.adnan.cataloguemovies.helpers.MovieHelper;
import aji.bayu.adnan.cataloguemovies.pagefavorite.MovieItem;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class DetailMovieActivity extends AppCompatActivity {
    private MovieItem mMovieItem;

    private MovieHelper mMovieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        String from = getIntent().getStringExtra("from");

        if (from.equals("MovieAdapter")){
            mMovieItem = getIntent().getParcelableExtra("item");
        } else if (from.equals("FavouriteAdapter")){
            mMovieHelper = new MovieHelper(this);
            mMovieHelper.open();
            Uri uri = getIntent().getData();
            if (uri!=null){
                Cursor cursor = getContentResolver().query(uri, null,null,null,null);
                if (cursor!=null){
                    if (cursor.moveToFirst()){
                        mMovieItem = new MovieItem(cursor);
                        cursor.close();
                    }
                }
            }
        }

        TextView titleTextView = findViewById(R.id.tv_title);
        TextView descriptionTextView = findViewById(R.id.tv_description);
        TextView releaseTextView = findViewById(R.id.tv_release);
        ImageView posterImageView = findViewById(R.id.img_poster);

        titleTextView.setText(mMovieItem.getTitleMovie());
        descriptionTextView.setText(mMovieItem.getOverView());
        releaseTextView.setText(mMovieItem.getReleaseDate());

        Picasso
                .with(this)
                .load(mMovieItem.getImagePoster())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(posterImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMovieHelper != null){
            mMovieHelper.close();
        }
    }
}
