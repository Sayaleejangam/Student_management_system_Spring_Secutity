package com.technoelevet.StudentManagmentSystem.DTO;

import com.technoelevet.StudentManagmentSystem.Entity.School;

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
	private String studentName;
	private String dob;
	private String standerd;
	private SchoolDTO schoolDTO;
}
