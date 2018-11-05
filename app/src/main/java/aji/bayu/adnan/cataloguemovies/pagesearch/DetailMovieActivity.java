package aji.bayu.adnan.cataloguemovies.pagesearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import aji.bayu.adnan.cataloguemovies.R;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class DetailMovieActivity extends AppCompatActivity {
    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_DESKRIPSI     = "extra_overview";
    public static String EXTRA_RILIS = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_poster_jpg";
    public static String EXTRA_RATE_COUNT   = "extra_rate_count";
    public static String EXTRA_RATE         = "extra_rate";

    private TextView tvJudul, tvOverview, tvReleaseDate, tvRating;
    private ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvJudul = (TextView)findViewById(R.id.detailJudul);
        tvOverview = (TextView)findViewById(R.id.detailOverview);
        tvReleaseDate = (TextView)findViewById(R.id.detailReleaseDate);
        tvRating = (TextView)findViewById(R.id.detailVote);
        imgPoster = (ImageView)findViewById(R.id.imgPoster);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String overview = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String rating = getIntent().getStringExtra(EXTRA_RATE);
        String rating_count = getIntent().getStringExtra(EXTRA_RATE_COUNT);
        String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        String release_date = getIntent().getStringExtra(EXTRA_RILIS);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvJudul.setText(title);
        tvOverview.setText(overview);
        tvRating.setText(rating_count+" Ratings ("+rating+"/10)");
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/"+poster_jpg).into(imgPoster);
    }
}
