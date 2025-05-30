package com.technoelevet.StudentManagmentSystem.Service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevet.StudentManagmentSystem.DTO.SchoolDTO;
import com.technoelevet.StudentManagmentSystem.DTO.StudentDTO;
import com.technoelevet.StudentManagmentSystem.Entity.School;
import com.technoelevet.StudentManagmentSystem.Entity.Student;
import com.technoelevet.StudentManagmentSystem.Exception.DataAlreadyFoundException;
import com.technoelevet.StudentManagmentSystem.Exception.DataNotFoundException;
import com.technoelevet.StudentManagmentSystem.Repository.SchoolRepository;
import com.technoelevet.StudentManagmentSystem.Repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private SchoolRepository schoolRepository;

	private ObjectMapper objectmapper = new ObjectMapper();

	@Override
	public StudentDTO registerStudent(StudentDTO studentDTO) {
		Optional<Student> optionalStudentEntity = studentRepository.findById(studentDTO.getStudentId());
		if (!optionalStudentEntity.isPresent()) {
			Student student = new Student();
			School school = new School();
			BeanUtils.copyProperties(studentDTO.getSchoolDTO(), school);
			School saveSchool = schoolRepository.save(school);
			student.setSchool(saveSchool);
			BeanUtils.copyProperties(studentDTO, student);
			Student saveStudent = studentRepository.save(student);
			BeanUtils.copyProperties(saveStudent, studentDTO);
			return studentDTO;

		}
		throw new DataAlreadyFoundException("Data With Given Id Is Already Exists");
	}

	@Override
	public StudentDTO updateStudent(StudentDTO studentDTO) {
		// Check if the student with the given ID exists
		Optional<Student> optionalStudentEntity = studentRepository.findById(studentDTO.getStudentId());
		if (!optionalStudentEntity.isPresent()) {
			throw new DataNotFoundException("Student with the given ID does not exist");
		}
		// Retrieve the existing student entity
		Student existingStudent = optionalStudentEntity.get();
		// Check if we need to update the school information
		if (studentDTO.getSchoolDTO() != null) {
			SchoolDTO schoolDTO = studentDTO.getSchoolDTO();
			School existingSchool;
			// If the school already exists, update it
			if (schoolDTO.getSchoolId() != null) {
				Optional<School> optionalSchoolEntity = schoolRepository.findById(schoolDTO.getSchoolId());
				if (optionalSchoolEntity.isPresent()) {
					existingSchool = optionalSchoolEntity.get();
					BeanUtils.copyProperties(schoolDTO, existingSchool, "schoolId");
				} else {
					throw new DataNotFoundException("School with the given ID does not exist");
				}
			} else {
				existingSchool = new School();
				BeanUtils.copyProperties(schoolDTO, existingSchool);
			}
			School savedSchool = schoolRepository.save(existingSchool);
			existingStudent.setSchool(savedSchool);
		}
		BeanUtils.copyProperties(studentDTO, existingStudent, "studentId", "school");
		Student updatedStudent = studentRepository.save(existingStudent);
		BeanUtils.copyProperties(updatedStudent, studentDTO);
		return studentDTO;
	}

	@Override
	public StudentDTO getStundents(int id) {
		Optional<Student> byId = studentRepository.findByStudentId(id);
		School school = byId.get().getSchool();
		Optional<School> bySchoolId = schoolRepository.findBySchoolId(school.getSchoolId());

		SchoolDTO dtoSchoolDTO = new SchoolDTO();
		BeanUtils.copyProperties(bySchoolId.get(), dtoSchoolDTO);
		StudentDTO dto = new StudentDTO();
		dto.setSchoolDTO(dtoSchoolDTO);
		if (byId.isPresent()) {
			BeanUtils.copyProperties(byId.get(), dto);
			return dto;
		} else {
			throw new DataNotFoundException("Student With Given Id Ia Not present");
		}
	}

}
