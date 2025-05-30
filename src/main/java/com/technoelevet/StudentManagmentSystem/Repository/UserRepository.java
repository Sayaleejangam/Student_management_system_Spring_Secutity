package com.technoelevet.StudentManagmentSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.technoelevet.StudentManagmentSystem.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String username);



}
