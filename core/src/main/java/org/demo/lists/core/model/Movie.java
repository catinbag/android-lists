package org.demo.lists.core.model;

public final class Movie {
    private final long id;
    private final String title;
    private final int year;
    private final Genre genre;
    private final double rating;
    private final String description;

    public Movie(long id, String title, int year, Genre genre, double rating, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.description = description;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public Genre getGenre() { return genre; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Movie && ((Movie) o).id == id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    // ArrayAdapter shows the result of toString(), so it must be readable.
    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
