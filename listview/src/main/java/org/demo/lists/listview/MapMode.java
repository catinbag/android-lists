package org.demo.lists.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** SimpleAdapter binds Map values to views by id. No own adapter class. */
public class MapMode implements DemoMode {
    private static final int COUNT = 200;

    private static final String TITLE = "title";
    private static final String DETAILS = "details";

    @Override
    public String title() {
        return "Map";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_list, parent, false);
        ((TextView) view.findViewById(R.id.stats))
                .setText("SimpleAdapter + simple_list_item_2, data in Map");

        List<Map<String, String>> data = new ArrayList<>();
        for (Movie movie : new MovieGenerator().generate(COUNT)) {
            Map<String, String> row = new HashMap<>();
            row.put(TITLE, movie.toString());
            row.put(DETAILS, "id " + movie.getId() + " · " + movie.getGenre().getTitle());
            data.add(row);
        }

        ListView list = view.findViewById(R.id.list);
        list.setAdapter(new SimpleAdapter(
                inflater.getContext(),
                data,
                android.R.layout.simple_list_item_2,
                new String[]{TITLE, DETAILS},
                new int[]{android.R.id.text1, android.R.id.text2}));
        return view;
    }
}
