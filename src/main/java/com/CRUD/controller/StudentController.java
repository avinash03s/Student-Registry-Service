package com.CRUD.controller;

import com.CRUD.dto.LoginResponseDTO;
import com.CRUD.dto.LoginStudentDTO;
import com.CRUD.dto.RegisterStudentDTO;
import com.CRUD.dto.ResponseStudentDTO;
import com.CRUD.model.Student;
import com.CRUD.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService service;

    public static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @PostMapping("/register")
    public ResponseEntity<ResponseStudentDTO> registerStudent(@Valid @RequestBody RegisterStudentDTO dto) {
        log.info("Student registration started for email: {}", dto.getEmail());
        ResponseStudentDTO response = service.registerStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginStudent(@Valid @RequestBody LoginStudentDTO dto) {
        log.info("Student Enter Into Validation Process for email: {}", dto.getEmail());
        LoginResponseDTO response = service.validateStudent(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(response);
    }

    // used to add the new student in db
    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@Valid @RequestBody Student std) {
        log.info("Creating new student");
        log.debug("Saving student into database");
        Student student = this.service.saveStudent(std);
        log.info("Student saved successfully with id: {}", student.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body("student saved successfully with id: " + student.getId());
    }

    @PostMapping("/students/batch")
    public ResponseEntity<String> addAllStudents(@Valid @RequestBody List<Student> students) {
        log.info("addAllStudent Api Call");
        log.debug("Saving All Student into database");
        List<Student> savedStudents = service.saveAll(students);
        log.info("Total students saved: {}", savedStudents.size());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Total students saved: " + savedStudents.size());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,
                                           @Valid @RequestBody Student std) {
        std.setId(id);
        log.info("updateStudent Api Call");
        log.debug("Update Student Data into Database");
        this.service.updateStudent(id, std);
        log.info("Student Successfully Update");
        return ResponseEntity.status(HttpStatus.OK).body("Student data Updated Successfully");
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (id == null || id <= 0) {
            log.warn("Invalid student id received: {}", id);
            return ResponseEntity.badRequest().body("Invalid student id");
        }
        log.debug("Student Delete From Database");
        this.service.deleteById(id);
        log.info("Student Delete Successfully by id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body("Student Data Delete Successfully");
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ResponseStudentDTO> getStudent(@PathVariable Long id) {
        log.info("Searching student with id: {}", id);
        if (id <= 0) {
            log.warn("Invalid student-id {}", id);
            return ResponseEntity.badRequest().build();
        }
        log.debug("Fetching student from database");
        Student student = service.findById(id);
        ResponseStudentDTO dto = new ResponseStudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getMobileNo()
        );
        log.info("Student found successfully with id: {}", id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/students")
    public ResponseEntity<List<ResponseStudentDTO>> getAllStudents() {
        log.info("Get all students API called");
        List<ResponseStudentDTO> students = service.findAll();
        List<ResponseStudentDTO> responseStdDTOList = students.stream()
                .map(std -> new ResponseStudentDTO(
                        std.getId(),
                        std.getFirstName(),
                        std.getLastName(),
                        std.getEmail(),
                        std.getMobileNo()))
                .toList();
        return ResponseEntity.ok(responseStdDTOList);
    }

    @GetMapping("/findByName/{firstName}")
    public ResponseEntity<List<ResponseStudentDTO>> findByName(@PathVariable String firstName) {
        log.info("Searching student with name: {}", firstName);
        List<Student> students = service.findByName(firstName);
        if (students.isEmpty()) {
            log.warn("Student not found with name {}", firstName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<ResponseStudentDTO> response = students.stream()
                .map(student -> new ResponseStudentDTO(
                        student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getMobileNo()))
                .toList();
        log.info("Student found with name: {}", firstName);
        return ResponseEntity.ok(response);
    }
}
