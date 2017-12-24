package com.android.amartiolivart.themoviedbsimpleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.amartiolivart.themoviedbsimpleapp.tasks.LoadTvShowsAsyncTask;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.TaskCallback;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class DetailActivity extends AppCompatActivity implements TaskCallback {

    TvShow show;
    private ViewPager viewPager;
    private ScreenSlidesAdapter screenSlidesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            show = (TvShow) extras.getSerializable("tvShow");
            setTitle(show.getTitle());
        }
        // Make the http request in order to get Similar Shows
        loadSimilarTvShows();

        // Instantiate ViewPager and PagerAdapter.
        viewPager = findViewById(R.id.pager);
        screenSlidesAdapter = new ScreenSlidesAdapter(getSupportFragmentManager());
        // Add current show, already loaded
        screenSlidesAdapter.addCurrentShow(show);
        viewPager.setAdapter(screenSlidesAdapter);
        // Add PageListener in order to Chenge ActivityTitle acording to TvShow
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // DoNothing
            }
            @Override
            public void onPageSelected(int position) {
                setTitle(screenSlidesAdapter.getTvShows().get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // DoNothing
            }
        });
    }

    private void loadSimilarTvShows(){
        LoadTvShowsAsyncTask tvShowsAsyncTask = new LoadTvShowsAsyncTask(this);
        tvShowsAsyncTask.execute(getSimilarTvShowURL());
    }

    private String getSimilarTvShowURL(){
        String url = getString(R.string.base_similar_url);
        url = url.replace("((show_id))", show.getId());
        url = url.replace("((api_key))", getApiKeyTMDB());
        return url + "&language=es-ES";
    }

    private String getApiKeyTMDB(){
        return getResources().getString(R.string.TMDBAPIKey);
    }

    @Override
    public void setTvShows(List tvShows) {
        screenSlidesAdapter.populateShowList(tvShows);
    }

    // Screen Adapter for FragmentsPage
    public static class ScreenSlidesAdapter extends FragmentPagerAdapter {
        private static List<TvShow> tvShows;

        public static List<TvShow> getTvShows() {
            return tvShows;
        }

        public ScreenSlidesAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            tvShows = new ArrayList<>();
        }

        public void populateShowList(List<TvShow> list){
            this.tvShows.addAll(list);
            notifyDataSetChanged();
        }

        public void addCurrentShow(TvShow tvShow){
            tvShows.add(tvShow);
            notifyDataSetChanged();
        }

        // Returns total number of shows
        @Override
        public int getCount() {
            return tvShows.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(tvShows.get(position));
        }

    }
}


