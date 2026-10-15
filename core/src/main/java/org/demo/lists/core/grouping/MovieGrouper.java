package org.demo.lists.core.grouping;

import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class MovieGrouper {

    private MovieGrouper() {
    }

    /**
     * Groups movies by genre. The genres come in the order of the Genre enum.
     * A genre without movies is not in the result. The order inside a group is kept.
     */
    public static Map<Genre, List<Movie>> byGenre(List<Movie> movies) {
        Map<Genre, List<Movie>> groups = new EnumMap<>(Genre.class);
        for (Movie movie : movies) {
            groups.computeIfAbsent(movie.getGenre(), genre -> new ArrayList<>()).add(movie);
        }
        return groups;
    }
}
