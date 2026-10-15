package org.demo.lists.core.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CallCounterTest {

    @Test
    public void countersWorkIndependently() {
        CallCounter counter = new CallCounter();
        counter.onInflate();
        counter.onFindViewById();
        counter.onFindViewById();
        counter.onBind();
        counter.onBind();
        counter.onBind();
        assertEquals(1, counter.getInflateCount());
        assertEquals(2, counter.getFindViewByIdCount());
        assertEquals(3, counter.getBindCount());
    }

    @Test
    public void resetSetsAllCountersToZero() {
        CallCounter counter = new CallCounter();
        counter.onInflate();
        counter.onFindViewById();
        counter.onBind();
        counter.reset();
        assertEquals("inflate=0, findViewById=0, bind=0", counter.toString());
    }

    @Test
    public void toStringShowsAllCounters() {
        CallCounter counter = new CallCounter();
        counter.onInflate();
        counter.onBind();
        counter.onBind();
        assertEquals("inflate=1, findViewById=0, bind=2", counter.toString());
    }
}
