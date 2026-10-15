package org.demo.lists.rvbasic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;
import org.demo.lists.rvbasic.ClickAdapter.ClickStyle;

import java.util.ArrayList;
import java.util.List;

/** Remove the first movie, then tap a row. Compare the two click styles. */
public class ClickMode implements DemoMode {
    private static final int COUNT = 200;

    private final List<Movie> source = new MovieGenerator().generate(COUNT);

    private RecyclerView recycler;
    private TextView status;
    private ClickAdapter adapter;
    private ClickStyle style = ClickStyle.IN_BIND;

    @Override
    public String title() {
        return "Click";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_click, parent, false);
        recycler = view.findViewById(R.id.recycler);
        status = view.findViewById(R.id.status);
        recycler.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        RadioGroup group = view.findViewById(R.id.style_group);
        group.setOnCheckedChangeListener((g, checkedId) -> {
            style = checkedId == R.id.radio_in_holder ? ClickStyle.IN_HOLDER : ClickStyle.IN_BIND;
            restart();
        });
        view.findViewById(R.id.remove_first).setOnClickListener(v -> {
            adapter.removeFirst();
            status.setText(style + ": first row removed. Tap a row.");
        });

        restart();
        return view;
    }

    // A fresh copy of the data for each style.
    private void restart() {
        adapter = new ClickAdapter(new ArrayList<>(source), new CallCounter(), style,
                (movie, position) -> status.setText(movie == null
                        ? style + ": position " + position + " is out of range"
                        : style + ": listener got " + movie + ", position " + position));
        recycler.setAdapter(adapter);
        status.setText(style + ": tap a row.");
    }
}
