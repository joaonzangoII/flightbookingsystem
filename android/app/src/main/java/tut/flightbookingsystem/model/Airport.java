package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Airport implements Serializable {
    public long id;
    public String name;
    public String iata_airport_code;
    public String city;
    public long country_id;
    public Country country;
    public String created_at;
    public String updated_at;

    @Override
    public String toString() {
        return name;
    }
}
