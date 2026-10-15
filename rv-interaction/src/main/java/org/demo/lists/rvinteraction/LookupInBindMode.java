package org.demo.lists.rvinteraction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.metrics.CallCounter;

public class LookupInBindMode extends MistakeMode {
    private static final int COUNT = 200;

    private final CallCounter counter = new CallCounter();
    private final Runnable statsUpdater = this::showStats;
    private RecyclerView recycler;
    private boolean fixed;

    @Override
    public String title() {
        return "findViewById in bind";
    }

    @Override
    protected String explanation() {
        return "onBindViewHolder searches the views again on every bind. "
                + "Compare the findViewById count after the scroll.";
    }

    @Override
    protected String actionLabel() {
        return "Restart and scroll to end";
    }

    @Override
    protected void build(boolean fixed) {
        this.fixed = fixed;
        recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                // Update the text later: setText during a layout would request a layout.
                rv.removeCallbacks(statsUpdater);
                rv.postDelayed(statsUpdater, 200);
            }
        });
        restart(false);
    }

    @Override
    protected void onAction(boolean fixed) {
        restart(true);
    }

    private void restart(boolean scrollToEnd) {
        counter.reset();
        recycler.setAdapter(new LookupAdapter(sampleMovies(COUNT), counter, !fixed));
        if (scrollToEnd) {
            recycler.post(() -> recycler.smoothScrollToPosition(COUNT - 1));
        }
    }

    private void showStats() {
        result.setText((fixed ? "fixed: " : "broken: ") + counter);
    }
}
