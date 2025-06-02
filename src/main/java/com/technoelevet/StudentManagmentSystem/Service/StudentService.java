package com.technoelevet.StudentManagmentSystem.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;
import com.technoelevet.StudentManagmentSystem.Entity.Student;

@Service
public interface StudentService {

	StudentDTO registerStudent(StudentDTO studentDTO);

	public StudentDTO updateStudent(StudentDTO studentDTO);

	StudentDTO getStundents(int id);
	List<StudentDTO> getAllStudentsWithSorting(String feild);
	
	List<StudentDTO> getAllStudents();
	
	public Page<Student> getAllStudentsWithPagination(int offSet, int pageSize) ;

	Page<StudentDTO> getAllStudentDTOsWithPagination(int offset, int pageSize);
}
