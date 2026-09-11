package com.example.hellomomgotest.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student save(StudentSaveDTO dto){
        Student student = Student.builder()
                                .name(dto.name()).age(dto.age()).status(dto.status())
                                .build();
        return studentRepository.save(student);
    }

    public Student findById ( String id ){
        return studentRepository.findById(id).orElse(null);
    }
}
