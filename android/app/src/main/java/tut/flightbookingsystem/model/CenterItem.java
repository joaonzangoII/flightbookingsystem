package tut.flightbookingsystem.model;

public class CenterItem extends AbstractItem {
    public CenterItem(final long id,
                      final String label,
                      final boolean isTaken) {
        super(id, label, isTaken);
    }

    @Override
    public int getType() {
        return TYPE_CENTER;
    }
}
