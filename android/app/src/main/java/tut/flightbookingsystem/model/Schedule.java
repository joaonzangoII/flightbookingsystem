package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Schedule implements Serializable {
    public long id;
    public String origin_airport_id;
    public String origin_airport_name;
    public String destination_airport_id;
    public String destination_airport_name;
    public String departure_time;
    public String arrival_time;
}
