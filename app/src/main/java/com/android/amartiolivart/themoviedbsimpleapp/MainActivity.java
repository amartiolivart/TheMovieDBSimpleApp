package com.android.amartiolivart.themoviedbsimpleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.amartiolivart.themoviedbsimpleapp.tasks.LoadPopularTvShowsAsyncTask;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.TaskCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCallback {

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

        scrollAdapter = new ScrollAdapter(this);
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
        LoadPopularTvShowsAsyncTask tvShowsAsyncTask = new LoadPopularTvShowsAsyncTask(this);
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
}
