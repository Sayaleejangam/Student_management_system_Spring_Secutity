package com.technoelevet.StudentManagmentSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import com.technoelevet.StudentManagmentSystem.Service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public UserDTO createUser(@RequestBody UserDTO userDTO) {
		log.warn("Inside Create User method");
		return userService.saveUser(userDTO);
	}

	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable int id) {
		log.warn("Inside get  User by Id method");
		return userService.getUserById(id);
	}

	@GetMapping
	public List<UserDTO> getAllUsers() {
		log.warn("Inside get All User method");
		return userService.getAllUsers();
	}

	@PutMapping("/{id}")
	public UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
		log.warn("Inside update User by Id method");
		return userService.updateUser(id, userDTO);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		log.warn("Inside delete User method");
		userService.deleteUser(id);
	}
}
