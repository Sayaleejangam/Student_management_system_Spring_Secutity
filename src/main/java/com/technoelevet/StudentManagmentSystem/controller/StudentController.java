package com.technoelevet.StudentManagmentSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;
import com.technoelevet.StudentManagmentSystem.Entity.Student;
import com.technoelevet.StudentManagmentSystem.Responce.ApiResponse;
import com.technoelevet.StudentManagmentSystem.Responce.ResponcesStructure;
import com.technoelevet.StudentManagmentSystem.Service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@PostMapping("/register")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponcesStructure> registerStudent(@Valid @RequestBody StudentDTO studentDTO) {
		return ResponseEntity
				.ok(new ResponcesStructure(false, "Student Details Added", studentService.registerStudent(studentDTO)));

	}

	@GetMapping("/")
	public String getInside() {
		return "Hi Sayalee From Student management System";
	}

	@PutMapping("/updateStudent")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponcesStructure> updateStudent(StudentDTO studentDTO) {
		return ResponseEntity
				.ok(new ResponcesStructure(false, "Student Details Added", studentService.registerStudent(studentDTO)));

	}

	@GetMapping("/csrf-token")
	public CsrfToken geCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("csrf");
	}

	@GetMapping("/getStudent")
	public ResponseEntity<ResponcesStructure> getStudent(@RequestParam(name = "id") int id) {
		System.out.println(id);
		StudentDTO stundents = studentService.getStundents(id);
		return ResponseEntity.ok(new ResponcesStructure(false, "Student Details Added", stundents));

	}

	@GetMapping("/getStudents")
	@PreAuthorize("hasRole('ADMIN','USER')")
	public ResponseEntity<ResponcesStructure> getAllStudents() {

		List<StudentDTO> students = studentService.getAllStudents();
		return ResponseEntity.ok(new ResponcesStructure(false, "Student Details Added", students));

	}

	@GetMapping("/getSortedStudents/{field}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<ApiResponse<StudentDTO>> getAllSortedStudents(@PathVariable("field") String field) {
		List<StudentDTO> students = studentService.getAllStudentsWithSorting(field);
		ApiResponse<StudentDTO> response = new ApiResponse<>(false, "Sorted Student List Retrieved", students.size(),
				students);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/paginated/{offSet}/{pageSize}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<Student>> getStudentsWithPagination(@PathVariable(name = "offSet") int offSet,
			@PathVariable(name = "pageSize") int pageSize) {

		Page<Student> students = studentService.getAllStudentsWithPagination(offSet, pageSize);
		return ResponseEntity.ok(students);
	}

//getAllStudentDTOsWithPagination
	@GetMapping("/sortedAndPaginated/{offSet}/{pageSize}/{feild}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Page<StudentDTO>> getStudentsWithPagination(@PathVariable(name = "offSet") int offSet,
			@PathVariable(name = "pageSize") int pageSize, @PathVariable(name = "feild") String feild) {

 Page<StudentDTO> studentsWithPaginationAndSorting = studentService.getStudentsWithPaginationAndSorting(offSet, pageSize,feild);
		return ResponseEntity.ok(studentsWithPaginationAndSorting);
	}
}
