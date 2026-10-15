package org.demo.lists.rvmistakes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StateInViewMode extends MistakeMode {

    @Override
    public String title() {
        return "State in View";
    }

    @Override
    protected String explanation() {
        return "A row is reused for other data. Check a few rows, scroll down and up.";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(new CheckAdapter(sampleMovies(100), fixed));
        result.setText(fixed ? "State in data: ids in a Set" : "State in View: only the CheckBox");
    }
}
