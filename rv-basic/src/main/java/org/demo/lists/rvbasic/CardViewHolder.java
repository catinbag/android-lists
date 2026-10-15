package org.demo.lists.rvbasic;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

/** A card with a description. The description length changes the card height. */
public class CardViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView details;
    private final TextView description;

    public CardViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        details = itemView.findViewById(R.id.details);
        description = itemView.findViewById(R.id.description);
    }

    public void bind(Movie movie) {
        title.setText(movie.toString());
        details.setText(movie.getGenre().getTitle() + " · " + movie.getRating());
        description.setText(movie.getDescription());
    }
}
