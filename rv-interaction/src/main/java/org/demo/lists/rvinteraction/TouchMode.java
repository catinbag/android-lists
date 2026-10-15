package org.demo.lists.rvinteraction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

/** Drag and swipe with ItemTouchHelper. */
public class TouchMode implements DemoMode {
    private static final int COUNT = 30;

    @Override
    public String title() {
        return "Touch";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_touch, parent, false);
        TextView status = view.findViewById(R.id.status);
        RecyclerView recycler = view.findViewById(R.id.recycler);

        EditableMovieAdapter adapter = new EditableMovieAdapter(new MovieGenerator().generate(COUNT));
        recycler.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recycler.setAdapter(adapter);

        new ItemTouchHelper(new MovieTouchCallback(adapter, new MovieTouchCallback.Listener() {
            @Override
            public void onMoved(Movie movie, int from, int to) {
                status.setText("moved " + movie + ": " + from + " -> " + to);
            }

            @Override
            public void onRemoved(Movie movie) {
                status.setText("removed " + movie);
            }
        })).attachToRecyclerView(recycler);
        return view;
    }
}
