package com.technoelevet.StudentManagmentSystem.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	    if (optionalStudentEntity.isPresent()) {
	        throw new DataAlreadyFoundException("Data With Given Id Is Already Exists");
	    }
	    Student student = new Student();
	    School school;
	    Optional<School> existingSchool = schoolRepository.findTheSchool(
	        studentDTO.getSchoolDTO().getSchoolName(),
	        studentDTO.getSchoolDTO().getSchoolCity()
	    );  if (existingSchool.isPresent()) {
	        school = existingSchool.get();
	    } else {
	        school = new School();
	        BeanUtils.copyProperties(studentDTO.getSchoolDTO(), school);
	        school = schoolRepository.save(school);
	    }
	    student.setSchool(school);
	    BeanUtils.copyProperties(studentDTO, student, "school"); 
	    Student savedStudent = studentRepository.save(student);
	    BeanUtils.copyProperties(savedStudent, studentDTO);

	    return studentDTO;
	}


	@Override
	public StudentDTO updateStudent(StudentDTO studentDTO) {
		Optional<Student> optionalStudentEntity = studentRepository.findById(studentDTO.getStudentId());
		if (!optionalStudentEntity.isPresent()) {
			throw new DataNotFoundException("Student with the given ID does not exist");
		}
		Student existingStudent = optionalStudentEntity.get();
		if (studentDTO.getSchoolDTO() != null) {
			SchoolDTO schoolDTO = studentDTO.getSchoolDTO();
			School existingSchool;
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

	@Override
	public List<StudentDTO> getAllStudents() {
		List<Student> allStudents = studentRepository.findAll();
		return allStudents.stream().map(student -> {
			StudentDTO studentDTO = new StudentDTO();
			BeanUtils.copyProperties(student, studentDTO);

			if (student.getSchool() != null) {
				SchoolDTO schoolDTO = new SchoolDTO();
				BeanUtils.copyProperties(student.getSchool(), schoolDTO);
				studentDTO.setSchoolDTO(schoolDTO);
			}
			return studentDTO;
		}).toList();
	}

	
	public List<StudentDTO> getAllStudentsWithSorting(String feild) {
		List<Student> all = studentRepository.findAll(Sort.by(Sort.Direction.ASC,feild));
		return all.stream().map(student -> {
			StudentDTO studentDTO = new StudentDTO();
			BeanUtils.copyProperties(student, studentDTO);

			if (student.getSchool() != null) {
				SchoolDTO schoolDTO = new SchoolDTO();
				BeanUtils.copyProperties(student.getSchool(), schoolDTO);
				studentDTO.setSchoolDTO(schoolDTO);
			}
			return studentDTO;
		}).toList();
	}
	
	public Page<Student> getAllStudentsWithPagination(int offSet, int pageSize) {
        Pageable pageable = PageRequest.of(offSet, pageSize);
        return studentRepository.findAll(pageable);
    }
	 public Page<StudentDTO> getAllStudentDTOsWithPagination(int offSet, int pageSize) {
	        Page<Student> studentPage = getAllStudentsWithPagination(offSet, pageSize);

	        List<StudentDTO> dtoList = studentPage.stream().map(student -> {
	            StudentDTO dto = new StudentDTO();
	            BeanUtils.copyProperties(student, dto);
	            return dto;
	        }).collect(Collectors.toList());

	        return new PageImpl<>(dtoList, studentPage.getPageable(), studentPage.getTotalElements());
	    }

}
