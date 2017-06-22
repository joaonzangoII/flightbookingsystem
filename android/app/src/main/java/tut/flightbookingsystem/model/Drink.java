package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Drink implements Serializable {
    public Long id;
    public String name;
    public String image;
    public String created_at;
    public String updated_at;

    public Drink(Long id,
                 final String name,
                 final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}
