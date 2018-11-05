package aji.bayu.adnan.cataloguemovies.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import aji.bayu.adnan.cataloguemovies.R;
import aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.CONTENT_URI;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.DESKRIPSI;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.GAMBAR;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.JUDUL;
import static aji.bayu.adnan.cataloguemovies.DatabaseContract.MovieColumns.RILIS;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<MovieItems> movieLists;
    private Context context;

    public Adapter(List<MovieItems> movieLists, Context context) {
        // generate constructors to initialise the List and Context objects
        this.movieLists = movieLists;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // this method will bind the data to the ViewHolder from hence it'll be shown to other Views
        final MovieItems movList = movieLists.get(position);
        holder.title.setText(movList.getJudul());
        holder.overview.setText(movList.getDeskripsi());

        String release_date = movList.getTanggal();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.date.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/"+movList.getGambar())
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                addFavourite(movList);
                Toast.makeText(context, "Favorite: "+movList.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: "+movList.getJudul(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems movieList = movieLists.get(position);
                Intent Intent = new Intent(context, DetailMovieActivity.class);
                Intent.putExtra(DetailMovieActivity.EXTRA_TITLE, movieList.getJudul());
                Intent.putExtra(DetailMovieActivity.EXTRA_DESKRIPSI, movieList.getDeskripsi());
                Intent.putExtra(DetailMovieActivity.EXTRA_POSTER_JPG, movieList.getGambar());
                Intent.putExtra(DetailMovieActivity.EXTRA_RILIS, movieList.getTanggal());
                Intent.putExtra(DetailMovieActivity.EXTRA_RATE, movieList.getPeringkat());
                Intent.putExtra(DetailMovieActivity.EXTRA_RATE_COUNT, movieList.getJumlahPeringkat());
                context.startActivity(Intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_now_playing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        // deklarasi View objects
        public TextView title, overview, date;
        public ImageView poster;
        public Button btnFavorite, btnShare;
        public LinearLayout cvDetail; //cv = cardview

        public ViewHolder(View itemView) {
            super(itemView);

            // init the View objects
            title       = (TextView) itemView.findViewById(R.id.tv_item_name);
            poster      = (ImageView) itemView.findViewById(R.id.img_item_photo);
            overview    = (TextView) itemView.findViewById(R.id.tv_item_overview);
            date        = (TextView) itemView.findViewById(R.id.tv_item_date);
            btnFavorite = (Button) itemView.findViewById(R.id.btn_set_favorite);
            btnShare    = (Button) itemView.findViewById(R.id.btn_set_share);
            cvDetail    = (LinearLayout) itemView.findViewById(R.id.cv_movie);
        }

    }

    private void addFavourite(MovieItems movieItem) {
        String titleMovie = movieItem.getJudul();
        String overView = movieItem.getDeskripsi();
        String releaseDate = movieItem.getTanggal();
        String imagePoster = movieItem.getGambar();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL, titleMovie);
        contentValues.put(DESKRIPSI, overView);
        contentValues.put(RILIS, releaseDate);
        contentValues.put(GAMBAR, imagePoster);
        context.getContentResolver().insert(CONTENT_URI,contentValues);
    }
}
