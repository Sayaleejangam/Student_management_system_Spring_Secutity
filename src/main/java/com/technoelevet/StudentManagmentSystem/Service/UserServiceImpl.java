package com.technoelevet.StudentManagmentSystem.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoelevet.StudentManagmentSystem.DTO.UserDTO;
import com.technoelevet.StudentManagmentSystem.Entity.User;
import com.technoelevet.StudentManagmentSystem.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getUserId(), user.getUserName(), user.getDob(), user.getUserRole());
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getDob(), userDTO.getUserRole());
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(int id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setDob(userDTO.getDob());
        existingUser.setUserRole(userDTO.getUserRole());
        return convertToDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
