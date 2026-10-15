package org.demo.lists.rvpool;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;

import java.util.List;

/** The outer adapter. The inner lists can share one pool. */
public class GenreRowAdapter extends RecyclerView.Adapter<GenreRowViewHolder> {
    private final List<RowData> rows;
    private final CallCounter counter;
    private final RecyclerView.RecycledViewPool sharedPool;
    private final int cardWidthPx;

    /** @param sharedPool the pool of all inner lists, or null: each list has its own */
    public GenreRowAdapter(List<RowData> rows, CallCounter counter,
                           RecyclerView.RecycledViewPool sharedPool, int cardWidthPx) {
        this.rows = rows;
        this.counter = counter;
        this.sharedPool = sharedPool;
        this.cardWidthPx = cardWidthPx;
    }

    @NonNull
    @Override
    public GenreRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GenreRowViewHolder holder = new GenreRowViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre_row, parent, false));
        if (sharedPool != null) {
            holder.recycler.setRecycledViewPool(sharedPool);
            // A row that leaves the screen gives its cards to the pool at once.
            holder.layoutManager.setRecycleChildrenOnDetach(true);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreRowViewHolder holder, int position) {
        RowData row = rows.get(position);
        holder.title.setText(row.getTitle());
        holder.recycler.setAdapter(new PoolAdapter(row.getMovies(), counter, cardWidthPx));
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
