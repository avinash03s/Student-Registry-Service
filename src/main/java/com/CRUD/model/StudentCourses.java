package com.CRUD.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "student_course")
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String universityName;

    @ManyToMany()
    @JoinColumn(name = "student_id")
    private Student student;
}
