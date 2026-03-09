package com.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
}
