package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Country implements Serializable {
    public long id;
    public String name;
    public String iata_country_code;
    public String created_at;
    public String updated_at;
}
