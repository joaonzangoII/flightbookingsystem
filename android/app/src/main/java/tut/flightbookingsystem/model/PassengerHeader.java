package tut.flightbookingsystem.model;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.io.Serializable;
import java.util.List;

public class PassengerHeader implements Serializable, Parent<Passenger> {
    // a PassengerHeader contains several passengers
    private String name;
    private List<Passenger> passenger;
    //
    //    @Override
    //    public boolean equals(Object o) {
    //        if (this == o) return true;
    //        if (!(o instanceof Passenger)) return false;
    //
    //        final Passenger passenger = (Passenger) o;
    //
    //        //if (isFavorite() != passenger.isFavorite()) return false;
    //        return name != null ? name.equals(passenger.name) : passenger.name == null;
    //
    //    }

    public PassengerHeader(final String name,
                           final List<Passenger> passenger) {
        this.passenger = passenger;
    }

    @Override
    public List<Passenger> getChildList() {
        return passenger;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
