package org.demo.lists.rvlayouts;

import androidx.recyclerview.widget.GridLayoutManager;

/** A header takes all columns. A movie takes one. */
public class GenreSpanLookup extends GridLayoutManager.SpanSizeLookup {
    private final SectionAdapter adapter;
    private final int spanCount;

    public GenreSpanLookup(SectionAdapter adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;
    }

    @Override
    public int getSpanSize(int position) {
        return adapter.getItemViewType(position) == ListRow.TYPE_HEADER ? spanCount : 1;
    }
}
