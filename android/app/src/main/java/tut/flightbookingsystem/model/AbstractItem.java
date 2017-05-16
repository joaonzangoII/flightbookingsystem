package tut.flightbookingsystem.model;


public abstract class AbstractItem {
    public static final int TYPE_CENTER = 0;
    public static final int TYPE_EDGE = 1;
    public static final int TYPE_EMPTY = 2;

    private long id;
    private String label;

    public AbstractItem(final long id,
                        final String label) {
        this.id = id;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
    abstract public int getType();
}
