package tut.flightbookingsystem.model;

import java.io.Serializable;

public class MealDrink implements Serializable {
    public long id;
    public long passenger_id;
    public long drink_id;
    public Drink drink;
    public String created_at;
    public String updated_at;
}
