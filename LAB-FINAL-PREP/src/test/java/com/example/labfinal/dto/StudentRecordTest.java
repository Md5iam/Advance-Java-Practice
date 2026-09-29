package com.example.labfinal.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentRecordTest {

    @Test
    void testStudentRecordNormalization() {
        StudentGpaRecord record = new StudentGpaRecord("2021-1-60-001", "john doe", 4.5);
        Assertions.assertEquals("JOHN DOE", record.name());
        Assertions.assertEquals(4.0, record.gpa());
    }

    @Test
    void testStudentRecordValidationThrowsOnBlank() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new StudentGpaRecord("2021", "  ", 3.0));
    }

    @Test
    void testStudentRecordValidCreation() {
        StudentGpaRecord record = new StudentGpaRecord("2021-1-60-002", "Alice Smith", 3.75);
        Assertions.assertEquals("2021-1-60-002", record.studentId());
        Assertions.assertEquals("ALICE SMITH", record.name());
        Assertions.assertEquals(3.75, record.gpa());
    }
}
