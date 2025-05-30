package com.technoelevet.StudentManagmentSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevet.StudentManagmentSystem.Entity.Student;


public interface StudentRepository extends JpaRepository<Student, Integer> {

	Optional<Student> findByStudentId(int studentId);
}
