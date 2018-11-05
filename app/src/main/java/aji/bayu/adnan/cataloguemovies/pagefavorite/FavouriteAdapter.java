package aji.bayu.adnan.cataloguemovies.pagefavorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import aji.bayu.adnan.cataloguemovies.R;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> {
    private Cursor mListMovies;
    private Context mContext;
    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvOverview, mTvRelease;
        ImageView imgPoster;
        Button btnDetail, btnShare;
        MovieViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvOverview = itemView.findViewById(R.id.tv_description);
            mTvRelease = itemView.findViewById(R.id.tv_release);
            imgPoster = itemView.findViewById(R.id.img_poster);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final MovieItem movieItem = getItem(position);

        holder.mTvTitle.setText(movieItem.getTitleMovie());

        holder.mTvOverview.setText(movieItem.getOverView());

        holder.mTvRelease.setText(movieItem.getReleaseDate());

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+movieItem.getImagePoster()).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.imgPoster);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent Intent = new Intent(mContext, aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity.class);
                Intent.putExtra(aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity.EXTRA_TITLE, movieItem.getTitleMovie());
                Intent.putExtra(aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity.EXTRA_DESKRIPSI, movieItem.getOverView());
                Intent.putExtra(aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity.EXTRA_POSTER_JPG, movieItem.getImagePoster());
                Intent.putExtra(aji.bayu.adnan.cataloguemovies.pagesearch.DetailMovieActivity.EXTRA_RILIS, movieItem.getReleaseDate());
                mContext.startActivity(Intent);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, movieItem.getTitleMovie()+" \n"+movieItem.getOverView());
                intent.setType("text/plain");
                mContext.startActivity(intent);
//                Toast.makeText(mContext, "Share "+ movieItem.getTitleMovie(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (mListMovies == null){
            return 0;
        }
        return mListMovies.getCount();
    }

    private MovieItem getItem(int position){
        if (!mListMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(mListMovies);
    }

    public FavouriteAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setListMovies(Cursor mListMovies){
        this.mListMovies = mListMovies;
    }

}
