package org.demo.lists.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.demo.lists.core.metrics.CallCounter;
import org.demo.lists.core.model.Movie;

import java.util.List;

/** The same row in three ways. Only the way to get the row and its views changes. */
public class VariantAdapter extends ArrayAdapter<Movie> {

    public enum Variant { NO_CONVERT_VIEW, CONVERT_VIEW, VIEW_HOLDER }

    private final LayoutInflater inflater;
    private final CallCounter counter;
    private final Variant variant;

    public VariantAdapter(Context context, List<Movie> movies, CallCounter counter, Variant variant) {
        super(context, R.layout.item_variant, movies);
        this.inflater = LayoutInflater.from(context);
        this.counter = counter;
        this.variant = variant;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (variant) {
            case NO_CONVERT_VIEW:
                return withoutConvertView(position, parent);
            case CONVERT_VIEW:
                return withConvertView(position, convertView, parent);
            default:
                return withViewHolder(position, convertView, parent);
        }
    }

    /** A: a new row and two findViewById on every call. */
    private View withoutConvertView(int position, ViewGroup parent) {
        View row = inflate(parent);
        bind(textView(row, R.id.title), textView(row, R.id.details), getItem(position));
        return row;
    }

    /** B: a new row only if there is no old one. Two findViewById on every call. */
    private View withConvertView(int position, View convertView, ViewGroup parent) {
        View row = convertView != null ? convertView : inflate(parent);
        bind(textView(row, R.id.title), textView(row, R.id.details), getItem(position));
        return row;
    }

    /** C: findViewById only when a row is created. The views wait in the tag. */
    private View withViewHolder(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            row = inflate(parent);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        bind(holder.title, holder.details, getItem(position));
        return row;
    }

    private class ViewHolder {
        final TextView title;
        final TextView details;

        ViewHolder(View row) {
            title = textView(row, R.id.title);
            details = textView(row, R.id.details);
        }
    }

    private View inflate(ViewGroup parent) {
        counter.onInflate();
        return inflater.inflate(R.layout.item_variant, parent, false);
    }

    private TextView textView(View row, int id) {
        counter.onFindViewById();
        return row.findViewById(id);
    }

    private void bind(TextView title, TextView details, Movie movie) {
        title.setText(movie.toString());
        details.setText("id " + movie.getId() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
        counter.onBind();
    }
}
