package com.CRUD.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "student_data",indexes = {
        @Index(name = "idx_email", columnList = "Email"),
        @Index(name = "idx_rollno", columnList = "Roll_No")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "First name must contain only letters")
    @Column(name = "First_Name")
    private String firstName;

    @NotBlank(message = "Last Name Cannot be Blank")
    @Column(name = "Last_Name")
    private String lastName;

    @NotBlank(message = "Address filed cannot be blank")
    @Column(name = "Address")
    private String address;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email filed cannot be blank")
    @Column(name = "Email", unique = true)
    private String email;

    @NotBlank(message = "Mobile No cannot blank")
    @Column(name = "Mobile_No")
    @Pattern(regexp = "^(\\+91)?[6-9]\\d{9}$")
    private String mobileNo;

    @Column(name = "Password")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain 1 uppercase, 1 lowercase, 1 number and 1 special character"
    )
    private String password;

    @NotNull(message = "Roll number cannot be null")
    @Column(name = "Roll_No", unique = true)
    private Integer rollNo;

    @NotBlank(message = "College name cannot be blank")
    @Column(name = "College_Name")
    private String collegeName;

    @Column(name = "Date_Of_Birth")
    private LocalDate dob;

    @Min(18)
    @Max(60)
    @Column(name = "Age")
    private Integer age;
}
