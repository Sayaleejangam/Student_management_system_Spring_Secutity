package com.technoelevet.StudentManagmentSystem.Service;

import org.springframework.stereotype.Service;

import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;

@Service
public interface StudentService {

	StudentDTO registerStudent(StudentDTO studentDTO);

	public StudentDTO updateStudent(StudentDTO studentDTO);

	StudentDTO getStundents(int id);
}
