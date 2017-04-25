package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Flight implements Serializable {
    public long id;
    public long aircraft_id;
    public long flight_status_id;
    public Aircraft aircraft;
    public Schedule schedule;
}
