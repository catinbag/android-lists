package org.demo.lists.rvanimations;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

import java.util.List;

/** Every change first edits the list, then tells the RecyclerView what changed. */
public class EditableMovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    protected final List<Movie> movies;
    private final int layoutRes;

    public EditableMovieAdapter(List<Movie> movies) {
        this(movies, R.layout.item_movie);
    }

    public EditableMovieAdapter(List<Movie> movies, int layoutRes) {
        this.movies = movies;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Movie getMovie(int position) {
        return movies.get(position);
    }

    public void insert(int position, Movie movie) {
        movies.add(position, movie);
        notifyItemInserted(position);
    }

    public Movie remove(int position) {
        Movie movie = movies.remove(position);
        notifyItemRemoved(position);
        return movie;
    }

    public void move(int from, int to) {
        Movie movie = movies.remove(from);
        movies.add(to, movie);
        notifyItemMoved(from, to);
    }

    /** Replaces the movie with a copy that has a mark in the title. */
    public void change(int position) {
        changeSilently(position);
        notifyItemChanged(position);
    }

    /** Mistake demo: the list changes, but RecyclerView is not told. */
    public void changeSilently(int position) {
        Movie m = movies.get(position);
        movies.set(position, new Movie(m.getId(), m.getTitle() + " *", m.getYear(),
                m.getGenre(), m.getRating(), m.getDescription()));
    }

    /** The same removal, but RecyclerView gets no detail: all rows are bound again. */
    public void removeAndNotifyAll(int position) {
        movies.remove(position);
        notifyDataSetChanged();
    }
}
