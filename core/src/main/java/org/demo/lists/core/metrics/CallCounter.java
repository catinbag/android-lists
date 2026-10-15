package org.demo.lists.core.metrics;

/** Counts the expensive calls of a list screen. */
public class CallCounter {
    private int inflateCount;
    private int findViewByIdCount;
    private int bindCount;

    public void onInflate() { inflateCount++; }
    public void onFindViewById() { findViewByIdCount++; }
    public void onBind() { bindCount++; }

    public void reset() {
        inflateCount = 0;
        findViewByIdCount = 0;
        bindCount = 0;
    }

    public int getInflateCount() { return inflateCount; }
    public int getFindViewByIdCount() { return findViewByIdCount; }
    public int getBindCount() { return bindCount; }

    @Override
    public String toString() {
        return "inflate=" + inflateCount
                + ", findViewById=" + findViewByIdCount
                + ", bind=" + bindCount;
    }
}
