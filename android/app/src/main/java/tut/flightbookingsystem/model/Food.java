package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Food implements Serializable {
    public long id;
    public String name;
    public String image;
    public long food_type_id;
    public FoodType food_type;
    public String created_at;
    public String updated_at;
}
