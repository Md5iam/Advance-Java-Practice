package com.turtlesltd.sothikbhara;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Starting address is required")
    private String startAddress;

    @NotBlank(message = "Drop address is required")
    private String dropAddress;

    @Positive(message = "KM must be greater than 0")
    private double km;

    private double normalFare;

    private double studentFare;
}