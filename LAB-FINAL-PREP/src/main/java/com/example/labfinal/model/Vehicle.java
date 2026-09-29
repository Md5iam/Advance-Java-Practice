package com.example.labfinal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract sealed class Vehicle permits ElectricVehicle, AutonomousVehicle {
    private String id;
    private String modelName;
    private String registrationNumber;

    public abstract String getVehicleType();
}
