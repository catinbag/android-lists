package org.demo.lists.rvinteraction;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotifyAllMode extends MistakeMode {
    private static final long SLOW_MS = 1000;

    private EditableMovieAdapter adapter;

    @Override
    public String title() {
        return "notifyDataSetChanged";
    }

    @Override
    protected String explanation() {
        return "The row is removed, but RecyclerView is not told which one. "
                + "It binds all rows again and plays no animation.";
    }

    @Override
    protected String actionLabel() {
        return "Remove first";
    }

    @Override
    protected void build(boolean fixed) {
        RecyclerView recycler = newRecycler();
        recycler.setLayoutManager(new LinearLayoutManager(context));
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setRemoveDuration(SLOW_MS);
        animator.setMoveDuration(SLOW_MS);
        recycler.setItemAnimator(animator);
        adapter = new EditableMovieAdapter(sampleMovies(30));
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onAction(boolean fixed) {
        if (adapter.getItemCount() == 0) return;
        if (fixed) {
            adapter.remove(0);
            result.setText("notifyItemRemoved(0)");
        } else {
            adapter.removeAndNotifyAll(0);
            result.setText("notifyDataSetChanged()");
        }
    }
}
