package aji.bayu.adnan.cataloguemovies.pagesearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import aji.bayu.adnan.cataloguemovies.R;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private String mTemp;

    public MovieAdapter(Context mContext) {
        this.mContext    = mContext;
        mInflater       = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        TextView mTvJudul;
        TextView mTvDesk;
        TextView mTvRilis;
        ImageView mTvGambar;
    }

    public String setTanggal(String data)
    {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        String date_of_release = null;
        try {
            Date date = date_format.parse(data);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            date_of_release = new_date_format.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date_of_release;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder                  = new ViewHolder();
            convertView             = mInflater.inflate(R.layout.item_row_movies, null);
            holder.mTvJudul          = (TextView)convertView.findViewById(R.id.tvTitle);
            holder.mTvDesk    = (TextView)convertView.findViewById(R.id.tvDescription);
            holder.mTvRilis    = (TextView)convertView.findViewById(R.id.tvDate);
            holder.mTvGambar        = (ImageView)convertView.findViewById(R.id.imgMoviePict);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvJudul.setText(mData.get(position).getMov_title());
        String overview = mData.get(position).getMov_description();
        if(TextUtils.isEmpty(overview)){
            mTemp = "No data";
        }else{
            mTemp = overview;
        }
        holder.mTvDesk.setText(mTemp);
        String retrievedDate = mData.get(position).getMov_date();
        String sem = setTanggal(retrievedDate);
        holder.mTvRilis.setText(sem);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w154/"+mData.get(position).getMov_image()).placeholder(mContext.getResources().getDrawable(R.drawable.ic_image_black_50dp)).error(mContext.getResources().getDrawable(R.drawable.ic_image_black_50dp)).into(holder.mTvGambar);

        return convertView;
    }

    public void setData(ArrayList<MovieItems> items){
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
