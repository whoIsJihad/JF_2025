package com.example.myapp.model;

import jakarta.persistence.*;

/**
 * Represents a place of interest within a trip.
 * This class is a JPA entity that maps to the "place" table in the database.
 * Each Place object contains details about a specific location, such as its name,
 * type (e.g., restaurant, sightseeing), location, and rating. It is associated
 * with a single {@link Trip}, forming a many-to-one relationship.
 */
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // restaurant / sightseeing / relaxing

    private String location;

    private Double rating;

    // Many places belong to one trip
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Place() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
