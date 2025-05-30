package com.technoelevet.StudentManagmentSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private int userId;
	private String userName;
	private String Password;
	private String dob;
	private String userRole;

}
