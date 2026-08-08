package org.example.productshop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_data")
public class Student {
    @Id
    private int id;
    private String name;
    @Column(name ="mygpa")
    private double gpa;

    @Embedded //dont create any seperate table for this
    private Address address;

    @ElementCollection //dont create any seperate table for this
    private List<String> mobileNumber;

    @OneToOne
    private Guardian guardian;

}
