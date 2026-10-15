package org.demo.lists.core.generator;

import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Creates the same movies for the same seed. */
public class MovieGenerator {
    public static final long DEFAULT_SEED = 42;

    private static final String[] ADJECTIVES = {
            "Silent", "Last", "Golden", "Hidden", "Broken", "Distant",
            "Midnight", "Crimson", "Forgotten", "Wild", "Frozen", "Endless"
    };
    private static final String[] NOUNS = {
            "River", "Garden", "Signal", "Harbor", "Mirror", "Journey",
            "Letter", "Empire", "Shadow", "Island", "Orchestra", "Winter"
    };
    private static final String[] PHRASES = {
            "A quiet town hides an old secret.",
            "Two strangers meet on a long night train.",
            "A young engineer finds a message from the past.",
            "The story follows one family through many years.",
            "Nobody expects the final decision of the captain.",
            "A small mistake changes the life of a whole city.",
            "An old friend returns with a strange request.",
            "The search for a lost map leads to the sea.",
            "A doctor must choose between duty and friendship.",
            "The last concert starts when the lights go out."
    };

    private final Random random;

    public MovieGenerator() {
        this(DEFAULT_SEED);
    }

    public MovieGenerator(long seed) {
        this.random = new Random(seed);
    }

    /** Returns movies with ids from 1 to count. */
    public List<Movie> generate(int count) {
        List<Movie> movies = new ArrayList<>(count);
        for (int id = 1; id <= count; id++) {
            movies.add(generateOne(id));
        }
        return movies;
    }

    /** The result depends on how many movies this generator created before. */
    public Movie generateOne(long id) {
        String title = pick(ADJECTIVES) + " " + pick(NOUNS);
        int year = 1950 + random.nextInt(75);
        Genre genre = Genre.values()[random.nextInt(Genre.values().length)];
        double rating = (10 + random.nextInt(90)) / 10.0;
        return new Movie(id, title, year, genre, rating, description());
    }

    private String description() {
        int sentences = 1 + random.nextInt(4);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < sentences; i++) {
            if (i > 0) text.append(' ');
            text.append(pick(PHRASES));
        }
        return text.toString();
    }

    private String pick(String[] values) {
        return values[random.nextInt(values.length)];
    }
}
