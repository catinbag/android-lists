package org.demo.lists.scrollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

/** NestedScrollView inside NestedScrollView: two scrolls on one screen. */
public class NestedMode implements DemoMode {

    @Override
    public String title() {
        return "Nested";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_nested, parent, false);
        LinearLayout container = view.findViewById(R.id.nested_container);

        for (Movie movie : new MovieGenerator().generate(50)) {
            View row = inflater.inflate(R.layout.item_movie, container, false);
            ((TextView) row.findViewById(R.id.title)).setText(movie.toString());
            ((TextView) row.findViewById(R.id.details)).setText("id " + movie.getId());
            container.addView(row);
        }
        return view;
    }
}
