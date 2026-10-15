package org.demo.lists.rvlayouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** One adapter class, six layouts. Only the LayoutManager changes. */
public class LayoutsMode implements DemoMode {
    private static final int COUNT = 200;
    private static final String[] LAYOUTS = {
            "Linear vertical", "Linear horizontal", "Linear reverse",
            "Grid 2", "Grid 3", "Staggered 2"
    };

    private final List<Movie> movies = new MovieGenerator().generate(COUNT);

    private RecyclerView recycler;
    private CheckBox decoration;
    private float density;
    private int selected;

    @Override
    public String title() {
        return "Layouts";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_layouts, parent, false);
        Context context = inflater.getContext();
        density = context.getResources().getDisplayMetrics().density;
        recycler = view.findViewById(R.id.recycler);
        decoration = view.findViewById(R.id.decoration);

        Spinner spinner = view.findViewById(R.id.layout_spinner);
        spinner.setAdapter(new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, LAYOUTS));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int position, long id) {
                selected = position;
                apply(context);
            }

            @Override
            public void onNothingSelected(AdapterView<?> p) {
            }
        });
        decoration.setOnCheckedChangeListener((button, checked) -> apply(context));
        return view;
    }

    private void apply(Context context) {
        boolean horizontal = selected == 1;
        recycler.setAdapter(new CardAdapter(movies, horizontal ? dp(220) : 0));
        recycler.setLayoutManager(createLayoutManager(context));

        while (recycler.getItemDecorationCount() > 0) {
            recycler.removeItemDecorationAt(0);
        }
        if (decoration.isChecked()) {
            recycler.addItemDecoration(createDecoration(context));
        }
    }

    private RecyclerView.LayoutManager createLayoutManager(Context context) {
        switch (selected) {
            case 1:
                return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            case 2:
                return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
            case 3:
                return new GridLayoutManager(context, 2);
            case 4:
                return new GridLayoutManager(context, 3);
            case 5:
                return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            default:
                return new LinearLayoutManager(context);
        }
    }

    private RecyclerView.ItemDecoration createDecoration(Context context) {
        switch (selected) {
            case 0:
            case 2:
                return new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
            case 1:
                return new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
            default:
                return new SpacingDecoration(dp(8));
        }
    }

    private int dp(int value) {
        return Math.round(value * density);
    }
}
