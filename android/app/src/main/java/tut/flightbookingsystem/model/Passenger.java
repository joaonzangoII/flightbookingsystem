package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Passenger implements Serializable {
    public long id;
    public String first_name;
    public String middle_name;
    public String last_name;
    public String id_number;
    public String date_of_birth;
    public String gender;
    public long booking_id;
    public String created_at;
    public String updated_at;
}
