package com.android.amartiolivart.themoviedbsimpleapp.Activities.ScrollComponents;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by amartiolivart on 23/12/17.
 */

public abstract class ScrollListener extends RecyclerView.OnScrollListener {

    int mPreviousTotal = 0;
    // Check new data at the star of the app
    boolean newData = true;
    // Load new data when 5 items remain to show
    int visibleThreshold = 5;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // Returns the number of children in the group.
        int visibleItemCount = recyclerView.getChildCount();
        // Returns the number of items in the adapter bound to the parent RecyclerView.
        int totalShows = recyclerView.getLayoutManager().getItemCount();
        // The adapter position of the first visible item
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (newData) {
            if (totalShows > mPreviousTotal) {
                newData = false;
                mPreviousTotal = totalShows;
            }
        }
        if (!newData && (totalShows - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            loadMoreItems();
            newData = true;
        }
    }

    public abstract void loadMoreItems();
}
