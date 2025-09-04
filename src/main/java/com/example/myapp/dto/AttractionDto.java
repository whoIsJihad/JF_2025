package com.example.myapp.dto;

import lombok.Data;
import java.math.BigDecimal;
/**
 * DTO for representing an attraction in API responses.
 * This is 
 * a plain data container with no connection to the database.
 */
@Data // A Lombok annotation that bundles @Getter, @Setter, @ToString, etc.
public class AttractionDto {

    private Long id;
    private String name;
    private String type;
    private String address;
    private String locationCoords;
    private BigDecimal avgRating;
    private String description;
    
    // FLATTENED DATA: Instead of a City object, we have a simple String.
    private String cityName; 
}

