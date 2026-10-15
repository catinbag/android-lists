package org.demo.lists.core.simulator;

public enum ReuseStrategy {
    /** Like ScrollView: every row is created at the start. */
    ALL_AT_ONCE,
    /** Like getView without convertView: a row that leaves is dropped. */
    NO_REUSE,
    /** Like convertView, ViewHolder, RecyclerView: a row that leaves goes to a pool. */
    REUSE
}
