package org.demo.lists.core.data;

import org.demo.lists.core.model.Movie;
import org.demo.lists.core.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> movies;

    public InMemoryMovieRepository(List<Movie> initial) {
        this.movies = new ArrayList<>(initial);
    }

    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(movies);
    }

    @Override
    public Movie getById(long id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) return movie;
        }
        throw new NoSuchElementException("No movie with id " + id);
    }

    @Override
    public void add(Movie movie) {
        movies.add(movie);
    }

    @Override
    public boolean remove(long id) {
        Iterator<Movie> it = movies.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
