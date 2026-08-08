package org.example;

import org.example.DTO.StudentGpaRecord;

public class Main {
    static void main() {
        Student student = new Student();
        student.setId(1);
        student.setName("Mr. Java");
        student.setGpa(3.52);

        StudentGpaRecord record = new StudentGpaRecord(student.getName(), student.getGpa());
    }
}
