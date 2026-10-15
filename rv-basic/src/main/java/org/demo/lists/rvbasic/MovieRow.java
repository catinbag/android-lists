package org.demo.lists.rvbasic;

import org.demo.lists.core.model.Movie;

public class MovieRow implements ListRow {
    private final Movie movie;

    public MovieRow(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public int getType() {
        return TYPE_MOVIE;
    }
}
