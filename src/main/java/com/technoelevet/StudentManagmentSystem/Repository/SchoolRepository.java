package com.technoelevet.StudentManagmentSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevet.StudentManagmentSystem.Entity.School;

@Repository
public interface SchoolRepository  extends JpaRepository<School, Integer> {

	Optional<School> findBySchoolId(Integer schoolId);
	
}
