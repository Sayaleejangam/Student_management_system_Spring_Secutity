package com.technoelevet.StudentManagmentSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;
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
	public ResponseEntity<ResponcesStructure> registerStudent(@Valid @RequestBody StudentDTO studentDTO) {
		return ResponseEntity
				.ok(new ResponcesStructure(false, "Student Details Added", studentService.registerStudent(studentDTO)));

	}

	@GetMapping("/")
	public String getInside() {
		return "Hi Sayalee From Student management System";
	}

	@PutMapping
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
	public ResponseEntity<ResponcesStructure> getAllStudents() {

		List<StudentDTO> students = studentService.getAllStudents();
		return ResponseEntity.ok(new ResponcesStructure(false, "Student Details Added", students));

	}

}
