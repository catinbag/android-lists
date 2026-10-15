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
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** Two lists with the same rows. With a shared pool the second list can use rows of the first. */
public class TwoListsMode implements DemoMode {
    private static final int COUNT = 200;
    private static final int POOL_SIZE = 20;

    private final List<Movie> movies = new MovieGenerator().generate(COUNT);
    private final CallCounter counterFirst = new CallCounter();
    private final CallCounter counterSecond = new CallCounter();
    private final Runnable statsUpdater = this::showStats;

    private Context context;
    private RecyclerView first;
    private RecyclerView second;
    private TextView stats;
    private CheckBox sharedPool;

    @Override
    public String title() {
        return "Two lists";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_two_lists, parent, false);
        context = inflater.getContext();
        first = view.findViewById(R.id.first);
        second = view.findViewById(R.id.second);
        stats = view.findViewById(R.id.stats);
        sharedPool = view.findViewById(R.id.shared_pool);

        first.setLayoutManager(new LinearLayoutManager(context));
        second.setLayoutManager(new LinearLayoutManager(context));
        first.addOnScrollListener(new StatsScrollListener());
        second.addOnScrollListener(new StatsScrollListener());

        sharedPool.setOnCheckedChangeListener((button, checked) -> restart());
        view.findViewById(R.id.restart).setOnClickListener(v -> restart());
        view.findViewById(R.id.scroll_first).setOnClickListener(
                v -> first.smoothScrollToPosition(COUNT - 1));
        view.findViewById(R.id.scroll_second).setOnClickListener(
                v -> second.smoothScrollToPosition(COUNT - 1));

        restart();
        return view;
    }

    private void restart() {
        counterFirst.reset();
        counterSecond.reset();

        // The pool is set before the adapter. A null pool gives the list its own new pool.
        RecyclerView.RecycledViewPool pool = null;
        if (sharedPool.isChecked()) {
            pool = new RecyclerView.RecycledViewPool();
            pool.setMaxRecycledViews(0, POOL_SIZE);
        }
        first.setRecycledViewPool(pool);
        second.setRecycledViewPool(pool);

        first.setAdapter(new PoolAdapter(movies, counterFirst, 0));
        second.setAdapter(new PoolAdapter(movies, counterSecond, 0));
        first.scrollToPosition(0);
        second.scrollToPosition(0);
        first.post(statsUpdater);
    }

    private void showStats() {
        int total = counterFirst.getInflateCount() + counterSecond.getInflateCount();
        stats.setText((sharedPool.isChecked() ? "shared pool" : "own pools")
                + ", inflate total=" + total
                + "\nfirst:  " + counterFirst + "\nsecond: " + counterSecond);
    }

    private class StatsScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
            // Update the text later: setText during a layout would request a layout.
            rv.removeCallbacks(statsUpdater);
            rv.postDelayed(statsUpdater, 200);
        }
    }
}
