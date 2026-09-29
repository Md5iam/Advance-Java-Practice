package com.example.labfinal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class AutonomousVehicle extends Vehicle {
    private String autopilotSoftwareVersion;

    public AutonomousVehicle(String id, String modelName, String registrationNumber, String autopilotSoftwareVersion) {
        super(id, modelName, registrationNumber);
        this.autopilotSoftwareVersion = autopilotSoftwareVersion;
    }

    @Override
    public String getVehicleType() {
        return "AUTONOMOUS";
    }
}
