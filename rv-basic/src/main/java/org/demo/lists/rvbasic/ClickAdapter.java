package org.demo.lists.rvbasic;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** Two places for the click listener. The second one stays correct after a removal. */
public class ClickAdapter extends MovieAdapter {

    public enum ClickStyle { IN_BIND, IN_HOLDER }

    private final ClickStyle style;
    private final OnMovieClickListener listener;

    public ClickAdapter(List<Movie> movies, CallCounter counter,
                        ClickStyle style, OnMovieClickListener listener) {
        super(movies, counter);
        this.style = style;
        this.listener = listener;
    }

    /** IN_HOLDER: one listener per row. The position is asked at the moment of the click. */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (style == ClickStyle.IN_HOLDER) {
            holder.itemView.setOnClickListener(v -> {
                int position = holder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMovieClick(movies.get(position), position);
                }
            });
        }
        return holder;
    }

    /** IN_BIND: the position is copied at bind time. It can be old at click time. */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (style == ClickStyle.IN_BIND) {
            holder.itemView.setOnClickListener(v -> {
                Movie movie = position < movies.size() ? movies.get(position) : null;
                listener.onMovieClick(movie, position);
            });
        }
    }

    public void removeFirst() {
        if (movies.isEmpty()) return;
        movies.remove(0);
        notifyItemRemoved(0);
    }
}
