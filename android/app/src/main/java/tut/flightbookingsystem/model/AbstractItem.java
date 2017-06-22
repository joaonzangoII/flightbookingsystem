package tut.flightbookingsystem.model;


public abstract class AbstractItem {
    public static final int TYPE_CENTER = 0;
    public static final int TYPE_EDGE = 1;
    public static final int TYPE_EMPTY = 2;

    private long id;
    private String label;
    private boolean isTaken;

    public AbstractItem(final long id,
                        final String label,
                        final boolean isTaken) {
        this.id = id;
        this.label = label;
        this.isTaken = isTaken;
    }

    public long getId() {
        return id;
    }

    public boolean getIsTaken() {
        return isTaken;
    }

    public String getLabel() {
        return label;
    }
    abstract public int getType();
}
