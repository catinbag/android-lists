package org.demo.lists.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** The shortest list: ArrayAdapter shows toString() of each movie. */
public class SimpleMode implements DemoMode {
    private static final int COUNT = 200;

    @Override
    public String title() {
        return "Simple";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_list, parent, false);
        ((TextView) view.findViewById(R.id.stats))
                .setText("ArrayAdapter + simple_list_item_1, 200 movies");

        List<Movie> movies = new MovieGenerator().generate(COUNT);
        ListView list = view.findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, movies));
        return view;
    }
}
