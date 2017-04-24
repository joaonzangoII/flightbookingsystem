package tut.flightbookingsystem.model;

/**
 * Created by joaonzangoii on 4/23/17.
 */

public class Aircraft {
    private long id;
    public String name;
    public String model;
    private long number_of_seats;
    private long aircraft_manufaturer_id;
    private AircraftManufaturer aircraft_manufaturer;
    public String created_at;
    public String updated_at;
}
