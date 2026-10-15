package org.demo.lists.rvinteraction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** The exception would come out of the layout and close the app. So we call by hand. */
public class AttachToRootMode extends MistakeMode {
    private RecyclerView recycler;
    private RecyclerView.Adapter<?> adapter;

    @Override
    public String title() {
        return "attachToRoot = true";
    }

    @Override
    protected String explanation() {
        return "The adapter calls inflate with attachToRoot = true. "
                + "We call createViewHolder by hand to see the exception.";
    }

    @Override
    protected String actionLabel() {
        return "Create a ViewHolder by hand";
    }

    @Override
    protected void build(boolean fixed) {
        recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        if (fixed) {
            adapter = new EditableMovieAdapter(sampleMovies(30));
            recycler.setAdapter(adapter);
        } else {
            // Not set on the list: RecyclerView would call it in a layout.
            adapter = new AttachTrueAdapter(sampleMovies(30));
        }
    }

    @Override
    protected void onAction(boolean fixed) {
        try {
            adapter.createViewHolder(recycler, 0);
            result.setText("OK: the ViewHolder is created");
        } catch (RuntimeException e) {
            result.setText(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
