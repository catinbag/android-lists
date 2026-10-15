package org.demo.lists.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** Two fields in a row need our own getView. It uses convertView, but not ViewHolder. */
public class TwoLinesMode implements DemoMode {
    private static final int COUNT = 200;

    private final CallCounter counter = new CallCounter();
    private final Runnable statsUpdater = this::showStats;
    private TextView stats;

    @Override
    public String title() {
        return "Two lines";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_list, parent, false);
        stats = view.findViewById(R.id.stats);
        counter.reset();

        List<Movie> movies = new MovieGenerator().generate(COUNT);
        ListView list = view.findViewById(R.id.list);
        list.setAdapter(new MovieAdapter(inflater.getContext(), movies));
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView v, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView v, int first, int visible, int total) {
                // Update the text later: setText during a layout would request a layout.
                v.removeCallbacks(statsUpdater);
                v.postDelayed(statsUpdater, 200);
            }
        });
        return view;
    }

    private void showStats() {
        stats.setText("getView calls: " + counter.getBindCount() + "\n" + counter);
    }

    private class MovieAdapter extends ArrayAdapter<Movie> {
        MovieAdapter(Context context, List<Movie> movies) {
            super(context, android.R.layout.simple_list_item_2, movies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                counter.onInflate();
                row = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            counter.onFindViewById();
            TextView title = row.findViewById(android.R.id.text1);
            counter.onFindViewById();
            TextView details = row.findViewById(android.R.id.text2);

            Movie movie = getItem(position);
            title.setText(movie.toString());
            details.setText("id " + movie.getId() + " · " + movie.getGenre().getTitle()
                    + " · " + movie.getRating());
            counter.onBind();
            return row;
        }
    }
}
