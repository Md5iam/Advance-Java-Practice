package com.example.labfinal.seeder;

import com.example.labfinal.document.CaseRecord;
import com.example.labfinal.entity.*;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
import com.example.labfinal.repository.jpa.CourseRepository;
import com.example.labfinal.repository.jpa.DepartmentRepository;
import com.example.labfinal.repository.jpa.StudentRepository;
import com.example.labfinal.repository.jpa.UserRepository;
import com.example.labfinal.repository.mongo.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CaseRepository caseRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            AppUser admin = AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@seu.edu.bd")
                    .roles(Set.of("ROLE_ADMIN", "ROLE_USER"))
                    .build();
            userRepository.save(admin);

            AppUser studentUser = AppUser.builder()
                    .username("student1")
                    .password(passwordEncoder.encode("user123"))
                    .email("student1@seu.edu.bd")
                    .roles(Set.of("ROLE_USER"))
                    .build();
            userRepository.save(studentUser);
        }

        Department cse;
        Department swe;
        if (departmentRepository.count() == 0) {
            cse = departmentRepository.save(Department.builder().name("Computer Science & Engineering").buildingCode("ENG-101").build());
            swe = departmentRepository.save(Department.builder().name("Software Engineering").buildingCode("ENG-202").build());
            departmentRepository.save(Department.builder().name("Electrical Engineering").buildingCode("ENG-303").build());
        } else {
            cse = departmentRepository.findAll().get(0);
            swe = departmentRepository.findAll().size() > 1 ? departmentRepository.findAll().get(1) : cse;
        }

        Course cse101;
        Course cse201;
        if (courseRepository.count() == 0) {
            cse101 = courseRepository.save(Course.builder().courseCode("CSE101").title("Object Oriented Programming").credits(3.0).build());
            cse201 = courseRepository.save(Course.builder().courseCode("CSE201").title("Advanced Java & Spring Boot").credits(3.0).build());
            courseRepository.save(Course.builder().courseCode("CSE301").title("Database Management Systems").credits(3.0).build());
        } else {
            cse101 = courseRepository.findAll().get(0);
            cse201 = courseRepository.findAll().size() > 1 ? courseRepository.findAll().get(1) : cse101;
        }

        if (studentRepository.count() == 0) {
            Guardian g1 = Guardian.builder().name("Rafiq Ahmed").email("rafiq@example.com").mobile("+8801711111111").build();
            Address a1 = Address.builder().streetAddress("House 12, Road 4").city("Dhaka").state("Dhaka").country("Bangladesh").build();
            List<String> m1 = new ArrayList<>(List.of("+8801812345678", "+8801912345678"));

            Student s1 = Student.builder()
                    .studentId("2021-1-60-001")
                    .name("Siam Ahmed")
                    .gpa(3.85)
                    .department(cse)
                    .guardian(g1)
                    .address(a1)
                    .mobileNumbers(m1)
                    .courses(new ArrayList<>(List.of(cse101, cse201)))
                    .build();
            studentRepository.save(s1);

            Guardian g2 = Guardian.builder().name("Nasir Uddin").email("nasir@example.com").mobile("+8801722222222").build();
            Address a2 = Address.builder().streetAddress("Plot 5, Sector 7").city("Uttara").state("Dhaka").country("Bangladesh").build();
            List<String> m2 = new ArrayList<>(List.of("+8801755555555"));

            Student s2 = Student.builder()
                    .studentId("2021-1-60-002")
                    .name("Tanvir Hasan")
                    .gpa(3.65)
                    .department(swe)
                    .guardian(g2)
                    .address(a2)
                    .mobileNumbers(m2)
                    .courses(new ArrayList<>(List.of(cse201)))
                    .build();
            studentRepository.save(s2);
        }

        try {
            if (caseRepository.count() == 0) {
                CaseRecord c1 = CaseRecord.builder()
                        .caseId("CASE-101")
                        .title("Laboratory Equipment Misplacement")
                        .leadDetective("Inspector Morse")
                        .priority(Priority.HIGH)
                        .status(CaseStatus.OPEN)
                        .build();
                caseRepository.save(c1);

                CaseRecord c2 = CaseRecord.builder()
                        .caseId("CASE-102")
                        .title("Server Room Unauthorized Access Log")
                        .leadDetective("Agent Cooper")
                        .priority(Priority.MEDIUM)
                        .status(CaseStatus.IN_PROGRESS)
                        .build();
                caseRepository.save(c2);

                CaseRecord c3 = CaseRecord.builder()
                        .caseId("CASE-103")
                        .title("Lost Campus Access Badge")
                        .leadDetective("Officer Brady")
                        .priority(Priority.LOW)
                        .status(CaseStatus.RESOLVED)
                        .build();
                caseRepository.save(c3);
            }
        } catch (Exception ignored) {
        }
    }
}
