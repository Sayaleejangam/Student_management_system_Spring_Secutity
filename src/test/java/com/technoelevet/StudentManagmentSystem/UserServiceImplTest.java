package com.technoelevet.StudentManagmentSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import com.technoelevet.StudentManagmentSystem.DTO.UserLogInDTO;
import com.technoelevet.StudentManagmentSystem.Entity.User;
import com.technoelevet.StudentManagmentSystem.Repository.UserRepository;
import com.technoelevet.StudentManagmentSystem.Service.UserServiceImpl;
import com.technoelevet.StudentManagmentSystem.securityService.JwtService;

public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private UserDTO getSampleUserDTO() {
		UserDTO dto = new UserDTO();
		dto.setUserId(1);
		dto.setUserName("testUser");
		dto.setPassword("password123");
		dto.setUserRole("ADMIN");
		
		return dto;
	}

	private User getSampleUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("testUser");
		user.setPassword(bCryptPasswordEncoder.encode("password123"));
		user.setUserRole("ADMIN");

		return user;
	}

	@Test
	public void testSaveUser() {
		UserDTO dto = getSampleUserDTO();
		User userEntity = getSampleUser();

		when(userRepository.save(any(User.class))).thenReturn(userEntity);

		UserDTO savedUser = userService.saveUser(dto);

		assertEquals(dto.getUserName(), savedUser.getUserName());
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	public void testGetUserById() {
		User user = getSampleUser();
		when(userRepository.findById(1)).thenReturn(Optional.of(user));

		UserDTO dto = userService.getUserById(1);

		assertEquals("testUser", dto.getUserName());
	}

	@Test
	public void testGetUserById_NotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			userService.getUserById(1);
		});

		assertEquals("User not found with ID: 1", ex.getMessage());
	}

	@Test
	public void testGetAllUsers() {
		List<User> userList = Arrays.asList(getSampleUser(), getSampleUser());
		when(userRepository.findAll()).thenReturn(userList);

		List<UserDTO> dtos = userService.getAllUsers();

		assertEquals(2, dtos.size());
	}

	@Test
	public void testUpdateUser() {
		User user = getSampleUser();
		UserDTO updateDTO = getSampleUserDTO();
		updateDTO.setUserName("updatedName");

		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserDTO updatedUser = userService.updateUser(1, updateDTO);

		assertEquals("updatedName", updatedUser.getUserName());
	}

	@Test
	public void testDeleteUser() {
		doNothing().when(userRepository).deleteById(1);
		userService.deleteUser(1);
		verify(userRepository, times(1)).deleteById(1);
	}

	@Test
	public void testVerifyUser_Success() {
		UserLogInDTO loginDTO = new UserLogInDTO();
		loginDTO.setUserName("testUser");
		loginDTO.setPassword("password123");

		Authentication auth = mock(Authentication.class);
		when(auth.isAuthenticated()).thenReturn(true);
		when(authenticationManager.authenticate(any())).thenReturn(auth);
		when(jwtService.generateToken("testUser")).thenReturn("mockedToken");

		String token = userService.veryfyUser(loginDTO);
		assertEquals("mockedToken", token);
	}

	@Test
	public void testVerifyUser_InvalidCredentials() {
		UserLogInDTO loginDTO = new UserLogInDTO();
		loginDTO.setUserName("testUser");
		loginDTO.setPassword("wrongPass");

		when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Bad Credentials"));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> {
			userService.veryfyUser(loginDTO);
		});

		assertEquals("Invalid username or password", ex.getMessage());
	}
}
