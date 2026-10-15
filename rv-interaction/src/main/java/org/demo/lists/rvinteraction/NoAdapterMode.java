package org.demo.lists.rvinteraction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoAdapterMode extends MistakeMode {

    @Override
    public String title() {
        return "No adapter";
    }

    @Override
    protected String explanation() {
        return "The list has a LayoutManager, but setAdapter is not called. There is nothing to show.";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        if (fixed) {
            recycler.setAdapter(new EditableMovieAdapter(sampleMovies(30)));
        }
        result.setText(fixed ? "Adapter: set" : "Adapter: missing");
    }
}
