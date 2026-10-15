package org.demo.lists.rvpool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** An adapter of cards that counts inflate, findViewById and bind. */
public class PoolAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private final List<Movie> movies;
    private final CallCounter counter;
    private final int itemWidthPx;

    /** @param itemWidthPx fixed width of a row, or 0 to keep the width from the layout */
    public PoolAdapter(List<Movie> movies, CallCounter counter, int itemWidthPx) {
        this.movies = movies;
        this.counter = counter;
        this.itemWidthPx = itemWidthPx;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        counter.onInflate();
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        if (itemWidthPx > 0) {
            row.getLayoutParams().width = itemWidthPx;
        }
        counter.onFindViewById(); // three lookups in the CardViewHolder constructor
        counter.onFindViewById();
        counter.onFindViewById();
        return new CardViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(movies.get(position));
        counter.onBind();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
