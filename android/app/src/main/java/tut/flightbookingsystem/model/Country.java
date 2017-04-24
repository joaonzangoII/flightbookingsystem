package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Country implements Serializable {
    private long id;
    private String name;
    private String iata_country_code;
    public String created_at;
    public String updated_at;
}
