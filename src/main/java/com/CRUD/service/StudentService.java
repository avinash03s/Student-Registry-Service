package com.CRUD.service;

import com.CRUD.dto.LoginResponseDTO;
import com.CRUD.dto.RegisterStudentDTO;
import com.CRUD.dto.ResponseStudentDTO;
import com.CRUD.model.Student;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student saveStudent);

    List<Student> saveAll(List<Student> students);

    Student updateStudent(Long id, Student student);

    void deleteById(Long id);

    List<ResponseStudentDTO> findAll();

    Student findById(Long id);

    List<Student> findByName(String firstName);

    ResponseStudentDTO registerStudent(RegisterStudentDTO registerStudentDTO);

    LoginResponseDTO validateStudent(String email, String password);
}
