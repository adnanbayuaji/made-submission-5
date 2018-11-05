package aji.bayu.adnan.cataloguemovies.adapter;

import android.view.View;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class CustomOnItemClickListener implements View.OnClickListener {

    private int mPosition;
    private OnItemClickCallback onItemClickCallback;

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, mPosition);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int mPosition);
    }

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.mPosition = position;
        this.onItemClickCallback = onItemClickCallback;
    }
}
