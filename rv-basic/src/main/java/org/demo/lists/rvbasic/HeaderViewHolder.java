package org.demo.lists.rvbasic;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.header_title);
    }

    public void bind(String text) {
        title.setText(text);
    }
}
