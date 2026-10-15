package org.demo.lists.scrollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** A list of movies built by hand: inflate, bind, addView for every movie. */
public class ListMode implements DemoMode {
    private final CallCounter counter = new CallCounter();

    private LayoutInflater inflater;
    private LinearLayout container;
    private ScrollView scroll;
    private TextView stats;
    private InflateMode inflateMode = InflateMode.ATTACH_FALSE;
    private int count = 10;

    @Override
    public String title() {
        return "List";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.mode_list, parent, false);
        container = view.findViewById(R.id.container);
        scroll = view.findViewById(R.id.scroll);
        stats = view.findViewById(R.id.stats);

        RadioGroup group = view.findViewById(R.id.inflate_group);
        group.setOnCheckedChangeListener((g, checkedId) -> {
            if (checkedId == R.id.radio_attach_true) {
                inflateMode = InflateMode.ATTACH_TRUE;
            } else if (checkedId == R.id.radio_root_null) {
                inflateMode = InflateMode.ROOT_NULL;
            } else {
                inflateMode = InflateMode.ATTACH_FALSE;
            }
            fill();
        });

        view.findViewById(R.id.count_10).setOnClickListener(v -> { count = 10; fill(); });
        view.findViewById(R.id.count_200).setOnClickListener(v -> { count = 200; fill(); });
        view.findViewById(R.id.count_1000).setOnClickListener(v -> { count = 1000; fill(); });

        view.findViewById(R.id.scroll_top).setOnClickListener(v -> scroll.scrollTo(0, 0));
        view.findViewById(R.id.scroll_end).setOnClickListener(
                v -> scroll.scrollTo(0, container.getHeight()));
        view.findViewById(R.id.scroll_down).setOnClickListener(v -> scroll.scrollBy(0, 500));
        view.findViewById(R.id.scroll_up).setOnClickListener(v -> scroll.scrollBy(0, -500));

        fill();
        return view;
    }

    private void fill() {
        container.removeAllViews();
        counter.reset();
        List<Movie> movies = new MovieGenerator().generate(count);

        long start = System.nanoTime();
        for (Movie movie : movies) {
            addRow(movie);
        }
        long millis = (System.nanoTime() - start) / 1_000_000;

        stats.setText(counter + "\nchildren=" + container.getChildCount()
                + ", build=" + millis + " ms (without measure and layout)");
    }

    private void addRow(Movie movie) {
        counter.onInflate();
        View row;
        switch (inflateMode) {
            case ROOT_NULL:
                row = inflater.inflate(R.layout.item_movie, null);
                break;
            case ATTACH_TRUE:
                // The result is the container, not the row.
                row = inflater.inflate(R.layout.item_movie, container, true);
                break;
            default:
                row = inflater.inflate(R.layout.item_movie, container, false);
                break;
        }

        bind(row, movie);

        if (inflateMode != InflateMode.ATTACH_TRUE) {
            container.addView(row);
        }
    }

    private void bind(View row, Movie movie) {
        counter.onFindViewById();
        TextView title = row.findViewById(R.id.title);
        counter.onFindViewById();
        TextView details = row.findViewById(R.id.details);

        title.setText(movie.toString());
        details.setText("id " + movie.getId() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
        counter.onBind();
    }
}
