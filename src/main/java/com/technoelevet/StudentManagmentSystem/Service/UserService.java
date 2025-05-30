package com.technoelevet.StudentManagmentSystem.Service;

import java.util.List;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import com.technoelevet.StudentManagmentSystem.DTO.UserLogInDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO getUserById(int id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(int id, UserDTO userDTO);
    void deleteUser(int id);
	boolean veryfyUser(UserLogInDTO dto);
}
