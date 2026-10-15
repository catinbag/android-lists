package org.demo.lists.rvmistakes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TallItemMode extends MistakeMode {

    @Override
    public String title() {
        return "match_parent height";
    }

    @Override
    protected String explanation() {
        return "The row layout has layout_height = match_parent. One row fills the whole list.";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(new EditableMovieAdapter(sampleMovies(30),
                fixed ? R.layout.item_movie : R.layout.item_tall));
        result.setText(fixed ? "layout_height: wrap_content" : "layout_height: match_parent");
    }
}
