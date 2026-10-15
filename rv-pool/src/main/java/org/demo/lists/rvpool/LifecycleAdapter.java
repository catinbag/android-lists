package org.demo.lists.rvpool;

import android.util.Log;

import androidx.annotation.NonNull;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/** Logs when a row is attached, detached and recycled. */
public class LifecycleAdapter extends PoolAdapter {
    private static final String TAG = "RvPool";
    private static final int LAST_EVENTS = 8;

    private final Deque<String> events = new ArrayDeque<>();
    private int attachedCount;
    private int detachedCount;
    private int recycledCount;

    public LifecycleAdapter(List<Movie> movies, CallCounter counter) {
        super(movies, counter, 0);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull CardViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        attachedCount++;
        log("attached ", holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull CardViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        detachedCount++;
        log("detached ", holder);
    }

    @Override
    public void onViewRecycled(@NonNull CardViewHolder holder) {
        super.onViewRecycled(holder);
        recycledCount++;
        log("recycled ", holder);
    }

    private void log(String event, CardViewHolder holder) {
        String line = event + "position " + holder.getLayoutPosition();
        Log.d(TAG, line);
        events.addFirst(line);
        if (events.size() > LAST_EVENTS) {
            events.removeLast();
        }
    }

    public int getAttachedCount() { return attachedCount; }
    public int getDetachedCount() { return detachedCount; }
    public int getRecycledCount() { return recycledCount; }

    /** The last events, the newest first. */
    public String getEventsText() {
        StringBuilder text = new StringBuilder();
        for (String line : events) {
            text.append(line).append('\n');
        }
        return text.toString();
    }
}
