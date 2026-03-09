package com.CRUD.service.impl;

import com.CRUD.dto.LoginResponseDTO;
import com.CRUD.dto.RegisterStudentDTO;
import com.CRUD.dto.ResponseStudentDTO;
import com.CRUD.exception.DuplicateStudentException;
import com.CRUD.exception.StudentNotFoundException;
import com.CRUD.model.Student;
import com.CRUD.repository.StudentRepository;
import com.CRUD.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student saveStudent(Student saveStudent) {
        log.info("Save Student with name : {}", saveStudent.getFirstName());
        Student student = studentRepository.save(saveStudent);
        log.info("Student Save Successfully with id : {}", student.getId());
        return student;
    }

    @Override
    public List<Student> saveAll(List<Student> students) {
        log.info("Saving {} students", students.size());
        return studentRepository.saveAll(students);
    }


    @Transactional
    @Override
    public Student updateStudent(Long id, Student update) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student Not Found With id : {}", id);
                    return new StudentNotFoundException("Student Not Found.." + id);
                });
        student.setFirstName(update.getFirstName());
        student.setLastName(update.getLastName());
        student.setAddress(update.getAddress());
        student.setEmail(update.getEmail());
        student.setMobileNo(update.getMobileNo());
        student.setRollNo(update.getRollNo());
        student.setCollegeName(update.getCollegeName());
        student.setDob(update.getDob());
        student.setAge(update.getAge());
        Student studentUpdate = studentRepository.save(student);
        log.info("Student Update successfully with id : {}", id);
        return studentUpdate;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Student delete by id : {}", id);
        studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student Not Found By id : {}", id);
                    return new StudentNotFoundException("Student Not Found " + id);
                });
        studentRepository.deleteById(id);
        log.info("Student Delete Successfully By id : {}", id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseStudentDTO> findAll() {
        log.info("Fetching all students from database");
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> new ResponseStudentDTO(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getMobileNo()
                ))
                .toList();
    }

    @Override
    public Student findById(Long id) {
        log.info("Get Student By id:{}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student Not Found By Id : {}", id);
                    return new StudentNotFoundException("Student not found" + id);
                });
    }

    @Override
    public List<Student> findByName(String firstName) {
        log.info("Finding students with first name: {}", firstName);
        return studentRepository.findByFirstName(firstName);
    }

    @Override
    public ResponseStudentDTO registerStudent(RegisterStudentDTO dto) {
        log.info("Registering student with email: {}", dto.getEmail());
        Student existing = studentRepository.getStudentByEmail(dto.getEmail());
        if (existing != null) {
            throw new DuplicateStudentException("Email already registered");
        }
        //Convert DTO to Entity
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setAddress(dto.getAddress());
        student.setEmail(dto.getEmail());
        student.setMobileNo(dto.getMobileNo());
        student.setPassword(dto.getPassword());
        Student saveStudent = studentRepository.save(student);
        //Convert Entity to DTO Response
        ResponseStudentDTO response = new ResponseStudentDTO();
        response.setId(saveStudent.getId());
        response.setFirstName(saveStudent.getFirstName());
        response.setLastName(saveStudent.getLastName());
        response.setEmail(saveStudent.getEmail());
        response.setMobileNo(saveStudent.getMobileNo());
        return response;
    }

    @Override
    public LoginResponseDTO validateStudent(String email, String password) {
        log.info("Validating student with email: {}", email);
        Student student = studentRepository.getStudentByEmail(email);
        if (student == null) {
            throw new StudentNotFoundException("Invalid Email");
        }
        if (!student.getPassword().equals(password)) {
            throw new StudentNotFoundException("Invalid Password");
        }
        //Convert Entity to DTO Response
        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(student.getId());
        response.setFirstName(student.getFirstName());
        response.setEmail(student.getEmail());
        return response;
    }
}
