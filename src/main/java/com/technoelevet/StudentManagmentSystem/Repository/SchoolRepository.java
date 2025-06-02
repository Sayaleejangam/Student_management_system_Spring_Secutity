package com.technoelevet.StudentManagmentSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technoelevet.StudentManagmentSystem.Entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

	Optional<School> findBySchoolId(Integer schoolId);

	@Query("SELECT s FROM School s WHERE s.schoolName = :schoolName AND s.schoolCity = :schoolCity")
	Optional<School> findTheSchool(@Param("schoolName") String schoolName, @Param("schoolCity") String schoolCity);

}
