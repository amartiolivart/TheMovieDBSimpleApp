package com.android.amartiolivart.themoviedbsimpleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.amartiolivart.themoviedbsimpleapp.glide.GlideApp;
import com.android.amartiolivart.themoviedbsimpleapp.tasks.data.TvShow;

/**
 * Created by amartiolivart on 24/12/17.
 */

public class ScreenSlidePageFragment extends Fragment {

    TvShow show;
    ImageView poster;
    TextView overView;

    public static ScreenSlidePageFragment newInstance(TvShow show) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putSerializable("tvShow", show);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            show = (TvShow) getArguments().getSerializable("tvShow");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_slide_page, container, false);

        poster = view.findViewById(R.id.show_poster);
        overView = view.findViewById(R.id.overview_text);

        // SetUp
        GlideApp.with(this).load(getString(R.string.image_base_url) + show.getPosterPath()).into(poster);
        if ("null".equals(show.getOverview())){
            overView.setText(R.string.no_description);
        }else{
            overView.setText(show.getOverview());
        }
        overView.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }
}

