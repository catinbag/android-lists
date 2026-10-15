package org.demo.lists.rvbasic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    protected final List<Movie> movies;
    protected final CallCounter counter;

    public MovieAdapter(List<Movie> movies, CallCounter counter) {
        this.movies = movies;
        this.counter = counter;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        counter.onInflate();
        // attachToRoot must be false: RecyclerView attaches the row itself.
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(row, counter);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
        counter.onBind();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
