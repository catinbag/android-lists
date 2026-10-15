package org.demo.lists.rvbasic;

public class HeaderRow implements ListRow {
    private final String title;

    public HeaderRow(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
