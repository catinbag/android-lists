package org.demo.lists.rvmistakes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** A common mistake. The "Fix it" check box switches to the correct code. */
public abstract class MistakeMode implements DemoMode {
    protected Context context;
    protected FrameLayout host;
    protected TextView result;
    protected Button action;

    /** What is wrong, in one or two sentences. */
    protected abstract String explanation();

    /** Builds the list for the broken or the fixed code. */
    protected abstract void build(boolean fixed);

    /** The label of the action button, or null if the mode has no action. */
    protected String actionLabel() {
        return null;
    }

    protected void onAction(boolean fixed) {
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_mistake, parent, false);
        context = inflater.getContext();
        host = view.findViewById(R.id.host);
        result = view.findViewById(R.id.result);
        action = view.findViewById(R.id.action);
        CheckBox fix = view.findViewById(R.id.fix);

        ((TextView) view.findViewById(R.id.explanation)).setText(explanation());
        String label = actionLabel();
        if (label != null) {
            action.setVisibility(View.VISIBLE);
            action.setText(label);
            action.setOnClickListener(v -> onAction(fix.isChecked()));
        }
        fix.setOnCheckedChangeListener((button, checked) -> rebuild(checked));
        rebuild(false);
        return view;
    }

    private void rebuild(boolean fixed) {
        result.setText("");
        build(fixed);
    }

    /** A new empty RecyclerView in the host. Without a LayoutManager and an adapter. */
    protected RecyclerView newRecycler() {
        host.removeAllViews();
        RecyclerView recycler = new RecyclerView(context);
        recycler.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        host.addView(recycler);
        return recycler;
    }

    protected static List<Movie> sampleMovies(int count) {
        return new MovieGenerator().generate(count);
    }
}
