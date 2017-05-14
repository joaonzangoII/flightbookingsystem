package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Passenger implements Serializable {
    public long id;
    public String name;
    public String first_name;
    public String middle_name;
    public String last_name;
    public String id_number;
    public String date_of_birth;
    public String gender;
    public Meal meal;
    public long booking_id;
    public long flight_seat_id;
    public FlightSeat flight_seat;
    public String created_at;
    public String updated_at;
}

