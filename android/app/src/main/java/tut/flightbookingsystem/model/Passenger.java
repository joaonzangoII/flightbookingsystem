package tut.flightbookingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Passenger implements Serializable, Parcelable {
    public long id;
    public String name;
    public String firstnames;
    public String surname;
    public String id_number;
    public String date_of_birth;
    public String gender;
    public String food_and_drink;
    public Meal meal;
    public long booking_id;
    public Booking booking;
    public long flight_seat_id;
    public FlightSeat flight_seat;
    public String created_at;
    public String updated_at;

    public Passenger() {
    }

    public Passenger(final Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest,
                              final int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(firstnames);
        dest.writeString(surname);
        dest.writeString(gender);
        dest.writeString(food_and_drink);
        dest.writeString(id_number);
        dest.writeString(date_of_birth);
        dest.writeLong(flight_seat_id);
        dest.writeLong(booking_id);
        dest.writeSerializable(flight_seat);
        dest.writeSerializable(meal);
        dest.writeSerializable(created_at);
        dest.writeSerializable(updated_at);
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(final Parcel in) {
            return new Passenger(in);
        }

        @Override
        public Passenger[] newArray(final int size) {
            return new Passenger[size];
        }
    };
}

