package com.CRUD.service;

import com.CRUD.dto.LoginResponseDTO;
import com.CRUD.dto.RegisterStudentDTO;
import com.CRUD.dto.ResponseStudentDTO;
import com.CRUD.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface StudentService {
    Student saveStudent(Student saveStudent);

    List<Student> saveAll(List<Student> students);

    Student updateStudent(Long id, Student student);

    void deleteById(Long id);

    @EntityGraph(attributePaths = "")
    List<ResponseStudentDTO> findAll();

    Student findById(Long id);

    List<ResponseStudentDTO> findByName(String firstName);

    ResponseStudentDTO registerStudent(RegisterStudentDTO registerStudentDTO);

    LoginResponseDTO validateStudent(String email, String password);

    boolean emailExists(String email);

    Page<ResponseStudentDTO> getAllStudents(int page, int size, String sortByName);
}
