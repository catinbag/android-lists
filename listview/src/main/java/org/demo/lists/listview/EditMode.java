package org.demo.lists.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.demo.lists.core.data.InMemoryMovieRepository;
import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

/**
 * Add and remove. The repository and the adapter keep two separate lists:
 * a change in one does not reach the other, so we change both.
 */
public class EditMode implements DemoMode {

    @Override
    public String title() {
        return "Edit";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_edit, parent, false);
        TextView status = view.findViewById(R.id.status);

        MovieGenerator generator = new MovieGenerator();
        InMemoryMovieRepository repository = new InMemoryMovieRepository(generator.generate(30));
        ArrayAdapter<Movie> adapter = new ArrayAdapter<>(
                inflater.getContext(), android.R.layout.simple_list_item_1, repository.getAll());
        long[] nextId = {31};

        ListView list = view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener((adapterView, row, position, id) -> status.setText(
                "getCount=" + adapter.getCount() + ", clicked position=" + position
                        + ", id=" + id + ", " + adapter.getItem(position)));

        view.findViewById(R.id.add).setOnClickListener(v -> {
            Movie movie = generator.generateOne(nextId[0]++);
            repository.add(movie);
            adapter.add(movie); // ArrayAdapter calls notifyDataSetChanged itself
            status.setText("getCount=" + adapter.getCount() + ", added " + movie);
        });

        view.findViewById(R.id.remove_first).setOnClickListener(v -> {
            if (adapter.isEmpty()) return;
            Movie first = adapter.getItem(0);
            repository.remove(first.getId());
            adapter.remove(first);
            status.setText("getCount=" + adapter.getCount() + ", removed " + first);
        });

        status.setText("getCount=" + adapter.getCount());
        return view;
    }
}
