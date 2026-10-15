package org.demo.lists.rvinteraction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoLayoutManagerMode extends MistakeMode {

    @Override
    public String title() {
        return "No LayoutManager";
    }

    @Override
    protected String explanation() {
        return "The list has an adapter, but no LayoutManager. Nobody places the rows.";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setAdapter(new EditableMovieAdapter(sampleMovies(30)));
        if (fixed) {
            recycler.setLayoutManager(new LinearLayoutManager(context));
        }
        result.setText(fixed ? "LayoutManager: set" : "LayoutManager: missing");
    }
}
