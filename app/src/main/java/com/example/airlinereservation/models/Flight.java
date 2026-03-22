package com.example.airlinereservation.models;

public class Flight {
    private int id;
    private String airlineName;
    private String source;
    private String destination;
    private String time;
    private double price;

    public Flight() {}

    public Flight(int id, String airlineName, String source, String destination, String time, double price) {
        this.id = id;
        this.airlineName = airlineName;
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
