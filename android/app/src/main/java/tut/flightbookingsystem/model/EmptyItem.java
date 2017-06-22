package tut.flightbookingsystem.model;

public class EmptyItem extends AbstractItem {
    public EmptyItem(final long id,
                     final String label,
                     final boolean isTaken) {
        super(id, label, isTaken);
    }

    @Override
    public int getType() {
        return TYPE_EMPTY;
    }
}
