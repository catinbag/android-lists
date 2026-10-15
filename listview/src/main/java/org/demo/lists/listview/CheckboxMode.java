package org.demo.lists.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** A row is reused. A flag kept only in the CheckBox moves to other rows. */
public class CheckboxMode implements DemoMode {
    private final List<Movie> movies = new MovieGenerator().generate(100);

    private LayoutInflater inflater;
    private ListView list;
    private TextView stats;

    @Override
    public String title() {
        return "Checkbox";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.mode_checkbox, parent, false);
        list = view.findViewById(R.id.list);
        stats = view.findViewById(R.id.stats);

        RadioGroup group = view.findViewById(R.id.state_group);
        group.setOnCheckedChangeListener((g, checkedId) -> show(checkedId == R.id.radio_data));
        show(false);
        return view;
    }

    private void show(boolean stateInData) {
        stats.setText(stateInData
                ? "State in data: checked ids are kept in a Set."
                : "State in View: only the CheckBox knows. Scroll down and up.");
        list.setAdapter(new CheckAdapter(inflater.getContext(), movies, stateInData));
    }

    private static class CheckAdapter extends ArrayAdapter<Movie> {
        private final LayoutInflater inflater;
        private final boolean stateInData;
        private final Set<Long> checkedIds = new HashSet<>();

        CheckAdapter(Context context, List<Movie> movies, boolean stateInData) {
            super(context, R.layout.item_check, movies);
            this.inflater = LayoutInflater.from(context);
            this.stateInData = stateInData;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView != null
                    ? convertView
                    : inflater.inflate(R.layout.item_check, parent, false);
            Movie movie = getItem(position);
            ((TextView) row.findViewById(R.id.title)).setText(movie.toString());

            if (stateInData) {
                CheckBox check = row.findViewById(R.id.check);
                // The listener must not fire while we set the state.
                check.setOnCheckedChangeListener(null);
                check.setChecked(checkedIds.contains(movie.getId()));
                check.setOnCheckedChangeListener((button, isChecked) -> {
                    if (isChecked) {
                        checkedIds.add(movie.getId());
                    } else {
                        checkedIds.remove(movie.getId());
                    }
                });
            }
            return row;
        }
    }
}
