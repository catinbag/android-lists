package org.demo.lists.rvmistakes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.model.Movie;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** The mistake: with stateInData = false only the CheckBox knows if it is checked. */
public class CheckAdapter extends RecyclerView.Adapter<CheckViewHolder> {
    private final List<Movie> movies;
    private final boolean stateInData;
    private final Set<Long> checkedIds = new HashSet<>();

    public CheckAdapter(List<Movie> movies, boolean stateInData) {
        this.movies = movies;
        this.stateInData = stateInData;
    }

    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.toString());

        if (stateInData) {
            // The listener must not fire while we set the state.
            holder.check.setOnCheckedChangeListener(null);
            holder.check.setChecked(checkedIds.contains(movie.getId()));
            holder.check.setOnCheckedChangeListener((button, isChecked) -> {
                if (isChecked) {
                    checkedIds.add(movie.getId());
                } else {
                    checkedIds.remove(movie.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
