package tut.flightbookingsystem.model;

import java.io.Serializable;
import java.util.List;

public class Flight implements Serializable {
    public long id;
    public long aircraft_id;
    public long flight_status_id;
    public Aircraft aircraft;
    public Schedule schedule;
    public List<FlightSeatPrice> flight_seat_prices;
    public String created_at;
    public String updated_at;


    public double getStartPrice() {
        for (final FlightSeatPrice sP : flight_seat_prices) {
            if (sP.flight_seat.travel_class.name.equals("First")) {
                return sP.price;
            }
        }

        return 0.0;
    }
}
