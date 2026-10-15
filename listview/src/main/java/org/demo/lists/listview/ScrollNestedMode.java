package org.demo.lists.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.demo.lists.core.generator.MovieGenerator;
import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;
import org.demo.lists.listview.VariantAdapter.Variant;

import java.util.List;

/** A common mistake: a ListView with wrap_content inside a ScrollView. */
public class ScrollNestedMode implements DemoMode {
    private static final int COUNT = 200;

    private final CallCounter counter = new CallCounter();

    @Override
    public String title() {
        return "List in ScrollView";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_list_in_scroll, parent, false);
        TextView stats = view.findViewById(R.id.stats);
        ListView list = view.findViewById(R.id.list);

        counter.reset();
        List<Movie> movies = new MovieGenerator().generate(COUNT);
        list.setAdapter(new VariantAdapter(
                inflater.getContext(), movies, counter, Variant.VIEW_HOLDER));

        view.findViewById(R.id.refresh).setOnClickListener(v -> stats.setText(
                "list height=" + list.getHeight() + " px, getView calls: "
                        + counter.getBindCount() + "\n" + counter));
        return view;
    }
}
