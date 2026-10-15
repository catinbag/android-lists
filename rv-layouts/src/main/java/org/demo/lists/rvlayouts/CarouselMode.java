package org.demo.lists.rvlayouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** A horizontal list. A SnapHelper stops the scroll on a card. */
public class CarouselMode implements DemoMode {
    private static final int COUNT = 200;

    private final List<Movie> movies = new MovieGenerator().generate(COUNT);

    private RecyclerView recycler;
    private SnapHelper snapHelper;

    @Override
    public String title() {
        return "Carousel";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_carousel, parent, false);
        float density = inflater.getContext().getResources().getDisplayMetrics().density;
        recycler = view.findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(
                inflater.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new CardAdapter(movies, Math.round(260 * density)));
        recycler.addItemDecoration(new SpacingDecoration(Math.round(8 * density)));

        RadioGroup group = view.findViewById(R.id.snap_group);
        group.setOnCheckedChangeListener((g, checkedId) -> {
            // Two helpers on one list throw an exception: detach the old one first.
            if (snapHelper != null) {
                snapHelper.attachToRecyclerView(null);
            }
            if (checkedId == R.id.radio_linear_snap) {
                snapHelper = new LinearSnapHelper();
            } else if (checkedId == R.id.radio_pager_snap) {
                snapHelper = new PagerSnapHelper();
            } else {
                snapHelper = null;
            }
            if (snapHelper != null) {
                snapHelper.attachToRecyclerView(recycler);
            }
        });
        return view;
    }
}
