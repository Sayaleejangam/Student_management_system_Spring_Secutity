package com.technoelevet.StudentManagmentSystem.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import com.technoelevet.StudentManagmentSystem.DTO.UserLogInDTO;
import com.technoelevet.StudentManagmentSystem.Entity.User;
import com.technoelevet.StudentManagmentSystem.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	private UserDTO convertToDTO(User user) {
		log.warn("Inside convert User to UserDto method");
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}

	private User convertToEntity(UserDTO userDTO) {
		log.warn("Inside convert UserDTO to User method");
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return user;
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) {

		log.warn("Inside Save User method");
		User user = convertToEntity(userDTO);
		String encode = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		return convertToDTO(userRepository.save(user));
	}

	@Override
	public UserDTO getUserById(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return convertToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO updateUser(int id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		existingUser.setUserName(userDTO.getUserName());
		existingUser.setDob(userDTO.getDob());
		existingUser.setUserRole(userDTO.getUserRole());
		return convertToDTO(userRepository.save(existingUser));
	}

	@Override
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public boolean veryfyUser(UserLogInDTO dto) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		if (authenticate.isAuthenticated())
			return true;
		else
			return false;
	}
}
