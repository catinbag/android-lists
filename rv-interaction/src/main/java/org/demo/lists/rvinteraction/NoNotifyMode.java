package org.demo.lists.rvinteraction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoNotifyMode extends MistakeMode {
    private EditableMovieAdapter adapter;

    @Override
    public String title() {
        return "No notify";
    }

    @Override
    protected String explanation() {
        return "The data changes, but notify is not called. "
                + "The screen keeps the old text until the row is bound again.";
    }

    @Override
    protected String actionLabel() {
        return "Change first";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        adapter = new EditableMovieAdapter(sampleMovies(30));
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onAction(boolean fixed) {
        if (adapter.getItemCount() == 0) return;
        if (fixed) {
            adapter.change(0);
        } else {
            adapter.changeSilently(0);
        }
        result.setText("data: " + adapter.getMovie(0)
                + (fixed ? "  (notifyItemChanged called)" : "  (RecyclerView is not told)"));
    }
}
