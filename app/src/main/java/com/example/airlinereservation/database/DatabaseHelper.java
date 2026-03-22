package com.example.airlinereservation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.airlinereservation.models.Booking;
import com.example.airlinereservation.models.Flight;
import com.example.airlinereservation.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "airline.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASS = "password";

    // Flights Table
    private static final String TABLE_FLIGHTS = "flights";
    private static final String COL_FLIGHT_ID = "id";
    private static final String COL_FLIGHT_NAME = "airline_name";
    private static final String COL_FLIGHT_SOURCE = "source";
    private static final String COL_FLIGHT_DEST = "destination";
    private static final String COL_FLIGHT_TIME = "time";
    private static final String COL_FLIGHT_PRICE = "price";

    // Bookings Table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COL_BOOKING_ID = "id";
    private static final String COL_BOOKING_USER_ID = "user_id";
    private static final String COL_BOOKING_FLIGHT_ID = "flight_id";
    private static final String COL_BOOKING_PASSENGER = "passenger_name";
    private static final String COL_BOOKING_AGE = "passenger_age";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_EMAIL + " TEXT UNIQUE, " +
                COL_USER_PASS + " TEXT)";
        db.execSQL(createUsersTable);

        String createFlightsTable = "CREATE TABLE " + TABLE_FLIGHTS + " (" +
                COL_FLIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FLIGHT_NAME + " TEXT, " +
                COL_FLIGHT_SOURCE + " TEXT, " +
                COL_FLIGHT_DEST + " TEXT, " +
                COL_FLIGHT_TIME + " TEXT, " +
                COL_FLIGHT_PRICE + " REAL)";
        db.execSQL(createFlightsTable);

        String createBookingsTable = "CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BOOKING_USER_ID + " INTEGER, " +
                COL_BOOKING_FLIGHT_ID + " INTEGER, " +
                COL_BOOKING_PASSENGER + " TEXT, " +
                COL_BOOKING_AGE + " INTEGER)";
        db.execSQL(createBookingsTable);
        
        // Insert dummy data
        db.execSQL("INSERT INTO " + TABLE_FLIGHTS + " (airline_name, source, destination, time, price) VALUES " +
                "('Air Blue', 'New York', 'London', '2024-12-01 10:00 AM', 450.0), " +
                "('Delta Airlines', 'Los Angeles', 'Tokyo', '2024-12-05 08:30 AM', 800.0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    // --- User Operations ---
    public boolean registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_NAME, user.getName());
        cv.put(COL_USER_EMAIL, user.getEmail());
        cv.put(COL_USER_PASS, user.getPassword());
        long result = db.insert(TABLE_USERS, null, cv);
        db.close();
        return result != -1;
    }

    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?", new String[]{email, password});
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }

    // --- Flight Operations ---
    public boolean addFlight(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FLIGHT_NAME, flight.getAirlineName());
        cv.put(COL_FLIGHT_SOURCE, flight.getSource());
        cv.put(COL_FLIGHT_DEST, flight.getDestination());
        cv.put(COL_FLIGHT_TIME, flight.getTime());
        cv.put(COL_FLIGHT_PRICE, flight.getPrice());
        long result = db.insert(TABLE_FLIGHTS, null, cv);
        db.close();
        return result != -1;
    }
    
    public boolean updateFlight(Flight flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FLIGHT_NAME, flight.getAirlineName());
        cv.put(COL_FLIGHT_SOURCE, flight.getSource());
        cv.put(COL_FLIGHT_DEST, flight.getDestination());
        cv.put(COL_FLIGHT_TIME, flight.getTime());
        cv.put(COL_FLIGHT_PRICE, flight.getPrice());
        long result = db.update(TABLE_FLIGHTS, cv, "id=?", new String[]{String.valueOf(flight.getId())});
        db.close();
        return result > 0;
    }

    public boolean deleteFlight(int flightId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_FLIGHTS, "id=?", new String[]{String.valueOf(flightId)});
        db.close();
        return result > 0;
    }

    public List<Flight> searchFlights(String source, String destination, String date) {
        List<Flight> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FLIGHTS + " WHERE source LIKE ? AND destination LIKE ? AND time LIKE ?", 
                new String[]{"%"+source+"%", "%"+destination+"%", "%"+date+"%"});
        if (cursor.moveToFirst()) {
            do {
                Flight f = new Flight(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5)
                );
                list.add(f);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    
    public List<Flight> getAllFlights() {
        List<Flight> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FLIGHTS, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Flight(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // --- Booking Operations ---
    public boolean bookTicket(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOKING_USER_ID, booking.getUserId());
        cv.put(COL_BOOKING_FLIGHT_ID, booking.getFlightId());
        cv.put(COL_BOOKING_PASSENGER, booking.getPassengerName());
        cv.put(COL_BOOKING_AGE, booking.getPassengerAge());
        long result = db.insert(TABLE_BOOKINGS, null, cv);
        db.close();
        return result != -1;
    }

    public boolean cancelTicket(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOKINGS, "id=?", new String[]{String.valueOf(bookingId)});
        db.close();
        return result > 0;
    }

    public List<Booking> getUserBookings(int userId) {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.id, b.user_id, b.flight_id, b.passenger_name, b.passenger_age, " +
                "f.airline_name, f.source, f.destination, f.time " +
                "FROM " + TABLE_BOOKINGS + " b " +
                "JOIN " + TABLE_FLIGHTS + " f ON b.flight_id = f.id " +
                "WHERE b.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Booking b = new Booking();
                b.setId(cursor.getInt(0));
                b.setUserId(cursor.getInt(1));
                b.setFlightId(cursor.getInt(2));
                b.setPassengerName(cursor.getString(3));
                b.setPassengerAge(cursor.getInt(4));
                b.setFlightName(cursor.getString(5));
                b.setSource(cursor.getString(6));
                b.setDestination(cursor.getString(7));
                b.setTime(cursor.getString(8));
                list.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    
    public List<Booking> getAllBookingsAdmin() {
        List<Booking> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b.id, b.user_id, b.flight_id, b.passenger_name, b.passenger_age, " +
                "f.airline_name, f.source, f.destination, f.time, u.email " +
                "FROM " + TABLE_BOOKINGS + " b " +
                "JOIN " + TABLE_FLIGHTS + " f ON b.flight_id = f.id " +
                "JOIN " + TABLE_USERS + " u ON b.user_id = u.id";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Booking b = new Booking();
                b.setId(cursor.getInt(0));
                b.setUserId(cursor.getInt(1));
                b.setFlightId(cursor.getInt(2));
                b.setPassengerName(cursor.getString(3));
                b.setPassengerAge(cursor.getInt(4));
                b.setFlightName(cursor.getString(5));
                b.setSource(cursor.getString(6));
                b.setDestination(cursor.getString(7));
                b.setTime(cursor.getString(8));
                b.setUserEmail(cursor.getString(9));
                list.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
