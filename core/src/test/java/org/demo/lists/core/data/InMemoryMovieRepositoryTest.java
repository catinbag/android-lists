package org.demo.lists.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class InMemoryMovieRepositoryTest {

    private static Movie movie(long id) {
        return new Movie(id, "Title " + id, 2000, Genre.DRAMA, 5.0, "text");
    }

    private static InMemoryMovieRepository repositoryWithTwoMovies() {
        List<Movie> initial = new ArrayList<>();
        initial.add(movie(1));
        initial.add(movie(2));
        return new InMemoryMovieRepository(initial);
    }

    @Test
    public void constructorCopiesInitialList() {
        List<Movie> initial = new ArrayList<>();
        initial.add(movie(1));
        InMemoryMovieRepository repository = new InMemoryMovieRepository(initial);
        initial.add(movie(2));
        assertEquals(1, repository.getAll().size());
    }

    @Test
    public void getAllReturnsCopy() {
        InMemoryMovieRepository repository = repositoryWithTwoMovies();
        repository.getAll().add(movie(3));
        assertEquals(2, repository.getAll().size());
    }

    @Test
    public void addAppendsMovie() {
        InMemoryMovieRepository repository = repositoryWithTwoMovies();
        repository.add(movie(3));
        List<Movie> all = repository.getAll();
        assertEquals(3, all.size());
        assertEquals(3, all.get(2).getId());
    }

    @Test
    public void removeKnownIdReturnsTrueAndRemoves() {
        InMemoryMovieRepository repository = repositoryWithTwoMovies();
        assertTrue(repository.remove(1));
        assertEquals(1, repository.getAll().size());
        assertEquals(2, repository.getAll().get(0).getId());
    }

    @Test
    public void removeUnknownIdReturnsFalse() {
        InMemoryMovieRepository repository = repositoryWithTwoMovies();
        assertFalse(repository.remove(99));
        assertEquals(2, repository.getAll().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void getByIdUnknownIdThrows() {
        repositoryWithTwoMovies().getById(99);
    }
}
