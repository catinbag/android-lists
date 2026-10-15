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

/** Choice mode: ListView keeps the checked positions, not the rows. */
public class CheckedMode implements DemoMode {
    private static final int COUNT = 200;

    @Override
    public String title() {
        return "Checked";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_list, parent, false);
        TextView stats = view.findViewById(R.id.stats);
        stats.setText("checked=0");

        List<Movie> movies = new MovieGenerator().generate(COUNT);
        ListView list = view.findViewById(R.id.list);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setAdapter(new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_checked, movies));
        list.setOnItemClickListener((adapterView, row, position, id) ->
                stats.setText("checked=" + list.getCheckedItemCount()));
        return view;
    }
}
