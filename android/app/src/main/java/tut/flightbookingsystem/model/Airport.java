package tut.flightbookingsystem.model;

import java.io.Serializable;

import tut.flightbookingsystem.manager.SessionManager;

public class Airport implements Serializable {
    public long id;
    public String name;
    public String iata_airport_code;
    public String city;
    public String image;
    public String description;
    public long country_id;
    public Country country;
    public String created_at;
    public String updated_at;

    @Override
    public String toString() {
        return name;
    }

    public String getImage(final SessionManager session) {
        // return String.format("%1$s%2$s", session.getServerUrl(), image);
        return image;
    }
}
