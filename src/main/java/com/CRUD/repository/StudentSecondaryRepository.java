package com.CRUD.repository;

import com.CRUD.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentSecondaryRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findByFirstName(@Param("firstName") String firstName);
}