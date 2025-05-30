package com.technoelevet.StudentManagmentSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technoelevet.StudentManagmentSystem.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.userName = :username")
	User findByUserName(@Param("username") String username);

}
