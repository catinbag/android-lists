package org.demo.lists.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;
import org.demo.lists.listview.VariantAdapter.Variant;

import java.util.List;

/** Three getView variants on one screen. A new adapter and a reset counter for each. */
public class VariantsMode implements DemoMode {
    private static final int COUNT = 200;

    private final CallCounter counter = new CallCounter();
    private final List<Movie> movies = new MovieGenerator().generate(COUNT);
    private final Runnable statsUpdater = this::showStats;

    private LayoutInflater inflater;
    private ListView list;
    private TextView stats;
    private Variant variant = Variant.NO_CONVERT_VIEW;

    @Override
    public String title() {
        return "Variants";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.mode_variants, parent, false);
        list = view.findViewById(R.id.list);
        stats = view.findViewById(R.id.stats);

        RadioGroup group = view.findViewById(R.id.variant_group);
        group.setOnCheckedChangeListener((g, checkedId) -> {
            if (checkedId == R.id.radio_b) {
                variant = Variant.CONVERT_VIEW;
            } else if (checkedId == R.id.radio_c) {
                variant = Variant.VIEW_HOLDER;
            } else {
                variant = Variant.NO_CONVERT_VIEW;
            }
            restart(false);
        });
        view.findViewById(R.id.scroll_end).setOnClickListener(v -> restart(true));

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

        restart(false);
        return view;
    }

    private void restart(boolean scrollToEnd) {
        counter.reset();
        list.setAdapter(new VariantAdapter(inflater.getContext(), movies, counter, variant));
        if (scrollToEnd) {
            list.post(() -> list.smoothScrollToPosition(COUNT - 1));
        }
    }

    private void showStats() {
        stats.setText(variant + "\ngetView calls: " + counter.getBindCount() + "\n" + counter);
    }
}
