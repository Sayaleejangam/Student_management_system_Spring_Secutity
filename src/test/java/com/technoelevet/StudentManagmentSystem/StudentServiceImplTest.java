package com.technoelevet.StudentManagmentSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.technoelevet.StudentManagmentSystem.DTO.SchoolDTO;
import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;
import com.technoelevet.StudentManagmentSystem.Entity.School;
import com.technoelevet.StudentManagmentSystem.Entity.Student;
import com.technoelevet.StudentManagmentSystem.Exception.DataAlreadyFoundException;
import com.technoelevet.StudentManagmentSystem.Repository.SchoolRepository;
import com.technoelevet.StudentManagmentSystem.Repository.StudentRepository;
import com.technoelevet.StudentManagmentSystem.Service.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private SchoolRepository schoolRepository;
	@InjectMocks
	private StudentServiceImpl studentService;

	private StudentDTO studentDTO;
	private SchoolDTO schoolDTO;
	private Student student;
	private School school;

	@BeforeEach
	void setUp() {
		schoolDTO = new SchoolDTO();
		schoolDTO.setSchoolId(1);
		schoolDTO.setSchoolName("ABC School");
		schoolDTO.setSchoolCity("CityX");

		studentDTO = new StudentDTO();
		studentDTO.setStudentId(101);
		studentDTO.setStudentName("John");
		studentDTO.setSchoolDTO(schoolDTO);

		school = new School();
		school.setSchoolId(1);
		school.setSchoolName("ABC School");
		school.setSchoolCity("CityX");

		student = new Student();
		student.setStudentId(101);
		student.setStudentName("John");
		student.setSchool(school);
	}

	// ✅ Register Student - Success
	@Test
	void testRegisterStudent_success() {
		when(studentRepository.findById(101)).thenReturn(Optional.empty());
		when(schoolRepository.findTheSchool("ABC School", "CityX")).thenReturn(Optional.of(school));
		when(studentRepository.save(any(Student.class))).thenReturn(student);

		StudentDTO savedDTO = studentService.registerStudent(studentDTO);

		assertNotNull(savedDTO);
		assertEquals("John", savedDTO.getStudentName());
	}

	// ❌ Register Student - Already Exists
	@Test
	void testRegisterStudent_alreadyExists() {
		when(studentRepository.findById(101)).thenReturn(Optional.of(student));
		assertThrows(DataAlreadyFoundException.class, () -> {
			studentService.registerStudent(studentDTO);
		});
	}

	// ✅ Get Student by ID - Success
	@Test
	void testGetStudentById_success() {
		when(studentRepository.findByStudentId(101)).thenReturn(Optional.of(student));
		when(schoolRepository.findBySchoolId(1)).thenReturn(Optional.of(school));

		StudentDTO dto = studentService.getStundents(101);

		assertNotNull(dto);
		assertEquals("John", dto.getStudentName());
		assertEquals("ABC School", dto.getSchoolDTO().getSchoolName());
	}

	// ❌ Get Student by ID - Not Found
	@Test
	void testGetStudentById_notFound() {
		when(studentRepository.findByStudentId(101)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> {
			studentService.getStundents(101);
		});
	}

	// ✅ Get All Students
	@Test
	void testGetAllStudents() {
		List<Student> studentList = List.of(student);
		when(studentRepository.findAll()).thenReturn(studentList);

		List<StudentDTO> allStudents = studentService.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals("John", allStudents.get(0).getStudentName());
	}

	@Test
	void testGetAllStudentsWithSorting() {
		List<Student> studentList = List.of(student);
		when(studentRepository.findAll(Sort.by(Sort.Direction.ASC, "studentName"))).thenReturn(studentList);

		List<StudentDTO> result = studentService.getAllStudentsWithSorting("studentName");

		assertEquals(1, result.size());
		assertEquals("John", result.get(0).getStudentName());
	}

	@Test
	void testGetAllStudentsWithPagination() {
		List<Student> students = List.of(student);
		Page<Student> page = new PageImpl<>(students);

		when(studentRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);

		Page<Student> result = studentService.getAllStudentsWithPagination(0, 1);

		assertEquals(1, result.getTotalElements());
	}

	@Test
	void testGetAllStudentDTOsWithPagination() {
		List<Student> students = List.of(student);
		Page<Student> page = new PageImpl<>(students);

		when(studentRepository.findAll(PageRequest.of(0, 1))).thenReturn(page);

		Page<StudentDTO> dtoPage = studentService.getAllStudentDTOsWithPagination(0, 1);

		assertEquals(1, dtoPage.getTotalElements());
		assertEquals("John", dtoPage.getContent().get(0).getStudentName());
	}
}
