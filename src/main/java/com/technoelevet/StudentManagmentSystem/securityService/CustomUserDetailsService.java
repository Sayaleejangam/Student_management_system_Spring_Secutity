package com.technoelevet.StudentManagmentSystem.securityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.technoelevet.StudentManagmentSystem.Entity.User;
import com.technoelevet.StudentManagmentSystem.Entity.UserPrincipal;
import com.technoelevet.StudentManagmentSystem.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			
			System.out.println(user);
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("User With Given UserName " + username + " Not Found");
		}
		return new UserPrincipal(user);
	}

}
