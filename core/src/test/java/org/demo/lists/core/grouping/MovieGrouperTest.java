package org.demo.lists.core.grouping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MovieGrouperTest {

    private static Movie movie(long id, Genre genre) {
        return new Movie(id, "Title " + id, 2000, genre, 5.0, "text");
    }

    @Test
    public void genresComeInEnumOrder() {
        List<Movie> movies = Arrays.asList(
                movie(1, Genre.COMEDY), movie(2, Genre.DRAMA), movie(3, Genre.SCI_FI));
        List<Genre> order = new ArrayList<>(MovieGrouper.byGenre(movies).keySet());
        assertEquals(Arrays.asList(Genre.DRAMA, Genre.SCI_FI, Genre.COMEDY), order);
    }

    @Test
    public void orderInsideGroupIsKept() {
        List<Movie> movies = Arrays.asList(
                movie(1, Genre.DRAMA), movie(2, Genre.CRIME), movie(3, Genre.DRAMA));
        List<Movie> drama = MovieGrouper.byGenre(movies).get(Genre.DRAMA);
        assertEquals(1, drama.get(0).getId());
        assertEquals(3, drama.get(1).getId());
    }

    @Test
    public void genreWithoutMoviesIsNotInResult() {
        Map<Genre, List<Movie>> groups =
                MovieGrouper.byGenre(Arrays.asList(movie(1, Genre.DRAMA)));
        assertFalse(groups.containsKey(Genre.ANIMATION));
    }

    @Test
    public void allMoviesStayInGroups() {
        List<Movie> movies = new MovieGenerator().generate(200);
        int total = 0;
        for (List<Movie> group : MovieGrouper.byGenre(movies).values()) {
            total += group.size();
        }
        assertEquals(200, total);
    }
}
