package tut.flightbookingsystem.model;

import java.io.Serializable;

public class FlightSeatPrice implements Serializable {
    public long id;
    public double price;
    public long flight_id;
    public long aircraft_id;
    public long flight_seat_id;
    public FlightSeat flight_seat;
    public String created_at;
    public String updated_at;
}
