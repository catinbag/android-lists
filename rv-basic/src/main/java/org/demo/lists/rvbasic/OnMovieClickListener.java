package org.demo.lists.rvbasic;

import org.demo.lists.core.model.Movie;

/** The adapter reports a click. The screen decides what to do. */
public interface OnMovieClickListener {
    /** @param movie null if the position is out of range */
    void onMovieClick(Movie movie, int position);
}
