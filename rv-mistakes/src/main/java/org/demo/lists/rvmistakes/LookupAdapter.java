package org.demo.lists.rvmistakes;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** Counts the lookups. The mistake: lookupInBind searches the views on every bind. */
public class LookupAdapter extends EditableMovieAdapter {
    private final CallCounter counter;
    private final boolean lookupInBind;

    public LookupAdapter(List<Movie> movies, CallCounter counter, boolean lookupInBind) {
        super(movies);
        this.counter = counter;
        this.lookupInBind = lookupInBind;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        counter.onInflate();
        counter.onFindViewById(); // two lookups in the ViewHolder constructor
        counter.onFindViewById();
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (lookupInBind) {
            counter.onFindViewById();
            TextView title = holder.itemView.findViewById(R.id.title);
            counter.onFindViewById();
            TextView details = holder.itemView.findViewById(R.id.details);
            MovieViewHolder.fill(title, details, movies.get(position));
        } else {
            super.onBindViewHolder(holder, position);
        }
        counter.onBind();
    }
}
