package com.example.labfinal.repository.jpa;

import com.example.labfinal.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByDepartmentId(Long departmentId);

    Page<Student> findAll(Pageable pageable);

    @Query("SELECT AVG(s.gpa) FROM Student s")
    Double calculateAverageGpa();

    @Query("SELECT s FROM Student s WHERE s.gpa >= :minGpa")
    List<Student> findHonorStudents(@Param("minGpa") double minGpa);

    boolean existsByStudentId(String studentId);
}
