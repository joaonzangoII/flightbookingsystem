package tut.flightbookingsystem.model;

public class CenterItem extends AbstractItem {
    public CenterItem(final long id,
                      final String label) {
        super(id, label);
    }

    @Override
    public int getType() {
        return TYPE_CENTER;
    }
}
