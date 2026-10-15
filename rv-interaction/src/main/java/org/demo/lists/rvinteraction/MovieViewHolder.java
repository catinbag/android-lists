package org.demo.lists.rvinteraction;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView details;

    public MovieViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        details = itemView.findViewById(R.id.details);
    }

    public void bind(Movie movie) {
        fill(title, details, movie);
    }

    public static void fill(TextView title, TextView details, Movie movie) {
        title.setText(movie.toString());
        details.setText("id " + movie.getId() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
    }
}
