package tut.flightbookingsystem.model;

public class EdgeItem extends AbstractItem {
    public EdgeItem(final long id,
                    final String label) {
        super(id, label);
    }

    @Override
    public int getType() {
        return TYPE_EDGE;
    }
}
