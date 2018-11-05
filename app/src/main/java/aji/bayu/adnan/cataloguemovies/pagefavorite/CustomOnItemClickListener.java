package aji.bayu.adnan.cataloguemovies.pagefavorite;

import android.view.View;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class CustomOnItemClickListener implements View.OnClickListener {
    private int mPosition;

    private OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int mPosition, OnItemClickCallback onItemClickCallback) {
        this.mPosition = mPosition;

        this.onItemClickCallback = onItemClickCallback;

    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int mPosition);

    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, mPosition);

    }
}
