package org.demo.lists.core.simulator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ListSimulatorTest {
    private static final int ITEMS = 1000;
    private static final int WINDOW = 8;

    private static ListSimulator scrollToEndByOne(ReuseStrategy strategy) {
        ListSimulator simulator = new ListSimulator(ITEMS, WINDOW, strategy);
        for (int i = 0; i < ITEMS - WINDOW; i++) {
            simulator.scrollBy(1);
        }
        return simulator;
    }

    @Test
    public void allAtOnceCreatesEveryRow() {
        ListSimulator simulator = scrollToEndByOne(ReuseStrategy.ALL_AT_ONCE);
        assertEquals(1000, simulator.getCreatedCount());
        assertEquals(1000, simulator.getBoundCount());
        assertEquals(1000, simulator.getPeakAliveCount());
    }

    @Test
    public void noReuseCreatesRowForEveryBind() {
        ListSimulator simulator = scrollToEndByOne(ReuseStrategy.NO_REUSE);
        assertEquals(1000, simulator.getCreatedCount());
        assertEquals(1000, simulator.getBoundCount());
        assertEquals(8, simulator.getPeakAliveCount());
    }

    @Test
    public void reuseCreatesOnlyWindowRows() {
        ListSimulator simulator = scrollToEndByOne(ReuseStrategy.REUSE);
        assertEquals(8, simulator.getCreatedCount());
        assertEquals(1000, simulator.getBoundCount());
        assertEquals(8, simulator.getPeakAliveCount());
    }

    @Test
    public void reuseJumpCreatesNoNewRows() {
        ListSimulator simulator = new ListSimulator(ITEMS, WINDOW, ReuseStrategy.REUSE);
        simulator.scrollTo(500);
        assertEquals(8, simulator.getCreatedCount());
        assertEquals(16, simulator.getBoundCount());
        assertEquals(8, simulator.getAliveCount());
    }

    @Test
    public void scrollToStaysInRange() {
        ListSimulator simulator = new ListSimulator(ITEMS, WINDOW, ReuseStrategy.REUSE);
        simulator.scrollTo(5000);
        assertEquals(992, simulator.getFirstVisible());
        simulator.scrollTo(-5);
        assertEquals(0, simulator.getFirstVisible());
    }

    @Test
    public void windowLargerThanDataUsesDataSize() {
        ListSimulator simulator = new ListSimulator(5, WINDOW, ReuseStrategy.REUSE);
        simulator.scrollBy(10);
        assertEquals(0, simulator.getFirstVisible());
        assertEquals(5, simulator.getCreatedCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroItemsThrows() {
        new ListSimulator(0, WINDOW, ReuseStrategy.REUSE);
    }
}
