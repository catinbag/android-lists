package org.demo.lists.rvbasic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** The same list as Basic. The adapter logs onViewRecycled (tag RvBasic). */
public class RecycleLogMode implements DemoMode {
    private static final int COUNT = 200;

    private final CallCounter counter = new CallCounter();
    private final List<Movie> movies = new MovieGenerator().generate(COUNT);
    private final Runnable statsUpdater = this::showStats;

    private RecyclerView recycler;
    private TextView stats;
    private RecycleLogAdapter adapter;

    @Override
    public String title() {
        return "Recycle log";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_basic, parent, false);
        recycler = view.findViewById(R.id.recycler);
        stats = view.findViewById(R.id.stats);

        recycler.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                // Update the text later: setText during a layout would request a layout.
                rv.removeCallbacks(statsUpdater);
                rv.postDelayed(statsUpdater, 200);
            }
        });
        view.findViewById(R.id.scroll_end).setOnClickListener(v -> restart(true));

        restart(false);
        return view;
    }

    private void restart(boolean scrollToEnd) {
        counter.reset();
        adapter = new RecycleLogAdapter(movies, counter);
        recycler.setAdapter(adapter);
        if (scrollToEnd) {
            recycler.post(() -> recycler.smoothScrollToPosition(COUNT - 1));
        }
    }

    private void showStats() {
        stats.setText("onViewRecycled calls: " + adapter.getRecycledCount()
                + "\nonBindViewHolder calls: " + counter.getBindCount() + "\n" + counter);
    }
}
