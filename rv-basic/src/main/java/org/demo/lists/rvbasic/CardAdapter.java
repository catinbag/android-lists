package org.demo.lists.rvbasic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private final List<Movie> movies;
    private final int itemWidthPx;

    /** @param itemWidthPx fixed width of a row, or 0 to keep the width from the layout */
    public CardAdapter(List<Movie> movies, int itemWidthPx) {
        this.movies = movies;
        this.itemWidthPx = itemWidthPx;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        if (itemWidthPx > 0) {
            row.getLayoutParams().width = itemWidthPx;
        }
        return new CardViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
