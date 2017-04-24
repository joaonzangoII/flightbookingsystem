package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Schedule implements Serializable {
    public long id;
    public long origin_airport_id;
    public long destination_airport_id;
    public long flight_id;
    public String departure_time;
    public String arrival_time;
    public String duration;
    public Airport origin_airport;
    public Airport destination_airport;
    public Flight flight;
    public String date;
    public String created_at;
    public String updated_at;
}
