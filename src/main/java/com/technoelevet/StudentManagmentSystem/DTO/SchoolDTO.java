package com.technoelevet.StudentManagmentSystem.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {

    private Integer schoolId;

    @NotBlank(message = "School name is required")
    private String schoolName;

    @NotBlank(message = "School city is required")
    private String schoolCity;
}
