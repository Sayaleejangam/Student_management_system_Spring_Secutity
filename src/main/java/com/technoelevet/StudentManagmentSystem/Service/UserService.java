package com.technoelevet.StudentManagmentSystem.Service;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO getUserById(int id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(int id, UserDTO userDTO);
    void deleteUser(int id);
}
