package org.demo.lists.rvbasic;

import android.util.Log;

import androidx.annotation.NonNull;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** Logs every row that goes back to the pool. */
public class RecycleLogAdapter extends MovieAdapter {
    private static final String TAG = "RvBasic";

    private int recycledCount;

    public RecycleLogAdapter(List<Movie> movies, CallCounter counter) {
        super(movies, counter);
    }

    @Override
    public void onViewRecycled(@NonNull MovieViewHolder holder) {
        super.onViewRecycled(holder);
        recycledCount++;
        Log.d(TAG, "recycled, layout position " + holder.getLayoutPosition());
    }

    public int getRecycledCount() {
        return recycledCount;
    }
}
