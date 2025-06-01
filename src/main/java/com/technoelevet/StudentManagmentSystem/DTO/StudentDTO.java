package com.technoelevet.StudentManagmentSystem.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private int studentId;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "DOB is required")
    private String dob;

    @NotBlank(message = "Standard is required")
    private String standerd;

    private SchoolDTO schoolDTO;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;
}
