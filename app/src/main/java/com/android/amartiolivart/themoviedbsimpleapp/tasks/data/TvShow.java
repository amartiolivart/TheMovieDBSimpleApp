package com.android.amartiolivart.themoviedbsimpleapp.tasks.data;

/**
 * Created by amartiolivart on 23/12/17.
 */

public class TvShow {

    private String image;
    private String title;
    private String voteAverage;

    public TvShow(String image, String title, String voteAverage) {
        this.image = image;
        this.title = title;
        this.voteAverage = voteAverage;
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
}
