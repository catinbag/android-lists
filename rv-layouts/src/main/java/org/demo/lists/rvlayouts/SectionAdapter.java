package org.demo.lists.rvlayouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

/** Two kinds of rows. The view type chooses the layout and the ViewHolder class. */
public class SectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ListRow> rows;

    public SectionAdapter(List<ListRow> rows) {
        this.rows = rows;
    }

    @Override
    public int getItemViewType(int position) {
        return rows.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ListRow.TYPE_HEADER) {
            return new HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false));
        }
        return new CardViewHolder(inflater.inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListRow row = rows.get(position);
        if (row.getType() == ListRow.TYPE_HEADER) {
            ((HeaderViewHolder) holder).bind(((HeaderRow) row).getTitle());
            makeFullSpan(holder.itemView);
        } else {
            ((CardViewHolder) holder).bind(((MovieRow) row).getMovie());
        }
    }

    // Staggered grid has no SpanSizeLookup. A header takes the full width by its own params.
    private void makeFullSpan(View itemView) {
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
