package com.example.labfinal.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehiclePolymorphismTest {

    private static String testSuiteId;
    private Vehicle ev;
    private Vehicle av;

    @BeforeAll
    static void initAll() {
        testSuiteId = "VEHICLE-TEST-SUITE-1";
    }

    @BeforeEach
    void init() {
        ev = new ElectricVehicle("EV-1", "Tesla Model 3", "DHK-1234", 75.0);
        av = new AutonomousVehicle("AV-1", "Waymo One", "DHK-5678", "v12.4");
    }

    @AfterEach
    void tearDown() {
        ev = null;
        av = null;
    }

    @AfterAll
    static void tearDownAll() {
        testSuiteId = null;
    }

    @Test
    void testSealedClassPolymorphism() {
        Assertions.assertNotNull(testSuiteId);
        Assertions.assertEquals("ELECTRIC", ev.getVehicleType());
        Assertions.assertEquals("AUTONOMOUS", av.getVehicleType());
    }

    @Test
    void testElectricVehicleAttributes() {
        Assertions.assertTrue(ev instanceof ElectricVehicle);
        ElectricVehicle electricVehicle = (ElectricVehicle) ev;
        Assertions.assertEquals("EV-1", electricVehicle.getId());
        Assertions.assertEquals("Tesla Model 3", electricVehicle.getModelName());
        Assertions.assertEquals("DHK-1234", electricVehicle.getRegistrationNumber());
        Assertions.assertEquals(75.0, electricVehicle.getBatteryCapacityKWh());
    }

    @Test
    void testAutonomousVehicleAttributes() {
        Assertions.assertTrue(av instanceof AutonomousVehicle);
        AutonomousVehicle autonomousVehicle = (AutonomousVehicle) av;
        Assertions.assertEquals("AV-1", autonomousVehicle.getId());
        Assertions.assertEquals("Waymo One", autonomousVehicle.getModelName());
        Assertions.assertEquals("DHK-5678", autonomousVehicle.getRegistrationNumber());
        Assertions.assertEquals("v12.4", autonomousVehicle.getAutopilotSoftwareVersion());
    }
}
