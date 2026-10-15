package org.demo.lists.rvinteraction;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CheckViewHolder extends RecyclerView.ViewHolder {
    final CheckBox check;
    final TextView title;

    public CheckViewHolder(View itemView) {
        super(itemView);
        check = itemView.findViewById(R.id.check);
        title = itemView.findViewById(R.id.title);
    }
}
