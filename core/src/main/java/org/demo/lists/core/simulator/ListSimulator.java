package org.demo.lists.core.simulator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * A list without Android. A window of rows moves over the data.
 * The simulator counts how many rows it creates and binds.
 */
public class ListSimulator {
    private static final class Row {
        int position;
    }

    private final int itemCount;
    private final int windowSize;
    private final ReuseStrategy strategy;

    private final Map<Integer, Row> visible = new HashMap<>();
    private final Deque<Row> pool = new ArrayDeque<>();

    private int firstVisible;
    private int createdCount;
    private int boundCount;
    private int aliveCount;
    private int peakAliveCount;

    public ListSimulator(int itemCount, int windowSize, ReuseStrategy strategy) {
        if (itemCount <= 0 || windowSize <= 0) {
            throw new IllegalArgumentException("itemCount and windowSize must be positive");
        }
        this.itemCount = itemCount;
        this.windowSize = Math.min(windowSize, itemCount);
        this.strategy = strategy;

        if (strategy == ReuseStrategy.ALL_AT_ONCE) {
            for (int position = 0; position < itemCount; position++) {
                createRow();
                boundCount++;
            }
        } else {
            for (int position = 0; position < this.windowSize; position++) {
                enter(position);
            }
        }
    }

    public void scrollBy(int delta) {
        scrollTo(firstVisible + delta);
    }

    public void scrollTo(int target) {
        int newFirst = Math.max(0, Math.min(target, itemCount - windowSize));
        int oldFirst = firstVisible;
        firstVisible = newFirst;
        if (strategy == ReuseStrategy.ALL_AT_ONCE || newFirst == oldFirst) return;

        // Rows leave first, then rows enter. RecyclerView works in this order.
        for (int position = oldFirst; position < oldFirst + windowSize; position++) {
            if (position < newFirst || position >= newFirst + windowSize) {
                leave(position);
            }
        }
        for (int position = newFirst; position < newFirst + windowSize; position++) {
            if (position < oldFirst || position >= oldFirst + windowSize) {
                enter(position);
            }
        }
    }

    private void leave(int position) {
        Row row = visible.remove(position);
        if (strategy == ReuseStrategy.REUSE) {
            pool.push(row);
        } else {
            aliveCount--;
        }
    }

    private void enter(int position) {
        Row row = (strategy == ReuseStrategy.REUSE && !pool.isEmpty()) ? pool.pop() : createRow();
        row.position = position;
        boundCount++;
        visible.put(position, row);
    }

    private Row createRow() {
        createdCount++;
        aliveCount++;
        peakAliveCount = Math.max(peakAliveCount, aliveCount);
        return new Row();
    }

    public int getFirstVisible() { return firstVisible; }
    public int getCreatedCount() { return createdCount; }
    public int getBoundCount() { return boundCount; }
    /** Rows in memory now. Rows in the pool count too. */
    public int getAliveCount() { return aliveCount; }
    public int getPeakAliveCount() { return peakAliveCount; }
}
