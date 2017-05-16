package tut.flightbookingsystem.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.io.Serializable;
import java.util.List;

public class PassengerHeader extends ExpandableGroup<Passenger> implements Serializable {
    private String name;
    private List<Passenger> passenger;

    public PassengerHeader(final String name,
                           final List<Passenger> items) {
        super(name, items);
    }


    public String getName() {
        return name;
    }
}
