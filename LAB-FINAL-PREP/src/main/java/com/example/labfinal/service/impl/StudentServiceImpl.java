package com.example.labfinal.service.impl;

import com.example.labfinal.dto.StudentCreateDTO;
import com.example.labfinal.dto.StudentGpaRecord;
import com.example.labfinal.entity.Address;
import com.example.labfinal.entity.Department;
import com.example.labfinal.entity.Guardian;
import com.example.labfinal.entity.Student;
import com.example.labfinal.exception.DuplicateResourceException;
import com.example.labfinal.exception.ResourceNotFoundException;
import com.example.labfinal.repository.jpa.DepartmentRepository;
import com.example.labfinal.repository.jpa.StudentRepository;
import com.example.labfinal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @Transactional
    public Student createStudent(StudentCreateDTO dto) {
        if (studentRepository.existsByStudentId(dto.getStudentId())) {
            throw new DuplicateResourceException("Student ID already exists: " + dto.getStudentId());
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + dto.getDepartmentId()));

        Guardian guardian = null;
        if (dto.getGuardianName() != null && !dto.getGuardianName().isBlank()) {
            guardian = Guardian.builder()
                    .name(dto.getGuardianName())
                    .email(dto.getGuardianEmail() != null ? dto.getGuardianEmail() : "guardian@example.com")
                    .mobile(dto.getGuardianMobile() != null ? dto.getGuardianMobile() : "N/A")
                    .build();
        }

        Address address = Address.builder()
                .streetAddress(dto.getStreetAddress() != null ? dto.getStreetAddress() : "Campus Road")
                .city(dto.getCity() != null ? dto.getCity() : "Dhaka")
                .state(dto.getState() != null ? dto.getState() : "Dhaka")
                .country(dto.getCountry() != null ? dto.getCountry() : "Bangladesh")
                .build();

        List<String> mobiles = new ArrayList<>();
        if (dto.getMobileNumber() != null && !dto.getMobileNumber().isBlank()) {
            mobiles.add(dto.getMobileNumber().trim());
        }

        Student student = Student.builder()
                .studentId(dto.getStudentId())
                .name(dto.getName())
                .gpa(dto.getGpa())
                .department(department)
                .guardian(guardian)
                .address(address)
                .mobileNumbers(mobiles)
                .build();

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Page<Student> getStudentsPaged(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentGpaRecord getStudentRecord(Long id) {
        Student student = getStudentById(id);
        return new StudentGpaRecord(student.getStudentId(), student.getName(), student.getGpa());
    }

    @Override
    public Double getAverageGpa() {
        Double avg = studentRepository.calculateAverageGpa();
        return avg != null ? avg : 0.0;
    }

    @Override
    public List<Student> getHonorStudents(double minGpa) {
        return studentRepository.findHonorStudents(minGpa);
    }
}
