package org.demo.lists.rvinteraction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

/** The gesture only says what the user wants. The code changes the data. */
public class MovieTouchCallback extends ItemTouchHelper.SimpleCallback {

    public interface Listener {
        void onMoved(Movie movie, int from, int to);

        void onRemoved(Movie movie);
    }

    private final EditableMovieAdapter adapter;
    private final Listener listener;

    public MovieTouchCallback(EditableMovieAdapter adapter, Listener listener) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.listener = listener;
    }

    /** Drag: called when the dragged row passes over another row. */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder holder,
                          @NonNull RecyclerView.ViewHolder target) {
        int from = holder.getBindingAdapterPosition();
        int to = target.getBindingAdapterPosition();
        if (from == RecyclerView.NO_POSITION || to == RecyclerView.NO_POSITION) {
            return false;
        }
        Movie movie = adapter.getMovie(from);
        adapter.move(from, to);
        listener.onMoved(movie, from, to);
        return true;
    }

    /** Swipe: the row is already moved away. Remove its data. */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int direction) {
        int position = holder.getBindingAdapterPosition();
        if (position == RecyclerView.NO_POSITION) return;
        listener.onRemoved(adapter.remove(position));
    }
}
