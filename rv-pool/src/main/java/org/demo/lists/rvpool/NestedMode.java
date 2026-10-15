package org.demo.lists.rvpool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.grouping.MovieGrouper;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Horizontal lists inside a vertical list. All cards are of one type: one pool can serve them. */
public class NestedMode implements DemoMode {
    private static final int COUNT = 200;
    private static final int ROW_SIZE = 20;
    private static final int POOL_SIZE = 30;

    private final CallCounter counter = new CallCounter();
    private final Runnable statsUpdater = this::showStats;

    private Context context;
    private RecyclerView recycler;
    private TextView stats;
    private CheckBox sharedPool;
    private List<RowData> rows;
    private int cardWidthPx;

    @Override
    public String title() {
        return "Nested";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_nested, parent, false);
        context = inflater.getContext();
        cardWidthPx = Math.round(200 * context.getResources().getDisplayMetrics().density);
        recycler = view.findViewById(R.id.recycler);
        stats = view.findViewById(R.id.stats);
        sharedPool = view.findViewById(R.id.shared_pool);
        rows = buildRows(new MovieGenerator().generate(COUNT));

        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                // Update the text later: setText during a layout would request a layout.
                rv.removeCallbacks(statsUpdater);
                rv.postDelayed(statsUpdater, 200);
            }
        });
        sharedPool.setOnCheckedChangeListener((button, checked) -> restart());
        view.findViewById(R.id.refresh).setOnClickListener(v -> showStats());

        restart();
        return view;
    }

    private List<RowData> buildRows(List<Movie> movies) {
        List<RowData> result = new ArrayList<>();
        for (Map.Entry<Genre, List<Movie>> group : MovieGrouper.byGenre(movies).entrySet()) {
            List<Movie> all = group.getValue();
            int part = 1;
            for (int start = 0; start < all.size(); start += ROW_SIZE) {
                int end = Math.min(start + ROW_SIZE, all.size());
                result.add(new RowData(group.getKey().getTitle() + " " + part++,
                        all.subList(start, end)));
            }
        }
        return result;
    }

    private void restart() {
        counter.reset();
        RecyclerView.RecycledViewPool pool = null;
        if (sharedPool.isChecked()) {
            pool = new RecyclerView.RecycledViewPool();
            pool.setMaxRecycledViews(0, POOL_SIZE);
        }
        recycler.setAdapter(new GenreRowAdapter(rows, counter, pool, cardWidthPx));
        recycler.scrollToPosition(0);
        recycler.postDelayed(statsUpdater, 200);
    }

    private void showStats() {
        stats.setText((sharedPool.isChecked() ? "shared pool" : "own pools")
                + ", rows=" + rows.size() + "\ncards: " + counter);
    }
}
