package com.sh.tasks.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.tasks.api.config.JwtTokenUtil;
import com.sh.tasks.api.model.JwtRequest;
import com.sh.tasks.api.model.JwtResponse;
import com.sh.tasks.api.services.JwtUserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <h1>JwtAuthenticationController</h1>
 * 
 * @author Bruno Amorim
 * @version 1.0
 * @since 2021-08-22
 */
@RestController
@CrossOrigin
@Api(tags = { "User API" })
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@ApiOperation(value = "Authenticates a user by username and password")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

//	@ApiOperation(value = "Create a new user to authenticate")
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
//		return ResponseEntity.ok(userDetailsService.save(user));
//	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}