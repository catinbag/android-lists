package org.demo.lists.rvanimations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;

/** Point notify calls start animations. notifyDataSetChanged does not. */
public class AnimationsMode implements DemoMode {
    private static final int COUNT = 30;
    private static final long SLOW_MS = 1000;

    private MovieGenerator generator;
    private long nextId;
    private EditableMovieAdapter adapter;
    private RecyclerView recycler;
    private CheckBox animator;
    private CheckBox slow;

    @Override
    public String title() {
        return "Animations";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_animations, parent, false);
        generator = new MovieGenerator();
        adapter = new EditableMovieAdapter(generator.generate(COUNT));
        nextId = COUNT + 1;

        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recycler.setAdapter(adapter);

        animator = view.findViewById(R.id.animator);
        slow = view.findViewById(R.id.slow);
        animator.setOnCheckedChangeListener((button, checked) -> applyAnimator());
        slow.setOnCheckedChangeListener((button, checked) -> applyAnimator());
        applyAnimator();

        view.findViewById(R.id.insert).setOnClickListener(v -> {
            adapter.insert(0, generator.generateOne(nextId++));
            recycler.scrollToPosition(0);
        });
        view.findViewById(R.id.remove).setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) adapter.remove(0);
        });
        view.findViewById(R.id.move).setOnClickListener(v -> {
            if (adapter.getItemCount() > 3) adapter.move(0, 3);
        });
        view.findViewById(R.id.change).setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) adapter.change(0);
        });
        view.findViewById(R.id.remove_all).setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) adapter.removeAndNotifyAll(0);
        });
        return view;
    }

    private void applyAnimator() {
        if (!animator.isChecked()) {
            recycler.setItemAnimator(null);
            return;
        }
        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        if (slow.isChecked()) {
            itemAnimator.setAddDuration(SLOW_MS);
            itemAnimator.setRemoveDuration(SLOW_MS);
            itemAnimator.setMoveDuration(SLOW_MS);
            itemAnimator.setChangeDuration(SLOW_MS);
        }
        recycler.setItemAnimator(itemAnimator);
    }
}
