package tut.flightbookingsystem.model;

import java.io.Serializable;

public class MainItem implements Serializable {
    public long id;
    public int color;
    public String value;
    public String description;

    public MainItem(final long id,
                    final int color,
                    final String value,
                    final String description) {
        this.id = id;
        this.color = color;
        this.value = value;
        this.description = description;
    }
}
