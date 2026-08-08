package org.example;

import java.time.LocalDateTime;

public class Student {
    private int id;
    private String name;
    private double gpa;
    private String creayedBy;
    private LocalDateTime createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getCreayedBy() {
        return creayedBy;
    }

    public void setCreayedBy(String creayedBy) {
        this.creayedBy = creayedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
