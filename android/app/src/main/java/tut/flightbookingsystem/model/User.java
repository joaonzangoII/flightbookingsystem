package tut.flightbookingsystem.model;

import java.io.Serializable;

public class User implements Serializable {
    public long id;
    public String name;
    public String first_name;
    public String middle_name;
    public String last_name;
    public String id_number;
    public String phone;
    public String email;
    public String password;
    public long country_id;
    public String created_at;
    public String updated_at;
}

