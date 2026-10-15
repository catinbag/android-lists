package org.demo.lists.rvinteraction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

import java.util.List;

/** The mistake: attachToRoot is true. */
public class AttachTrueAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private final List<Movie> movies;

    public AttachTrueAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // With true, inflate adds the row to parent and returns parent, not the row.
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, true);
        return new MovieViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
