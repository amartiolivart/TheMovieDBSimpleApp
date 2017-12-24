package com.android.amartiolivart.themoviedbsimpleapp;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.amartiolivart.themoviedbsimpleapp.glide.GlideApp;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class DetailActivity extends AppCompatActivity {

    TvShow show;
    ImageView poster;
    TextView overView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            show = (TvShow) extras.getSerializable("tvShow");
            setTitle(show.getTitle());
        }

        setupDetailLayout();

    }

    private void setupDetailLayout(){
        // View instantiation
        poster = findViewById(R.id.show_poster);
        overView = findViewById(R.id.overview_text);

        // SetUp
        GlideApp.with(this).load(getString(R.string.image_base_url) + show.getPosterPath()).into(poster);
        overView.setText(show.getOverview());
        overView.setMovementMethod(new ScrollingMovementMethod());


    }
}
