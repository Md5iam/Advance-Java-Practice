package org.example.hotelmanagmentsystem.model;

public enum RoomType {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    SUITE("Executive Suite"),
    DELUXE("Deluxe Room");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
