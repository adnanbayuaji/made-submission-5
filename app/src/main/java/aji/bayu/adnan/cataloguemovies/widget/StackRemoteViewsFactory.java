/*
 * Created by Jihad044.
 * Copyright (c) 2018. All rights reserved.
 * Last modified 25/10/18 9:55 AM.
 */

package aji.bayu.adnan.cataloguemovies.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import aji.bayu.adnan.cataloguemovies.BuildConfig;
import aji.bayu.adnan.cataloguemovies.R;
import aji.bayu.adnan.cataloguemovies.adapter.ResultsItem;

import java.util.concurrent.ExecutionException;

import static aji.bayu.adnan.cataloguemovies.DatabaseContract.CONTENT_URI;

/**
 * Created by Jihad044 on 25/10/2018.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private Cursor list;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }


    @Override
    public RemoteViews getViewAt(int i) {
        ResultsItem item = getItem(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.favourite_widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_IMG + "w500" + item.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavouriteWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }


    private ResultsItem getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new ResultsItem(list);
    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }


}
