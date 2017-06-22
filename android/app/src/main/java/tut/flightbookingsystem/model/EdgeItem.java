package tut.flightbookingsystem.model;

public class EdgeItem extends AbstractItem {
    public EdgeItem(final long id,
                    final String label,
                    final boolean isTaken) {
        super(id, label, isTaken);
    }

    @Override
    public int getType() {
        return TYPE_EDGE;
    }
}
