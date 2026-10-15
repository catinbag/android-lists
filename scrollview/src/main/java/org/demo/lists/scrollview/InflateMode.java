package org.demo.lists.scrollview;

/** The three ways to call LayoutInflater.inflate(resource, root, attachToRoot). */
public enum InflateMode {
    /** inflate(res, container, false): LayoutParams from the container, row not attached. */
    ATTACH_FALSE,
    /** inflate(res, container, true): the row is attached, the result is the container. */
    ATTACH_TRUE,
    /** inflate(res, null): no LayoutParams from the container. */
    ROOT_NULL
}
