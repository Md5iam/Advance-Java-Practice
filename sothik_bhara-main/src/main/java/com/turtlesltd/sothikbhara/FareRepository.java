package com.turtlesltd.sothikbhara;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FareRepository extends JpaRepository<Fare, Long> {
    // JpaRepository automatically save(), findAll(), findById(), deleteById() dei
    // Custom query lagle ekhane likha jabe
}