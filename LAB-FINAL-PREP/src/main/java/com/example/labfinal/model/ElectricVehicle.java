package com.example.labfinal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class ElectricVehicle extends Vehicle {
    private double batteryCapacityKWh;

    public ElectricVehicle(String id, String modelName, String registrationNumber, double batteryCapacityKWh) {
        super(id, modelName, registrationNumber);
        this.batteryCapacityKWh = batteryCapacityKWh;
    }

    @Override
    public String getVehicleType() {
        return "ELECTRIC";
    }
}
