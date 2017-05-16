package tut.flightbookingsystem.model;

import java.io.Serializable;
import java.util.List;

public class Booking implements Serializable {
    public long id;
    public String booking_number;
    public double total;
    public double subtotal;
    public long user_id;
    public long departure_flight_id;
    public long return_flight_id;
    public long aircraft_id;
    public User user;
    public Flight departure_flight;
    public Flight return_flight;
    public Aircraft aircraft;
    public List<Passenger> passengers;
    public String created_at;
    public String updated_at;
}
