package com.android.amartiolivart.themoviedbsimpleapp.tasks;

import java.util.List;

/**
 * Created by amartiolivart on 23/12/17.
 */

public interface TaskCallback<T> {

    void setTvShows(List<T> tvShows);
}
