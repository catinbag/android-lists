package org.demo.lists.rvlayouts;

/** One row of the list: a genre header or a movie. */
public interface ListRow {
    int TYPE_HEADER = 0;
    int TYPE_MOVIE = 1;

    int getType();
}
