package tut.flightbookingsystem.model;

import java.io.Serializable;

public class TravelClass implements Serializable {
    public long id;
    public String name;
    public String description;
    public String created_at;
    public String updated_at;


    @Override
    public String toString() {
        return name;
    }
}
