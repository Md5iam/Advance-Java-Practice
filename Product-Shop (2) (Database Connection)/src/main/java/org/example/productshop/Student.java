package org.example.productshop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
