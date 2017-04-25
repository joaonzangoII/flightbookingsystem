package tut.flightbookingsystem.model;

import java.io.Serializable;

public class Meal implements Serializable {
    public long id;
    public long passenger_id;
    public long drink_id;
    public long food_id;
    public Food food;
    public Drink drink;
    public String created_at;
    public String updated_at;
}
