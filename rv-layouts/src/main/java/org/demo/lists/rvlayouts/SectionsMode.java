package org.demo.lists.rvlayouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.grouping.MovieGrouper;
import org.demo.lists.core.model.Genre;
import org.demo.lists.core.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Genre headers and movies in one list. Headers take the full width in a grid. */
public class SectionsMode implements DemoMode {
    private static final int COUNT = 200;
    private static final int SPAN_COUNT = 2;
    private static final String[] LAYOUTS = {"Linear", "Grid 2", "Staggered 2"};

    private RecyclerView recycler;
    private CheckBox spanLookup;
    private List<ListRow> rows;
    private float density;
    private int selected;

    @Override
    public String title() {
        return "Sections";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_sections, parent, false);
        Context context = inflater.getContext();
        density = context.getResources().getDisplayMetrics().density;
        recycler = view.findViewById(R.id.recycler);
        spanLookup = view.findViewById(R.id.span_lookup);
        rows = buildRows(new MovieGenerator().generate(COUNT));

        Spinner spinner = view.findViewById(R.id.sections_spinner);
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
        spanLookup.setOnCheckedChangeListener((button, checked) -> apply(context));
        return view;
    }

    private List<ListRow> buildRows(List<Movie> movies) {
        List<ListRow> result = new ArrayList<>();
        for (Map.Entry<Genre, List<Movie>> group : MovieGrouper.byGenre(movies).entrySet()) {
            result.add(new HeaderRow(group.getKey().getTitle()));
            for (Movie movie : group.getValue()) {
                result.add(new MovieRow(movie));
            }
        }
        return result;
    }

    private void apply(Context context) {
        SectionAdapter adapter = new SectionAdapter(rows);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(createLayoutManager(context, adapter));

        while (recycler.getItemDecorationCount() > 0) {
            recycler.removeItemDecorationAt(0);
        }
        if (selected != 0) {
            recycler.addItemDecoration(new SpacingDecoration(Math.round(4 * density)));
        }
    }

    private RecyclerView.LayoutManager createLayoutManager(Context context, SectionAdapter adapter) {
        switch (selected) {
            case 1:
                GridLayoutManager grid = new GridLayoutManager(context, SPAN_COUNT);
                if (spanLookup.isChecked()) {
                    grid.setSpanSizeLookup(new GenreSpanLookup(adapter, SPAN_COUNT));
                }
                return grid;
            case 2:
                return new StaggeredGridLayoutManager(
                        SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
            default:
                return new LinearLayoutManager(context);
        }
    }
}
