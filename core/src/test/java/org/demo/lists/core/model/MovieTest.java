package org.demo.lists.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class MovieTest {

    @Test
    public void toStringShowsTitleAndYear() {
        Movie movie = new Movie(1, "Silent River", 1994, Genre.DRAMA, 8.0, "text");
        assertEquals("Silent River (1994)", movie.toString());
    }

    @Test
    public void equalsAndHashCodeUseIdOnly() {
        Movie a = new Movie(1, "A", 2000, Genre.DRAMA, 5.0, "x");
        Movie b = new Movie(1, "B", 2010, Genre.COMEDY, 9.0, "y");
        Movie c = new Movie(2, "A", 2000, Genre.DRAMA, 5.0, "x");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
