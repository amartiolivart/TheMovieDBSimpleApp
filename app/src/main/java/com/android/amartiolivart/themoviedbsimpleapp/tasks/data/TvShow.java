package com.android.amartiolivart.themoviedbsimpleapp.tasks.data;

import java.io.Serializable;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class TvShow implements Serializable {

    private String image;
    private String title;
    private String voteAverage;
    private String id;
    private String overview;
    private String posterPath;

    public TvShow(String image, String title, String voteAverage, String id, String overview, String posterPath) {
        this.image = image;
        this.title = title;
        this.voteAverage = voteAverage;
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public TvShow() {}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
