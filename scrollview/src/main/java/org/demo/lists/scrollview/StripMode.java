package org.demo.lists.scrollview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.demo.lists.core.model.Genre;

/** HorizontalScrollView: the same idea as ScrollView, the other axis. */
public class StripMode implements DemoMode {

    @Override
    public String title() {
        return "Strip";
    }

    @Override
    public View create(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.mode_strip, parent, false);

        LinearLayout genres = view.findViewById(R.id.strip_genres);
        for (Genre genre : Genre.values()) {
            addChip(inflater, genres, genre.getTitle());
        }

        LinearLayout years = view.findViewById(R.id.strip_years);
        for (int year = 1950; year <= 2020; year += 5) {
            addChip(inflater, years, String.valueOf(year));
        }
        return view;
    }

    private void addChip(LayoutInflater inflater, LinearLayout strip, String text) {
        TextView chip = (TextView) inflater.inflate(R.layout.item_chip, strip, false);
        chip.setText(text);
        strip.addView(chip);
    }
}
