package tut.flightbookingsystem.model;

import java.io.Serializable;

public class MainItem implements Serializable {
    public long id;
    public String value;
    public String description;

    public MainItem(final long id,
                    final String value,
                    final String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }
}
