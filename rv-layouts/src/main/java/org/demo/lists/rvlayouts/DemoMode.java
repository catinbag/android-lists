package org.demo.lists.rvlayouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** One demo on the screen. MainActivity shows one mode at a time. */
public interface DemoMode {
    String title();

    View create(LayoutInflater inflater, ViewGroup parent);
}
