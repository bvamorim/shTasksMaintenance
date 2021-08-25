package com.sh.tasks.api.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sh.tasks.api.model.User;
import com.sh.tasks.api.repository.UserDao;

/**
 * <h1>JwtUserDetailsService</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

//	@Autowired
//	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

//	public User save(UserDto user) {
//		User newUser = new User();
//		newUser.setUsername(user.getUsername());
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//		return userDao.save(newUser);
//	}
	
}