package org.demo.lists.core.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.demo.lists.core.model.Movie;
import org.junit.Test;

import java.util.List;

public class MovieGeneratorTest {

    @Test
    public void generateReturnsRequestedCount() {
        assertEquals(10, new MovieGenerator().generate(10).size());
        assertEquals(1000, new MovieGenerator().generate(1000).size());
    }

    @Test
    public void idsGoFromOneToCount() {
        List<Movie> movies = new MovieGenerator().generate(50);
        for (int i = 0; i < movies.size(); i++) {
            assertEquals(i + 1, movies.get(i).getId());
        }
    }

    @Test
    public void sameSeedGivesSameMovies() {
        List<Movie> first = new MovieGenerator(7).generate(30);
        List<Movie> second = new MovieGenerator(7).generate(30);
        for (int i = 0; i < first.size(); i++) {
            assertSameFields(first.get(i), second.get(i));
        }
    }

    @Test
    public void differentSeedGivesDifferentMovies() {
        assertFalse(describe(new MovieGenerator(1).generate(20))
                .equals(describe(new MovieGenerator(2).generate(20))));
    }

    @Test
    public void yearAndRatingStayInRange() {
        for (Movie movie : new MovieGenerator().generate(500)) {
            assertTrue(movie.getYear() >= 1950 && movie.getYear() <= 2024);
            assertTrue(movie.getRating() >= 1.0 && movie.getRating() <= 9.9);
        }
    }

    @Test
    public void descriptionLengthVaries() {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (Movie movie : new MovieGenerator().generate(100)) {
            int length = movie.getDescription().length();
            min = Math.min(min, length);
            max = Math.max(max, length);
        }
        assertTrue(min < max);
    }

    private static void assertSameFields(Movie a, Movie b) {
        assertEquals(a.getId(), b.getId());
        assertEquals(a.getTitle(), b.getTitle());
        assertEquals(a.getYear(), b.getYear());
        assertEquals(a.getGenre(), b.getGenre());
        assertEquals(a.getRating(), b.getRating(), 0.0);
        assertEquals(a.getDescription(), b.getDescription());
    }

    private static String describe(List<Movie> movies) {
        StringBuilder text = new StringBuilder();
        for (Movie movie : movies) {
            text.append(movie.getTitle()).append(movie.getYear()).append(movie.getDescription());
        }
        return text.toString();
    }
}
