package com.android.amartiolivart.themoviedbsimpleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.amartiolivart.themoviedbsimpleapp.tasks.LoadTvShowsAsyncTask;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.TaskCallback;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCallback, ScrollAdapter.ItemClickListener {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    int urlPageCounter = 0;

    private ScrollAdapter scrollAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMainLayout();
    }

    private void setupMainLayout(){
        // View instantiation
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        // Adapters
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        scrollAdapter = new ScrollAdapter(this, this);
        recyclerView.setAdapter(scrollAdapter);
        addDataToList();

        recyclerView.addOnScrollListener(new ScrollListener() {
            @Override
            public void loadMoreItems() {
                addDataToList();
            }
        });
    }

    private void addDataToList() {
        progressBar.setVisibility(View.VISIBLE);
        LoadTvShowsAsyncTask tvShowsAsyncTask = new LoadTvShowsAsyncTask(this);
        tvShowsAsyncTask.execute(getTvShowURL());
    }

    private String getTvShowURL(){
        urlPageCounter++;
        return getString(R.string.base_url) + getApiKeyTMDB() +"&language=es-ES&page=" + urlPageCounter;
    }

    private String getApiKeyTMDB(){
        return getResources().getString(R.string.TMDBAPIKey);
    }

    @Override
    public void setTvShows(List tvShows) {
        scrollAdapter.setTvShows(tvShows);
        scrollAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(TvShow show) {
        // Serializable instead of Parcelable due to the low amount of data passed between activities
        Intent i = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("tvShow", show);
        i.putExtras(bundle);
        startActivity(i);
    }
}
