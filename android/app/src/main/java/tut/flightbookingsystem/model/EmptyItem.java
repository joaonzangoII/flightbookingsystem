package tut.flightbookingsystem.model;

public class EmptyItem extends AbstractItem {
    public EmptyItem(final long id,
                     final String label) {
        super(id, label);
    }

    @Override
    public int getType() {
        return TYPE_EMPTY;
    }
}
