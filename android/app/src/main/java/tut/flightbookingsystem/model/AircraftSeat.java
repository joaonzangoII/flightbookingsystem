package tut.flightbookingsystem.model;

import java.io.Serializable;

/**
 * Created by joaonzangoii on 4/25/17.
 */

public class AircraftSeat implements Serializable {
    public long id;
    public int number;
    public long travel_class_id;
    public TravelClass travel_class;
    public String created_at;
    public String updated_at;
}
