package com.android.amartiolivart.themoviedbsimpleapp;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ViewHolder> {

    private List<TvShow> showsList;


    public ScrollAdapter() {
        this.showsList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TvShow show = showsList.get(position);
        holder.titleView.setText(show.getTitle());
        // TODO Glide
        // holder.imageView.setText(movie.getGenre());
        holder.voteView.setText(show.getVoteAverage());    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public void setTvShows(List tvShows) {
        this.showsList.addAll(tvShows);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        ImageView imageView;
        TextView voteView;

        ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_show);
            imageView = itemView.findViewById(R.id.image_show);
            voteView = itemView.findViewById(R.id.popularity_show);
        }
    }
}
