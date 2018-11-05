package aji.bayu.adnan.favouritemovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.DEKRIPSI;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.GAMBAR;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.JUDUL;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.MovieColumns.RILIS;
import static aji.bayu.adnan.favouritemovie.DatabaseContract.getColumnString;

/**
 * Created by Jihad044 on 10/09/2018.
 */

public class MovieAdapter extends CursorAdapter {
    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor!=null){
            TextView textViewTitle;
            TextView textViewOverview;
            TextView textViewRelease;
            Button btnShare;
            ImageView imgPoster;
            textViewRelease = view.findViewById(R.id.tv_release);
            textViewTitle = view.findViewById(R.id.tv_title);
            imgPoster = view.findViewById(R.id.img_poster);
            textViewOverview = view.findViewById(R.id.tv_description);
            textViewTitle.setText(getColumnString(cursor,JUDUL));
            textViewRelease.setText(getColumnString(cursor,RILIS));
//            Picasso.with(context).load(getColumnString(cursor, GAMBAR)).placeholder(R.drawable.loading).error(R.drawable.error).into(imgPoster);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+getColumnString(cursor, GAMBAR)).placeholder(R.drawable.loading).error(R.drawable.error).into(imgPoster);

            textViewOverview.setText(getColumnString(cursor,DEKRIPSI));
            btnShare = view.findViewById(R.id.btn_share);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, getColumnString(cursor, JUDUL)+" \n"+getColumnString(cursor,DEKRIPSI));
                    intent.setType("text/plain");
                    context.startActivity(intent);
                    Toast.makeText(context, "Share "+ getColumnString(cursor,JUDUL), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
