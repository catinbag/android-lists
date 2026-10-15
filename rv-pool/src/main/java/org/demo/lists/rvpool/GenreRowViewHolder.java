package org.demo.lists.rvpool;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** A row of the outer list. It owns an inner horizontal list. */
public class GenreRowViewHolder extends RecyclerView.ViewHolder {
    final TextView title;
    final RecyclerView recycler;
    final LinearLayoutManager layoutManager;

    public GenreRowViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.row_title);
        recycler = itemView.findViewById(R.id.row_recycler);
        layoutManager = new LinearLayoutManager(
                itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
    }
}
