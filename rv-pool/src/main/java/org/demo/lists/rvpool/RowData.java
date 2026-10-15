package org.demo.lists.rvpool;

import org.demo.lists.core.model.Movie;

import java.util.List;

/** One row of the outer list: a title and the movies of the inner list. */
public class RowData {
    private final String title;
    private final List<Movie> movies;

    public RowData(String title, List<Movie> movies) {
        this.title = title;
        this.movies = movies;
    }

    public String getTitle() {
        return title;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
