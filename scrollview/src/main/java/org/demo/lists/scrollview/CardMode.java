package org.demo.lists.scrollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** A movie card: a few blocks of different kinds. A good case for ScrollView. */
public class CardMode implements DemoMode {

    @Override
    public String title() {
        return "Card";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_card, parent, false);
        List<Movie> movies = new MovieGenerator().generate(4);
        Movie movie = movies.get(0);

        ((TextView) view.findViewById(R.id.card_title)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.card_meta)).setText(
                movie.getYear() + " · " + movie.getGenre().getTitle());
        ((TextView) view.findViewById(R.id.card_rating)).setText("Rating: " + movie.getRating());
        ((TextView) view.findViewById(R.id.card_description)).setText(movie.getDescription());

        LinearLayout similar = view.findViewById(R.id.card_similar);
        for (Movie other : movies.subList(1, movies.size())) {
            View row = inflater.inflate(R.layout.item_movie, similar, false);
            ((TextView) row.findViewById(R.id.title)).setText(other.toString());
            ((TextView) row.findViewById(R.id.details)).setText(other.getDescription());
            similar.addView(row);
        }
        return view;
    }
}
