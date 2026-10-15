package org.demo.lists.rvbasic;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

/** Keeps the views of one row. findViewById runs once, when the row is created. */
public class MovieViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView details;

    public MovieViewHolder(View itemView, CallCounter counter) {
        super(itemView);
        counter.onFindViewById();
        title = itemView.findViewById(R.id.title);
        counter.onFindViewById();
        details = itemView.findViewById(R.id.details);
    }

    public void bind(Movie movie) {
        title.setText(movie.toString());
        details.setText("id " + movie.getId() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
    }
}
