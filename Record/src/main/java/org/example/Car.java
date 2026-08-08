package org.example;

public sealed class Car permits ElectricCar, WaterCar{
    private int id;
    private String brand;
    private String color;
}
